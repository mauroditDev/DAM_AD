package accesoCatalogo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DBmanager {

	public Connection con;
    public Statement stmnt;
    
    public DBmanager(String ip, String user, String pass, int tipo)throws Exception{
       try {
    	   switch(tipo){
    	   case 0:
    		   Class.forName("com.mysql.jdbc.Driver").newInstance();
    		//   if(ip.equals("")) ip = "jdbc:mysql://localhost/";
    		   ip = "jdbc:mysql://"+ip+"/";
    		  // if(user.equals("")) user = "root";
    		  // if(pass.equals("")) pass = "root";
    		   con = DriverManager.getConnection(ip,user,pass);
   			stmnt = con.createStatement();
				break;
    	   case 1:
    		   Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
    		   ip = "jdbc:derby:"+ip+";create=true";
    		   con = DriverManager.getConnection(ip);
    	   case 2:
    		   Class.forName("net.ucanacces.jdbc.UcanaccessDriver").newInstance();
    		   ip = "jdbc:ucanaccess://"+ip+";showSchema=true";
    		   con = DriverManager.getConnection(ip);
    	   }
			
		} catch (Exception e) {
			e.printStackTrace();
    		javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
    		throw new Exception(e.getMessage());
		} 
    }
    
    public ArrayList<String> getCatalogos(){
    	ArrayList<String> res = new ArrayList<>();
    	try {
			ResultSet rs = con.getMetaData().getCatalogs();
			while(rs.next()){
				res.add(rs.getString("TABLE_CAT"));
			}
			
		} catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	return res;
    }
    
    public ArrayList<String> getTablasFromCat(String catalogo, int tipo){
    	ArrayList<String> res = new ArrayList<>();
    	try {
    		ResultSet rs = null;
    		switch(tipo){
    		case 0:
    			rs = con.getMetaData().getTables(catalogo,null,"%",null);
    			break;
    		case 1:
    			rs = con.getMetaData().getTables(null,catalogo.toUpperCase(),"%",null);
    			break;
    		}
			 
			 if(rs != null){
				while(rs.next()){
					System.out.println(rs.getString("TABLE_NAME"));
					if(rs.getString("TABLE_TYPE").equals("TABLE"))
						res.add(rs.getString("TABLE_NAME"));
				}
			 }
			 else
				 return res;
			
		} catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	return res;
    }
    
    public ArrayList<String> getColumnasFromTabCat(String catalogo, String tabla){
    	ArrayList<String> res = new ArrayList<>();
    	try {
			ResultSet rs = con.getMetaData().getColumns(catalogo, null, tabla, null);
			while(rs.next()){
				res.add(rs.getString("COLUMN_NAME"));
			}
			
		} catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	return res;
    }
    
    public ArrayList<String> getValoresFromTabCat(String catalogo, String tabla, int columnas){
    	ArrayList<String> res = new ArrayList<>();
    	try {
			Statement stmnt = con.createStatement();
			ResultSet rs = stmnt.executeQuery("select * from "+catalogo+"."+tabla);
			while(rs.next()){
				for(int i = 1; i <=columnas; i++)
					res.add(rs.getString(i));
			}
			
		} catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	return res;
    }

	public void crearDatabase(String catalogo) {
		try{
			Statement stmnt = con.createStatement();
			stmnt.executeUpdate("create database "+catalogo);
		}catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void crearTabla(File archivo, int tipo) {
		try{
			String[] nombres = null;
			Statement stmnt= null;
			String crearTabla= null;
			String linea = null;
			String[] cols = null;
			
			
			switch(tipo){
			case 0:
			case 1:
				nombres = archivo.getName().split("[.]");
				System.out.println("archivo: "+archivo.getName());
				stmnt = con.createStatement();
				crearTabla = "create table "+nombres[0]+"."+nombres[1] +" (";
				
				BufferedReader br = new BufferedReader(new FileReader(archivo));
				
				String[] cols = br.readLine().split(";"); 
				
				for(int i = 0; i<cols.length; i++){
					crearTabla += cols[i];
					crearTabla += " varchar(255)";
					if(i<cols.length-1){
						crearTabla += ", ";
					}
				}
				
				crearTabla += ")";
				System.out.println(crearTabla);
				stmnt.executeUpdate(crearTabla);
				String linea = br.readLine();
				
				while(linea != null){
					cols = linea.split(";"); 
					String sql = "insert into "+nombres[0]+"."+nombres[1]+" values (";
					for(int i = 0; i<cols.length; i++){
						sql += "'"+cols[i]+"'";
						if(i<cols.length-1){
							sql += ", ";
						}
					}
					sql += ")";
					stmnt.executeUpdate(sql);
					linea = br.readLine(); 
				}
				
				br.close();
				break;
			case 2:
				String[] nombres = archivo.getName().split("[.]");
				System.out.println("archivo: "+archivo.getName());
				Statement stmnt = con.createStatement();
				String crearTabla = "create table "+nombres[0]+"."+nombres[1] +" (";
				
				BufferedReader br = new BufferedReader(new FileReader(archivo));
				
				String[] cols = br.readLine().split(";"); 
				
				for(int i = 0; i<cols.length; i++){
					crearTabla += cols[i];
					crearTabla += " varchar(255)";
					if(i<cols.length-1){
						crearTabla += ", ";
					}
				}
				
				crearTabla += ")";
				System.out.println(crearTabla);
				stmnt.executeUpdate(crearTabla);
				String linea = br.readLine();
				
				while(linea != null){
					cols = linea.split(";"); 
					String sql = "insert into "+nombres[0]+"."+nombres[1]+" values (";
					for(int i = 0; i<cols.length; i++){
						sql += "'"+cols[i]+"'";
						if(i<cols.length-1){
							sql += ", ";
						}
					}
					sql += ")";
					stmnt.executeUpdate(sql);
					linea = br.readLine(); 
				}
				
				br.close();
				break;
			}
			
		}catch (Exception e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
		
	}

	public ArrayList<String> getEsquemas() {
		ArrayList<String> res = new ArrayList<>();
    	try {
			ResultSet rs = con.getMetaData().getSchemas();
			while(rs.next()){
				res.add(rs.getString("TABLE_SCHEM"));
			}
			
		} catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
    	return res;
	}

	public void crearEsquema(String esquema) {
		try{
			Statement stmnt = con.createStatement();
			stmnt.executeUpdate("create schema "+esquema);
		}catch (SQLException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
		}
		
	}
	
}
