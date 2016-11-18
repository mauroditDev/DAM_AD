package ejercicio_desayunos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Desayunos {
	public static void main(String[] args) {

	      try {	
	         File inputFile = new File("/home/alumno/Desayunos/desayunos.xml");
	         File outputFile = new File("/home/alumno/Desayunos/desayunos-mod.xml");
	         
	         DocumentBuilderFactory dbFactory 
	            = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         
	         ArrayList<String> divisa = new ArrayList<>();
	         ArrayList<Float> valor = new ArrayList<>();
	         
	         Node raiz = doc.getChildNodes().item(0);
	         double calorias = 0;
	         
	         BufferedReader br = new BufferedReader(new FileReader("/home/alumno/Desayunos/Divisas.csv"));
	         String linea = br.readLine();
	         
	         while(linea!=null){
	        	 String[] campos = linea.split(";");
	        	 divisa.add(campos[0]);
	        	 valor.add(Float.valueOf(campos[1]));
	        	 linea = br.readLine();
	         }
	         
	         br.close();
	         
	         for (int i = 0; i < raiz.getChildNodes().getLength(); i++){
	        	 
	    		 Node desayuno = raiz.getChildNodes().item(i);
	    		 
	    		 if(desayuno.getNodeType()==Node.ELEMENT_NODE){
	        		for(int j = 0; j< desayuno.getChildNodes().getLength(); j++){
	        			
	        			Node nodo = desayuno.getChildNodes().item(j);
	        			
	        			if(nodo.getNodeType()==Node.ELEMENT_NODE){
							 if(nodo.getNodeName().equals("calorias")){
				        		 calorias += Double.valueOf(nodo.getTextContent());
				        	 }
			        		 if(nodo.getNodeName().equals("descripcion")){
				        		 nodo.getParentNode().removeChild(nodo);
				        	 }
			        		 if(nodo.getNodeName().equals("precio")){
			        			 Float precio = Float.valueOf(nodo.getTextContent()); 
			        			 String moneda = nodo.getAttributes().item(0).toString();
			        			 moneda = moneda.substring(moneda.indexOf('"')+1, moneda.lastIndexOf('"'));
			        			 for(int k = 0; k<divisa.size(); k++){
			        				 if(divisa.get(k).equals(moneda)){
			        					 precio = precio / valor.get(k);
			        				 }
			        			 }
			        			 nodo.getAttributes().item(0).setTextContent("euro");
			        			 nodo.setTextContent(precio.toString());
			        		 }
			        		 
		        		}
	        			
	        		}
	        		 
	    		 }
	    		 
	         }
	         Element totalCalorias = doc.createElement("total_calorias");
    		 totalCalorias.setTextContent(String.valueOf(calorias));
    		 raiz.appendChild(totalCalorias);
             
             // write the content into xml file
             TransformerFactory transformerFactory = TransformerFactory.newInstance();
             Transformer transformer = transformerFactory.newTransformer();
             DOMSource source = new DOMSource(doc);
             StreamResult result = new StreamResult(outputFile);
             transformer.transform(source, result);
	         
	         
	      }catch (Exception e) {
	         e.printStackTrace();
	      }
	}
}
