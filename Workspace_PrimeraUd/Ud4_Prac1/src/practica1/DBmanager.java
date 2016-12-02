package practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;




public class DBmanager {
	public Connection con;
    public Statement stmnt;
    
    public DBmanager(){
       try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql://localhost/banco","root","root");
			stmnt = con.createStatement();
		} catch(MySQLSyntaxErrorException ex){
			try {
				con = DriverManager.getConnection("jdbc:mysql://localhost/","root","root");
				stmnt = con.createStatement();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();

    		javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		} 
    }

    public boolean createTables() throws YaExisteException{
    	boolean res = true;
    	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/","root","root");
			stmnt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
    		javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e1.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	try{
    		BufferedReader br = new BufferedReader(new FileReader("/media/Datos/Datos_Mauro/Acceso_Datos/Workspace_PrimeraUd/Ud4_Prac1/banco.sql"));
    		String sentencia = "";
    		String linea = br.readLine();
    		while(linea!=null){
    			sentencia += linea + " ";
    			linea = br.readLine();
    		}
    		br.close();
    		String[] sentencias = sentencia.split("\\\\");
    		for (String sent: sentencias){
    			sent.replaceAll("\\\\", "");
    			if(!sent.equals(""))
    				stmnt.executeUpdate(sent);
    		}
    		
    	}catch(SQLException e){
    		throw new YaExisteException("La base de datos ya existe");
    	}catch(Exception e){
    		e.printStackTrace();
    		javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
    		res = false;
    	}
    	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/banco","root","root");
			stmnt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e1.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	return res;
    	
    }

	public boolean reiniciarTablas() {
		boolean res = true;
		try{
			stmnt.executeUpdate("drop database banco");
			createTables();
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public int addCliente(String nombre, String fecha, String direccion) {
		int res = -1;
		try{
			stmnt.executeUpdate(
					"INSERT INTO cliente (nombre,f_nac,direccion) VALUES"
					+ "( '"+nombre+"', "
					+ " '"+fecha+"', "
					+ "'"+direccion+"')",
					Statement.RETURN_GENERATED_KEYS
					);
			ResultSet rs = stmnt.getGeneratedKeys();
			rs.next();
			res = rs.getInt(1);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return res;
	}

	public boolean getClient(String id, Cliente cli) {
		boolean res = true;
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT nombre, f_nac, direccion FROM cliente WHERE id = "+
						id
					);
			if(rs.next()){
				cli.nombre = rs.getString("nombre");
				cli.f_nac = rs.getString("f_nac");
				cli.direccion = rs.getString("direccion");
			}
			else{
				res = false;
			}
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public boolean eliminarCliente(String id) {
		boolean res = true;
		try{
			stmnt.executeUpdate(
					"DELETE FROM cliente WHERE id = '"
					+ id + "'"
					);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().equals("Cannot delete or update a parent row: a foreign key constraint fails "
					+ "(`banco`.`titular`, CONSTRAINT `fk_cuenta_has_cliente_cliente1` FOREIGN KEY "
					+ "(`id_cliente`) REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)"))
				javax.swing.JOptionPane.showConfirmDialog(null, "no puede eliminarse un cliente que tenga cuentas abiertas",
						"Error",javax.swing.JOptionPane.PLAIN_MESSAGE);
			else
				javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			
			res = false;
		}
		return res;
	}

	public boolean modCliente(Cliente cli) {
		boolean res = true;
		try{
			stmnt.executeUpdate(
					"UPDATE cliente SET "
					+ "nombre = '"+cli.nombre+"', "
					+ "f_nac = '"+cli.f_nac+"', "
					+ "direccion = '"+cli.direccion+"'"
					+ " WHERE id = "+cli.id
					);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public ArrayList<Cliente> getClientes() {
		 ArrayList<Cliente> resultado = new ArrayList<>();
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT * FROM cliente"
					);
			while(rs.next()){
				Cliente cli = new Cliente();
				cli.id = rs.getInt("id");
				cli.nombre = rs.getString("nombre");
				cli.f_nac = rs.getString("f_nac");
				cli.direccion = rs.getString("direccion");
				resultado.add(cli);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return resultado;
	}

	public ArrayList<Sucursal> getSucursales() {
		 ArrayList<Sucursal> resultado = new ArrayList<>();
			try{
				ResultSet rs =
				stmnt.executeQuery(
						"SELECT * FROM sucursal"
						);
				while(rs.next()){
					Sucursal suc = new Sucursal();
					suc.idsucursal = rs.getInt("idsucursal");
					suc.cp = rs.getString("cp");
					suc.direccion = rs.getString("direccion");
					resultado.add(suc);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
						"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			}
			return resultado;
	}

	public boolean modSucursal(Sucursal suc) {
		boolean res = true;
		try{
			stmnt.executeUpdate(
					"UPDATE sucursal SET "
					+ "cp = '"+suc.cp+"', "
					+ "direccion = '"+suc.direccion+"'"
					+ " WHERE idsucursal = "+suc.idsucursal
					);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public boolean getSucursal(String text, Sucursal suc) {
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT * FROM sucursal WHERE idsucursal = "+text
					);
			if(rs.next()){
				suc.idsucursal = rs.getInt("idsucursal");
				suc.direccion = rs.getString("direccion");
				suc.cp = rs.getString("cp");
			}
			else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		return true;
	}

	public int addSucursal(Sucursal suc) {
		int res = -1;
		try{
			stmnt.executeUpdate(
					"INSERT INTO sucursal (cp,direccion) VALUES"
					+ "( '"+suc.cp+"', "
					+ " '"+suc.direccion+"')",
					Statement.RETURN_GENERATED_KEYS
					);
			ResultSet rs = stmnt.getGeneratedKeys();
			rs.next();
			res = rs.getInt(1);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return res;
	}

	public boolean eliminarSucursal(String id) {
		boolean res = true;
		try{
			stmnt.executeUpdate(
					"DELETE FROM sucursal WHERE idsucursal = "
					+ id 
					);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public ArrayList<Cuenta> getCuentas() {
		ArrayList<Cuenta> resultado = new ArrayList<>();
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT cu.*, cl.id_cliente FROM cuenta cu JOIN titular cl "
					+ "ON cu.id = cl.id_cuenta ORDER BY cu.id DESC"
					);
			Cuenta cuenta = new Cuenta();
			int i = 0;
			while(rs.next()){
				
				if(cuenta.id!=rs.getInt(1)){
					if(i!=0)
						resultado.add(cuenta);
					cuenta = new Cuenta();
					cuenta.id = rs.getInt("id");
					cuenta.saldo = rs.getInt("saldo");
					cuenta.titulares.add(rs.getInt("id_cliente"));
				}
				else{
					cuenta.titulares.add(rs.getInt(3));
					i = 1;
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return resultado;
	}

	public int addCuenta(Cuenta cuenta) {
		int res = -1;
		try{
			con.setAutoCommit(false);
			
			stmnt.executeUpdate(
					"INSERT INTO cuenta (saldo) VALUES"
					+ "( "+cuenta.saldo+ ")",
					Statement.RETURN_GENERATED_KEYS
					);
			ResultSet rs = stmnt.getGeneratedKeys();
			rs.next();
			cuenta.id = rs.getInt(1);
			
			for(Integer titular:cuenta.titulares){
				stmnt.executeUpdate(
					"INSERT INTO titular (id_cuenta, id_cliente) VALUES"
					+ "("+cuenta.id+", "+titular+")"
					);
			}
			
			con.commit();
			
			con.setAutoCommit(true);
			
			res = cuenta.id;
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return res;
	}
	
	public boolean modCuenta(Cuenta cuenta){
		boolean res = true;
		
		try{
			con.setAutoCommit(false);
			
			stmnt.executeUpdate(
					"UPDATE cuenta SET "
					+ "saldo = "+cuenta.saldo + " WHERE id = "+cuenta.id
					);
			
			stmnt.executeUpdate(
					"DELETE FROM titular WHERE id_cuenta = "+cuenta.id
					);
			
			for(Integer titular:cuenta.titulares){
				stmnt.executeUpdate(
						"INSERT INTO titular (id_cuenta, id_cliente) VALUES"
								+ "("+cuenta.id+", "+titular+")"
					);
			}
			
			con.commit();
			
			con.setAutoCommit(true);
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		return res;
	}
	
	public Cuenta getCuenta(String id){
		
		Cuenta cuenta = new Cuenta();
		
		try{
			
			ResultSet rs = stmnt.executeQuery(
				"SELECT cu.*, cl.id_cliente FROM cuenta cu JOIN titular cl"
				+ " ON cu.id = cl.id_cuenta WHERE cu.id = " + id
				);

			if(rs.next()){
				do{
					cuenta.id = rs.getInt("id");
					cuenta.saldo = rs.getInt("saldo");
					cuenta.titulares.add(rs.getInt("id_cliente"));
				}while(rs.next());
			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		
		
		return cuenta;
		
	}
	
	public boolean eliminarCuenta(String id){
		boolean res = true;
		try{
			
			con.setAutoCommit(false);
			
			stmnt.executeUpdate("DELETE FROM cuenta WHERE id = "+id.toString());
			
			con.commit();
			
			con.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().equals(
					"Cannot delete or update a parent row: a foreign key constraint fails "
					+ "(`banco`.`titular`, CONSTRAINT `fk_cuenta_has_cliente_cuenta1` FOREIGN KEY "
					+ "(`id_cuenta`) REFERENCES `cuenta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)"))
				javax.swing.JOptionPane.showConfirmDialog(null, "no puede eliminar una cuenta con titulares asociados",
						"Aviso",javax.swing.JOptionPane.PLAIN_MESSAGE);
			else
				javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
						"Excepción grave, pongase en contacto con un técnico",javax.swing.JOptionPane.PLAIN_MESSAGE);
			
			res = false;
		}
		
		
		
		return res;
	}
	
}
