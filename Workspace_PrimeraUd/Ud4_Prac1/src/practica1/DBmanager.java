package practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.sql.Date;

import javax.swing.JOptionPane;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;




public class DBmanager {
	/* **************************
	 * DEFINICION DE LITERALES: *
	 ************************** */
	//---------------------------------- Cliente --------------------------
	public final String T_CLI = "cliente";
	public final String CLI_ID = "id";
	public final String CLI_N = "nombre";
	public final String CLI_NIF = "nif";
	public final String CLI_F = "f_nac";
	public final String CLI_D = "direccion";
	public final String CLI_SEL = "select * from "+T_CLI;
	public final String CLI_SEL_ID = CLI_SEL + " where "+CLI_ID+" in(?)";
	public final String CLI_SEL_NIF = CLI_SEL + " where "+CLI_NIF+" in(?)";
	public final String CLI_DEL = "delete from "+T_CLI+" where id in (?)";
	public final String CLI_INS = "insert into "+T_CLI+" ("+CLI_N+", "+CLI_F+", "+CLI_D+
			", "+CLI_NIF+") VALUES (?, ?, ?, ?)";
	public final String CLI_UPD = "UPDATE "+T_CLI+" SET "+CLI_N+"=?, "+CLI_F+"=?, "+
			CLI_D+"=?, "+CLI_NIF+"=? WHERE "+CLI_ID+" IN (?)";
	
	//----------------------------------- Sucursal -------------------------
	public final String T_SUC = "sucursal";
	public final String SUC_ID = "idsucursal";
	public final String SUC_CP = "cp";
	public final String SUC_D = "direccion";
	public final String SUC_SEL = "select * from "+T_SUC;
	public final String SUC_SEL_ID = SUC_SEL + " where "+SUC_ID+" IN (?)";
	public final String SUC_DEL = "delete from "+T_SUC+" where "+SUC_ID+" IN (?)";
	public final String SUC_INS = "insert into "+T_SUC+" ("+SUC_CP+", "+
			SUC_D+") VALUES (?, ?)";
	public final String SUC_UPD = "UPDATE "+T_SUC+" SET "+SUC_CP+"=?, "+SUC_D+
			"=? WHERE "+SUC_ID+" IN (?)";
	
	//------------------------------ Cuenta ----------------------------------
	public final String T_CUE = "cuenta";
	public final String CUE_ID = "id";
	public final String CUE_S = "saldo";
	public final String CUE_UPD = "UPDATE "+T_CUE+" SET "+CUE_S+"="+CUE_S+"+? WHERE "+CUE_ID+" IN (?)";
	
	
	//--------------------------------Movimientos-----------------------------
	public final String T_MOV = "movimiento";
	public final String MOV_ID = "id";
	public final String MOV_F = "f_h";
	public final String MOV_I = "importe";
	public final String MOV_CU = "id_cuenta";
	public final String MOV_SEL = "select * from "+T_MOV;
	public final String MOV_SEL_CU = MOV_SEL + " where "+MOV_CU+" IN (?)";
	public final String MOV_SEL_F = MOV_SEL_CU + " AND "+MOV_F+" >= ? AND "+MOV_F+" <= ?";
	public final String MOV_INS = "insert into "+T_MOV+" ("+MOV_F+", "+MOV_I+", "+MOV_CU+") "
			+ "VALUES ( ?, ?, ? )";
	
	//----------------------------------- Errores -------------------------------
	public final String ERR_INS_CUE = "Cannot add or update a child row: a foreign "
			+ "key constraint fails (`banco`.`titular`, CONSTRAINT "
			+ "`fk_cuenta_has_cliente_cliente1` FOREIGN KEY (`id_cliente`) "
			+ "REFERENCES `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
	public final String ERR_DEL_CLI = "Cannot delete or update a parent row: a"
			+ " foreign key constraint fails (`banco`.`titular`, CONSTRAINT "
			+ "`fk_cuenta_has_cliente_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES"
			+ " `cliente` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
	public final String ERR_DEL_CUE = "Cannot delete or update a parent row: a foreign "
			+ "key constraint fails (`banco`.`titular`, CONSTRAINT "
			+ "`fk_cuenta_has_cliente_cuenta1` FOREIGN KEY (`id_cuenta`) REFERENCES "
			+ "`cuenta` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
	public final String ERR_DEL_CUE_MOV = "Cannot delete or update a parent "
			+ "row: a foreign key constraint fails (`banco`.`movimiento`, CONSTRAINT"
			+ " `fk_movimiento_cuenta` FOREIGN KEY (`id_cuenta`) REFERENCES `cuenta`"
			+ " (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION)";
	
	/* ************************************
	 * FIN DE LA DEFINICION DE LITERALES: *
	 ************************************ */
	
	
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
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
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
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	try{
    		BufferedReader br = new BufferedReader(new FileReader("./banco.sql"));
    		String sentencia = "";
    		String linea = br.readLine();
    		while(linea!=null){
    			sentencia += linea + " ";
    			linea = br.readLine();
    		}
    		br.close();
    		String[] sentencias = sentencia.split(";");
    		for (String sent: sentencias){
    			System.out.println(sent);
    			System.out.println();
    			sent.replaceAll(";", "");
    			if(sent.trim().length()>0)
    				stmnt.executeUpdate(sent);
    		}
    		
    	}catch(SQLException e){
    		throw new YaExisteException("La base de datos ya existe");
    	}catch(Exception e){
    		e.printStackTrace();
    		javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
    		res = false;
    	}
    	try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/banco","root","root");
			stmnt = con.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e1.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
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
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public int addCliente(String nombre, java.util.Date fecha, String direccion, String nif) {
		int res = -1;
		try{
			PreparedStatement ps = con.prepareStatement(CLI_INS, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, nombre);
			ps.setDate(2, new Date(fecha.getTime()));
			ps.setString(3, direccion);
			ps.setString(4, nif);
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			res = rs.getInt(1);
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().equals("Duplicate entry '"+nif+"' for key 'nif_UNIQUE'"))
				javax.swing.JOptionPane.showConfirmDialog(null, 
						"ya existe un cliente con ese NIF",
						"Error", javax.swing.JOptionPane.PLAIN_MESSAGE);
			else
				javax.swing.JOptionPane.showConfirmDialog(null,
						"message: "+e.getMessage(),
						"Excepción grave, pongase en contacto con un técnico",
						javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return res;
	}

	public Cliente getClient(String id) {
		Cliente cli = new Cliente();
		try{
			PreparedStatement ps = con.prepareStatement(CLI_SEL_ID);
			ps.setInt(1, Integer.valueOf(id));
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				cli.id = rs.getInt(CLI_ID);
				cli.nombre = rs.getString(CLI_N);
				cli.f_nac.setTime(rs.getDate(CLI_F).getTime());
				cli.direccion = rs.getString(CLI_D);
				cli.nif = rs.getString(CLI_NIF);
			}
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return cli;
	}
	
	public Cliente getClientNif(String nif) {
		Cliente cli = new Cliente();
		try{
			PreparedStatement ps = con.prepareStatement(CLI_SEL_NIF);
			ps.setString(1, nif);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				cli.id = rs.getInt(CLI_ID);
				cli.nombre = rs.getString(CLI_N);
				cli.f_nac.setTime(rs.getDate(CLI_F).getTime());
				cli.direccion = rs.getString(CLI_D);
				cli.nif = rs.getString(CLI_NIF);
			}
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return cli;
	}

	public boolean eliminarCliente(String id) {
		boolean res = true;
		try{
			
			PreparedStatement ps = con.prepareStatement(CLI_DEL);
			ps.setInt(1, Integer.valueOf(id));
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			if(e.getMessage().equals(ERR_DEL_CLI))
				javax.swing.JOptionPane.showConfirmDialog(null,
						"no puede eliminarse un cliente que tenga cuentas abiertas",
						"Error",javax.swing.JOptionPane.PLAIN_MESSAGE);
			else
				javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			
			res = false;
		}
		return res;
	}

	public boolean modCliente(Cliente cli) {
		boolean res = true;
		try{
			PreparedStatement ps = con.prepareStatement(CLI_UPD);
			ps.setString(1, cli.nombre);
			ps.setDate(2, new Date(cli.f_nac.getTime()));
			ps.setString(3, cli.direccion);
			ps.setString(4, cli.nif);
			ps.setInt(5, cli.id);
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public ArrayList<Cliente> getClientes() {
		 ArrayList<Cliente> resultado = new ArrayList<>();
		try{
			PreparedStatement ps = con.prepareStatement(CLI_SEL);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Cliente cli = new Cliente();
				cli.id = rs.getInt(CLI_ID);
				cli.nombre = rs.getString(CLI_N);
				cli.f_nac.setTime(rs.getDate(CLI_F).getTime());
				cli.direccion = rs.getString(CLI_D);
				cli.nif = rs.getString(CLI_NIF);
				resultado.add(cli);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		return resultado;
	}

	public ArrayList<Sucursal> getSucursales() {
		 ArrayList<Sucursal> resultado = new ArrayList<>();
			try{
				ResultSet rs = con.prepareStatement(SUC_SEL).executeQuery();
				while(rs.next()){
					Sucursal suc = new Sucursal();
					suc.idsucursal = rs.getInt(SUC_ID);
					suc.cp = rs.getString(SUC_CP);
					suc.direccion = rs.getString(SUC_D);
					resultado.add(suc);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
						"Excepción grave, pongase en contacto con un técnico",
						javax.swing.JOptionPane.PLAIN_MESSAGE);
			}
			return resultado;
	}

	public boolean modSucursal(Sucursal suc) {
		boolean res = true;
		try{
			PreparedStatement ps = con.prepareStatement(SUC_UPD);
			ps.setString(1, suc.cp);
			ps.setString(2, suc.direccion);
			ps.setInt(3, suc.idsucursal);
			ps.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	
	
	public Sucursal getSucursal(String id) {
		
		Sucursal suc = new Sucursal();
		try{
			PreparedStatement ps = con.prepareStatement(SUC_SEL_ID);
			ps.setInt(1, Integer.valueOf(id));
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
				suc.idsucursal = rs.getInt("idsucursal");
				suc.direccion = rs.getString("direccion");
				suc.cp = rs.getString("cp");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		
		return suc;
		
	}

	
	public int addSucursal(Sucursal suc) {
		try{
			
			PreparedStatement ps = con.prepareStatement(SUC_INS, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, suc.cp);
			ps.setString(2,suc.direccion);
			
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			return -1;
		}
		
	}
	public boolean eliminarSucursal(String id) {
		boolean res = true;
		try{
			
			PreparedStatement ps = con.prepareStatement(SUC_DEL);
			ps.setInt(1, Integer.valueOf(id));
			ps.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			res = false;
		}
		return res;
	}

	public ArrayList<Cuenta> getCuentas() {
		ArrayList<Cuenta> resultado = new ArrayList<>();
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT cu.*, cl.id_cliente FROM cuenta cu LEFT JOIN titular cl "
					+ "ON cu.id = cl.id_cuenta ORDER BY cu.id DESC"
					);
			
			Cuenta cuenta;
				
			while(rs.next()){
				
				cuenta = new Cuenta();
				cuenta.id = rs.getInt("id");
				cuenta.saldo = rs.getInt("saldo");
				cuenta.titulares.add(rs.getInt("id_cliente"));
				int aux = 0;
				while(rs.next() && rs.getInt("id")==cuenta.id){
					aux= rs.getInt("id");
					cuenta.titulares.add(rs.getInt("id_cliente"));
				}
				if(aux != cuenta.id)
					rs.previous();
				resultado.add(cuenta);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
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
			if(e.getMessage().equals(ERR_INS_CUE))
				javax.swing.JOptionPane.showConfirmDialog(null, "El cliente no existe",
						"Error",javax.swing.JOptionPane.PLAIN_MESSAGE);
			else{
				e.printStackTrace();
				javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
						"Excepción grave, pongase en contacto con un técnico",
						javax.swing.JOptionPane.PLAIN_MESSAGE);
			}
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
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		
		return res;
	}
	
	public Cuenta getCuenta(String id){
		
		Cuenta cuenta = new Cuenta();
		
		try{
			
			ResultSet rs = stmnt.executeQuery(
				"SELECT cu.*, cl.id_cliente FROM cuenta cu LEFT JOIN titular cl"
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
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
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
			if(e.getMessage().equals(ERR_DEL_CUE))
				JOptionPane.showMessageDialog(null, 
						"no puede eliminar una cuenta con titulares asociados",
						"Aviso",JOptionPane.ERROR_MESSAGE);
			else{
				if (e.getMessage().equals(ERR_DEL_CUE_MOV))
					JOptionPane.showMessageDialog(null, 
							"no puede eliminar una cuenta con movimientos asociados",
							"Aviso",JOptionPane.ERROR_MESSAGE);
				else
					javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
						"Excepción grave, pongase en contacto con un técnico",
						javax.swing.JOptionPane.PLAIN_MESSAGE);
			}
			res = false;
		}
		
		
		
		return res;
	}

	public boolean ingresar(Cuenta cuentaSel, Integer cantidad) {
		try{
			con.setAutoCommit(false);
			PreparedStatement ps = con.prepareStatement(MOV_INS);
			Timestamp date = new Timestamp(System.currentTimeMillis());
			ps.setTimestamp(1,date);
			ps.setInt(2, cantidad);
			ps.setInt(3, cuentaSel.id);
			
			if(ps.executeUpdate()==1){
				ps = con.prepareStatement(CUE_UPD);
				ps.setInt(1, cantidad);
				ps.setInt(2, cuentaSel.id);
				ps.executeUpdate();
			}
			else{
				con.rollback();
				con.setAutoCommit(true);
				return false;
			}
			
			con.commit();
			
			con.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, póngase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		return true;
	}

	public ArrayList<Movimiento> extracto(Cuenta cuenta, long desde, long hasta) {
		ArrayList<Movimiento> resultado = new ArrayList<>();
		try{
			
			PreparedStatement ps = con.prepareStatement(MOV_SEL_F);
			ps.setInt(1, cuenta.id);
			ps.setDate(2,new Date(desde));
			ps.setDate(3,new Date(hasta));
			ResultSet rs = ps.executeQuery();
			int i = 0;
			while(rs.next()){
				System.out.println(i++);
				Movimiento mov = new Movimiento();
				mov.id = rs.getInt(MOV_ID);
				mov.importe = rs.getInt(MOV_I);
				mov.cuenta = cuenta;
				mov.f_h = rs.getTimestamp(MOV_F).getTime();
				System.out.println(mov.f_h);
				resultado.add(mov);

			}
			
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, póngase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		
		return resultado;
		
	}

	public boolean insertarCliente(Cliente clienteSel, Cuenta cuentaSel) {
		try{
			stmnt.executeUpdate(
					"INSERT INTO titular (id_cliente,id_cuenta) VALUES ("+
							clienteSel.id+", "+cuentaSel.id+")"
					);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, póngase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		return true;
	}

	public boolean quitarCliente(Cliente clienteSel, Cuenta cuentaSel) {
		try{
			stmnt.executeUpdate(
					"DELETE FROM titular WHERE id_cliente ="+
							clienteSel.id+" AND id_cuenta ="+cuentaSel.id
					);
		}catch(Exception e){
			e.printStackTrace();
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, póngase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			return false;
		}
		return true;
	}
	
}
