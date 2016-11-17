package ejercicio_xstream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;

public class Ejercicio1 {

	public static void main(String[]args){
		XStream xstream = new XStream();
		xstream.alias("persona",Persona.class);
		xstream.alias("trabajo", Trabajo.class);
		xstream.alias("personas", Gente.class);
		Gente g = new Gente();
		Persona p1=new Persona(20,"Julio Perez",'M'); 
		p1.añadirTrabajo(new Trabajo(1,"Albanil")); 
		p1.añadirTrabajo(new Trabajo(1,"Encofrador")); 
		Persona p2=new Persona(21,"Ana Gutierrez",'F'); 
		p2.añadirTrabajo(new Trabajo(2,"Ingeniero")); 
		Persona p3=new Persona(84,"Emilia Rivilla",'F'); 
		p3.añadirTrabajo(new Trabajo(2,"Profesora")); 
		p3.añadirTrabajo(new Trabajo(3,"Locutora de radio"));
		g.personal.add(p1);
		g.personal.add(p2);
		g.personal.add(p3);
		String documento = xstream.toXML(g);
		try {
			PrintWriter pw = new PrintWriter(new FileWriter("/home/alumno/vacia2/trabajos.xml"));
			pw.write(documento);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
