package ejercicio_tablas;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

public class DBmanager {
	public Connection con;
    public Statement stmnt;
    
    public DBmanager(){
       
		try {
			
			con = DriverManager.getConnection("jdbc:mysql://localhost/","root","root");
			stmnt = con.createStatement();
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			
		}
		
    }
    
    public String getEsquema(String databaseName){
    	
    	try{
    		boolean sw = false;
    		String result = "";
    		DatabaseMetaData dbmd = con.getMetaData();
    		ResultSet rs = dbmd.getCatalogs();
    		while(rs.next()){
    			if(rs.getString("TABLE_CAT").equals(databaseName)){
    				
    				sw = true;
    				
    				ResultSet rs2 = dbmd.getTables(rs.getString("TABLE_CAT"), null, "%", null);
    				while(rs2.next()){
    					result +="CREATE TABLE "+
    							rs2.getString("TABLE_NAME")+
    							"(\n";
    					ResultSet rs3 = dbmd.getColumns(rs.getString("TABLE_CAT"), null, rs2.getString("TABLE_NAME"), "%");
    					while(rs3.next()){
    						result+= "\t" + rs3.getString("COLUMN_NAME")+ " ";
    						
    						switch(rs3.getInt("DATA_TYPE")){
    						case 4:
    							result += "INTEGER ";
    							break;
    						case 12:
    							result += "VARCHAR ";
    							break;
    						case 91:
    							result += "DATE ";
    							break;
    						case 93:
    							result += "DATETIME ";
    							break;
    						}
    								
    						result += "(" + rs3.getString("COLUMN_SIZE")+ ")" + ", \n";
    						
    					}
    					result += ");\n";
    				}
    				
    				
    			}
    		}
    		if(!sw){
    			return "esa base de datos no existe en local";
    		}
    		else
    			return result;
    		
    	} catch (Exception e){
    		e.printStackTrace();
    		return "no ha sido posible establecer la conexion";
    	}
    }
    
    
    
}
