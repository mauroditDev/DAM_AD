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
		} 
    }

    public boolean createTables() throws YaExisteException{
    	boolean res = true;
    	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/","root","root");
			stmnt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
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
    		res = false;
    	}
    	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/banco","root","root");
			stmnt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
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
				cli.nombre = rs.getString(1);
				cli.f_nac = rs.getString(2);
				cli.direccion = rs.getString(3);
			}
			else{
				res = false;
			}
		}catch(Exception e){
			e.printStackTrace();
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
				cli.id = rs.getInt(1);
				cli.nombre = rs.getString(2);
				cli.f_nac = rs.getString(3);
				cli.direccion = rs.getString(4);
				resultado.add(cli);
			}
			
		}catch(Exception e){
			e.printStackTrace();
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
					suc.idsucursal = rs.getInt(1);
					suc.cp = rs.getString(3);
					suc.direccion = rs.getString(2);
					resultado.add(suc);
				}
				
			}catch(Exception e){
				e.printStackTrace();
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
				suc.idsucursal = rs.getInt(1);
				suc.direccion = rs.getString(2);
				suc.cp = rs.getString(3);
			}
			else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
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
			res = false;
		}
		return res;
	}

	public ArrayList<Cuenta> getCuentas() {
		ArrayList<Cuenta> resultado = new ArrayList<>();
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT cu.*, cl.id_cliente FROM cuenta cu JOIN titular cl"
					+ " ON cu.id = cl.id_cuenta ORDER BY cu.id DESC"
					);
			Cuenta cuenta = new Cuenta();
			int i = 0;
			while(rs.next()){
				
				if(cuenta.id!=rs.getInt(1)){
					if(i!=0)
						resultado.add(cuenta);
					cuenta = new Cuenta();
					cuenta.id = rs.getInt(1);
					cuenta.saldo = rs.getInt(2);
					cuenta.titulares.add(rs.getInt(3));
				}
				
				cuenta.titulares.add(rs.getInt(3));
				i = 1;	
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultado;
	}

	public int addCuenta(Cuenta cuenta) {
		// TODO Auto-generated method stub
		return 0;
	}
}
