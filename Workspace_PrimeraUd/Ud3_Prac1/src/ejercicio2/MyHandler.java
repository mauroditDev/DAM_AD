package ejercicio2;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {

	public int linea;
	public boolean encontrado;
	public boolean lineaEn;
	boolean lectura;
	public String titulo;
	public String index;

   @Override
   public void startElement(String uri, 
   String localName, String qName, Attributes attributes)
      throws SAXException {
	   if(qName.equalsIgnoreCase("libro")){
		   if(attributes.getValue(0).equals(titulo)){
			   lectura = true;
		   }
	   
	   }
	   if(lectura){
		   if(qName.equalsIgnoreCase("linea")&&
				   attributes.getValue(0).equals(String.valueOf(linea))){
			   lineaEn = true;
			   encontrado = true;
		   }
	   }
	   
   }

   @Override
   public void endElement(String uri, 
   String localName, String qName) throws SAXException {
	   lineaEn = false;
	   if(qName.equalsIgnoreCase("libro")){
		   lectura = false;
	   }
      
   }

   @Override
   public void characters(char ch[], 
      int start, int length) throws SAXException {
      
	   if(lineaEn){
		   index = new String(ch,start,length).trim();
		   //System.out.println(index);
		   lineaEn = false;
	   }
	   
   }
   
}