package practica1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
			res = false;
		}
		return res;
	}

	public boolean addCliente(String nombre, String fecha, String direccion) {
		boolean res = true;
		try{
			stmnt.executeUpdate(
					"INSERT INTO cliente (nombre,f_nac,direccion) VALUES"
					+ "( '"+nombre+"', "
					+ " '"+fecha+"', "
					+ "'"+direccion+"')"
					);
		}catch(Exception e){
			res = false;
		}
		return res;
	}
}
