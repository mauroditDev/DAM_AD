package jdbcTablaCSV;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BaseDatos {

	private Connection con;

	public BaseDatos(String IP, String usuario, String passwd)
			throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(0);
		}

		con = DriverManager
				.getConnection("jdbc:mysql://" + IP, usuario, passwd);

	}

	public void desconectar() {

		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<String> dameBases() throws SQLException {

		ArrayList<String> l = new ArrayList<String>();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getCatalogs();
		while (res.next()) {

			l.add(res.getString("TABLE_CAT"));
		}
		res.close();
		return l;
	}

	public ArrayList<String> dameTablas(String base) throws SQLException {
		ArrayList<String> l = new ArrayList<String>();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getTables(base, null, "%",null);
		while (res.next()) {
			if (res.getString("TABLE_TYPE").equals("TABLE"))
				l.add(res.getString("TABLE_NAME"));
		}
		res.close();
		return l;
	}

	public ArrayList<String> dameColumnas(String base, String tabla) throws SQLException {
		ArrayList<String> l = new ArrayList<String>();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet res = meta.getColumns(base, null,tabla,"%");
		while (res.next()) {
			l.add(res.getString("COLUMN_NAME"));
		}
		res.close();
		return l;
	}

	public int exportarTablaCSV(String base, String tabla,String nombreCSV) throws SQLException, IOException {
		ArrayList<String> lista=dameColumnas(base,tabla);
		String nombre;
		if (nombreCSV==null) 
			nombre=base+"."+tabla+".csv"; //Nombre por defecto si no viene especificado
		else
			nombre=nombreCSV;
		PrintWriter pw=new PrintWriter(nombre);//Se crea fichero CSV
		//Generamos linea de cabecera del CSV conteniendo los nombres de columna 
		for(String col:lista)
		{
			pw.print(col+";");
		}
		pw.println();
		//Obtenemos todas las filas de la tabla
		Statement st=con.createStatement();
		String sql = "SELECT * FROM "+base+"."+tabla;
		ResultSet res=st.executeQuery(sql);
		int filas=0;
		while (res.next()) {
			for(String col:lista)//Por cada fila se recogen los valores de cada columna
			{
				pw.print(res.getString(col)+";");
			}
			pw.println();
			filas++;
		}
		res.close();
		
		pw.close();
		return filas;
	}

	public int importarCSVTabla(String base, String tabla, String rutaCSV) throws SQLException {
		if (!existeBD(base))
			crearBD(base);
		if (existeTabla(base,tabla))
			throw new SQLException("La tabla ya existe en la BD. No se pueden importar datos.");
		int cnt=0;
		BufferedReader f = null;
		try {
			// apertura del fichero
			f = new BufferedReader(new FileReader(rutaCSV));

			String linea;
			linea = f.readLine(); //Lee la primera linea (nombres de columna)
			crearTabla(base,tabla,linea);
			
			while ((linea = f.readLine()) != null) {
				insertarFila(base,tabla,linea);
				cnt++;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return cnt;
	}

	private void insertarFila(String base, String tabla, String valores) throws SQLException {
		//Insertar fila en tabla con los valores especificados: val1;val2;val3;...
		//Todas las columnas seran VARCHAR(255)
		Statement stm=con.createStatement();
		String sql="INSERT INTO "+base+"."+tabla+" VALUES (";
		StringTokenizer stk=new StringTokenizer(valores,";");
		int cols=stk.countTokens();
		for (int i=1;i<=cols;i++)
		{
			sql+="'"+stk.nextToken()+ "'";
			if (i==cols) //ultima columna
				sql+=")";
			else
				sql+=",";
		}
		stm.executeUpdate(sql);			
		
	}

	private void crearTabla(String base,String tabla, String columnas) throws SQLException {
		//Crea tabla con las columnas especificadas: col1;col2;col3;...
		//Todas las columnas seran VARCHAR(255)
		Statement stm=con.createStatement();
		String sql="CREATE TABLE "+base+"."+tabla+" (";
		StringTokenizer stk=new StringTokenizer(columnas,";");
		int cols=stk.countTokens();
		for (int i=1;i<=cols;i++)
		{
			sql+=stk.nextToken()+ " VARCHAR(255)";
			if (i==cols) //ultima columna
				sql+=")";
			else
				sql+=",";
		}
		stm.executeUpdate(sql);	
	}

	private boolean existeTabla(String base, String tabla) throws SQLException {
		ArrayList<String> lt= dameTablas(base);
		for (String t:lt)
			if (t.toLowerCase().equals(tabla.toLowerCase()))
				return true;
		return false;
	}

	private void crearBD(String base) throws SQLException {
		Statement st=con.createStatement();
		String sql="CREATE DATABASE "+base;
		st.executeUpdate(sql);	
	}

	private boolean existeBD(String base) throws SQLException {
		ArrayList<String> lbd= dameBases();
		for (String b:lbd)
			if (b.toLowerCase().equals(base.toLowerCase()))
				return true;
		return false;
	}

}