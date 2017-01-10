package accesoCatalogo;

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

	public Connection con;
    public Statement stmnt;
    
    public DBmanager(String ip, String user, String pass){
       try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			if(ip.equals("")) ip = "jdbc:mysql://localhost/";
			if(user.equals("")) user = "root";
			if(pass.equals("")) pass = "root";
			con = DriverManager.getConnection(ip,user,pass);
			stmnt = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
    		javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
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
    
    public ArrayList<String> getTablasFromCat(String catalogo){
    	ArrayList<String> res = new ArrayList<>();
    	try {
			ResultSet rs = con.getMetaData().getTables(catalogo,null,"%",null);
			while(rs.next()){
				if(rs.getString("TABLE_TYPE").equals("TABLE"))
					res.add(rs.getString("TABLE_NAME"));
			}
			
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
					res.add(rs.getString(1));
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
	
}
