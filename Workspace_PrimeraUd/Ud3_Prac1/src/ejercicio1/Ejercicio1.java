package ejercicio1;

import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class Ejercicio1 {
   public static void main(String[] args){

      try {	
         File inputFile = new File("/home/alumno/vacia2/prueba.xml");
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXParser saxParser = factory.newSAXParser();
         MyHandler userhandler = new MyHandler();
         saxParser.parse(inputFile, userhandler);     
      } catch (Exception e) {
         e.printStackTrace();
      }
   }   
}
