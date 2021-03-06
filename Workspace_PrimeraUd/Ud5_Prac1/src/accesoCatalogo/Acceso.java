package accesoCatalogo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Acceso {
	
	boolean importarCsv(String ip, String user, String pass, int tipo, File archivo){
		try{
		if(!archivo.exists())
			return false;
		String[] strings = archivo.getName().split("[.]");
		if(strings.length != 3 || !strings[2].equals("csv"))
			return false;
		System.out.println("ip:"+ip+", user:"+user+", pass: "+pass+", "+tipo);
		DBmanager db = new DBmanager(ip,user,pass,tipo);
		boolean existe = false;
		switch(tipo){
		case 0:
			for(String catalogo: db.getCatalogos()){
				if(strings[0].toLowerCase().equals(catalogo.toLowerCase()))
					existe = true;
			}
			break;
		case 1:
			for(String catalogo: db.getEsquemas()){
				if(strings[0].toLowerCase().equals(catalogo.toLowerCase()))
					existe = true;
			}
			break;
		case 2:
			existe = true;
			break;
		}
		if(!existe){
			switch(tipo){
			case 0:
				db.crearDatabase(strings[0]);
				break;
			case 1:
				db.crearEsquema(strings[0]);
				break;
			}
		}
			
		//else{
		existe = false;
		for(String tabla: db.getTablasFromCat(strings[0],tipo)){
			System.out.println("tabla: "+tabla);
			if(strings[1].toLowerCase().equals(tabla.toLowerCase())){
				existe = true;
			}
		}
		if(existe){
			return false;
		}
		else{
			db.crearTabla(archivo);
		}
		//}
		
		return true;
		}catch (Exception e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
	}
	
	File exportarCsv(String ip, String user, String pass, int tipo, String catalogo, String tabla){
		File archivo = new File(catalogo+"."+tabla+".csv");
		try {
			PrintWriter pw = new PrintWriter(new FileWriter(archivo));
			DBmanager db = new DBmanager(ip, user, pass,tipo);
			ArrayList<String> datos = db.getColumnasFromTabCat(catalogo, tabla);
			int cols = datos.size();
			for(int i = 0; i<cols;i++){
				pw.write(datos.get(i));
				if(i<cols-1) pw.write(";");
				else pw.write("\n");
			}
			
			datos = db.getValoresFromTabCat(catalogo, tabla, cols);
			System.out.println(datos.size());
			for(int i = 0, j = 0; i< datos.size(); i++, j++){
				pw.write(datos.get(i));
				if(j%cols ==0) j=0;
				if(j<cols-1) pw.write(";");
				else{
					pw.write("\n");
				}
			}
			
			pw.close();
			
		} catch (Exception e) {
			javax.swing.JOptionPane.showConfirmDialog(null, "message: "+e.getMessage(),
					"Excepción grave, pongase en contacto con un técnico",
					javax.swing.JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
			return null;
		}
		return archivo;
	}
}
