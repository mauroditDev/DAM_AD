package examen;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

public class Examen_1 {

	public static void main(String[] args) {
		File biblioteca = new File("/home/alumno/Biblioteca");
		File[] archivos = biblioteca.listFiles();
		File catalogo = new File("/home/alumno/vacia2/catalogo.xml");
		xmlCabecera(catalogo);
		for(int i = 0; i< archivos.length; i++){
			xmlLibro(archivos[i],catalogo);
		}
		xmlPie(catalogo);

	}
	
	public static void xmlLibro(File origen, File destino){
		try{
			RandomAccessFile raf = new RandomAccessFile(origen,"rw");
			PrintWriter pw = new PrintWriter(new FileWriter(destino,true));
			long pointer = 0;
			int lineas = 1;
			pw.write("\t<libro titulo=\""+origen.getName()+"\" >\n");
			while(raf.readLine()!=null){
				pw.write("\t\t<linea num= \""+lineas+"\" >");
				pw.write(String.valueOf(pointer));
				pw.write("</linea>\n");
				pointer = raf.getFilePointer();
				lineas++;
			}
			pw.write("\t</libro>\n");
			pw.close();
			raf.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void xmlCabecera(File file){
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			pw.write("<biblioteca>\n");
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void xmlPie(File file){
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(file,true));
			pw.write("</biblioteca>\n");
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
