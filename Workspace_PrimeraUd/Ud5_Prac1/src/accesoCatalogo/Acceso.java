package accesoCatalogo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Acceso {
	boolean importarCsv(String ip, String user, String pass, File archivo){
		if(!archivo.exists())
			return false;
		String[] strings = archivo.getName().split(".");
		if(strings.length != 3 || !strings[2].equals("csv"))
			return false;
		
		DBmanager db = new DBmanager(ip,user,pass);
		boolean existe = false;
		for(String catalogo: db.getCatalogos()){
			if(strings[0].equals(catalogo))
				existe = true;
		}
		if(!existe)
			db.crearDatabase(strings[0]);
		else{
			existe = false;
			for(String tabla: db.getTablasFromCat(strings[0])){
				if(strings[1].equals(tabla))
					existe = true;
			}
		}
		
		return true;
		
	}
	
	File exportarCsv(String ip, String user, String pass, String catalogo, String tabla){
		File archivo = new File("./"+catalogo+"."+tabla+".csv");
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(archivo));
			DBmanager db = new DBmanager(ip, user, pass);
			ArrayList<String> datos = db.getColumnasFromTabCat(catalogo, tabla);
			int cols = datos.size();
			for(int i = 0; i<cols;i++){
				pw.write(datos.get(i));
				if(i<cols-1) pw.write(";");
				else pw.write("\n");
			}
			
			datos = db.getValoresFromTabCat(catalogo, tabla, cols);
			for(int i = 0, j = 0; i< datos.size(); i++, j++){
				pw.write(datos.get(i));
				if(j<cols-1) pw.write(";");
				else{
					pw.write("\n");
					j = 0;
				}
			}
			
			pw.close();
			
		} catch (IOException e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
		return archivo;
	}
}
