package ejercicio4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;


public class Ejercicio4 {
	
	public static void main(String[] args) {

	      try {	
	         File inputFile = new File("/home/alumno/vacia2/biblioteca.xml");
	         
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         Scanner sc = new Scanner(System.in);
	         System.out.println("Título del libro?");
	         
	         String titulo = sc.nextLine();
	         System.out.println("Línea?");
	         String linea = sc.nextLine();
	         
	         XPath xPath =  XPathFactory.newInstance().newXPath();
	         
	         RandomAccessFile raf = new RandomAccessFile("/home/alumno/Biblioteca/"+titulo+".txt","r");

	         String expression = "//libro[@nombre=\""+titulo+".txt\"]"+"/linea[@num=\""+linea+"\"]";

	         NodeList nodeList = 
	        		 (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
	         if(nodeList.getLength() == 0){
	        	 System.out.println("la línea no existe");
	         }
	         else{
	        	 String punteroS = nodeList.item(0).getTextContent();
	        	 long puntero = Long.valueOf(punteroS);
	        	 raf.seek(puntero);
	        	 System.out.println(raf.readLine());
	         }
	         
	         raf.close();
	         sc.close();
	         
	         
	      } catch(FileNotFoundException ex){
	    	  System.out.println("el libro no existe");
	      }catch (Exception e) {
	         e.printStackTrace();
	      }

	}
	
}
