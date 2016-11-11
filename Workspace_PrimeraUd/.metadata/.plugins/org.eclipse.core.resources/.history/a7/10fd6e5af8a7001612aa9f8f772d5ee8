package ejercicio3;

import java.io.File;
import java.io.RandomAccessFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Document;

public class Ejercicio3 {

	public static int inicial;
	
	public static void main(String[] args) {
		try{
			File biblioteca = new File("/home/alumno/Biblioteca");
			File[] archivos = biblioteca.listFiles();
			File catalogo = new File("/home/alumno/vacia2/biblioteca.xml");
			
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
	        DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
	        Document doc = docBuilder.newDocument();
			
	        Element root = doc.createElement("biblioteca");
            doc.appendChild(root);
			
			for(int i = 0; i< archivos.length; i++){
				xmlLibro(archivos[i],doc,root);
			}
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(catalogo);
			transformer.transform(source, result);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void xmlLibro(File origen, Document doc, Element root){
		try{
			RandomAccessFile raf = new RandomAccessFile(origen,"rw");
			long pointer = 0;
			int lineas = 1;
			int i=0;
			Element libro = doc.createElement("libro");
			libro.setAttribute("nombre", origen.getName());
			while(raf.readLine()!=null)
				i++;
			Element[] linea = new Element[i];
			raf.seek(0);
			while(raf.readLine()!=null){
				linea[lineas-1] = doc.createElement("linea");
				linea[lineas-1].setAttribute("num", String.valueOf(lineas));
				linea[lineas-1].setTextContent(String.valueOf(pointer));
				pointer = raf.getFilePointer();
				libro.appendChild(linea[lineas-1]);
				lineas++;
			}
			root.appendChild(libro);
			raf.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
