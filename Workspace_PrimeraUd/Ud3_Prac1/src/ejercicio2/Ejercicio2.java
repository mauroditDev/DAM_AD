package ejercicio2;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Scanner;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Ejercicio2 {

	public static void main(String[] args) {

	      try {	
	         File inputFile = new File("/home/alumno/vacia2/biblioteca.xml");
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         SAXParser saxParser = factory.newSAXParser();
	         MyHandler userhandler = new MyHandler();
	         Scanner sc = new Scanner(System.in);
	         System.out.println("Título del libro?");
	         String titulo = sc.nextLine();
	         userhandler.titulo = titulo +".txt"; 
	         System.out.println("Línea?");
	         userhandler.linea = Integer.parseInt(sc.nextLine());
	         sc.close();
	         saxParser.parse(inputFile, userhandler);     
	         if(userhandler.encontrado){
	        	 RandomAccessFile raf = new RandomAccessFile("/home/alumno/Biblioteca/"+titulo+".txt","rw");
	        	 int index = Integer.parseInt(userhandler.index);
	        	 raf.seek(index);
	        	 System.out.println(raf.readLine());
	        	 raf.close();
	         }
	         else{
	        	 System.out.println("no existe el libro o la línea");
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }

	}

}
