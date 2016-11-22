package practica1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;




public class DBmanager {
	public Connection con;
    public Statement stmnt;
    
    public DBmanager(){
           try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
           try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/almacen",
			           "root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
          
           

         
    }

}
