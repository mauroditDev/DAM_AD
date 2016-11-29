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

	public boolean getClient(String id, ArrayList<String> resultado) {
		boolean res = true;
		try{
			ResultSet rs =
			stmnt.executeQuery(
					"SELECT nombre, f_nac, direccion FROM cliente WHERE id = "+
						id
					);
			rs.next();
			for(int i=1;i<4;i++){
				resultado.add(rs.getString(i));
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

	public boolean modCliente(String id, String nombre, String f_nac,
			String direccion) {
		boolean res = true;
		try{
			stmnt.executeUpdate(
					"UPDATE cliente SET "
					+ "nombre = '"+nombre+"', "
					+ "f_nac = '"+f_nac+"', "
					+ "direccion = '"+direccion+"'"
					+ " WHERE id = "+id
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
}
