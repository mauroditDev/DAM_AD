package ejercicio_xstream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;

public class Ejercicio2 {

	public static void main(String[]args){
		
		XStream xstream = new XStream();
		xstream.alias("persona",Persona.class);
		xstream.alias("trabajo", Trabajo.class);
		xstream.alias("personas", Gente.class);
		Gente g = new Gente();
		g = (Gente) xstream.fromXML(new File("/home/alumno/vacia2/trabajos.xml"));
		
		Persona p1;
		p1 = g.personal.get(0);
		Persona p2;
		p2 = g.personal.get(1);
		Persona p3;
		p3 = g.personal.get(2);
		
		System.out.println(p1.toString());
		System.out.println(p2.toString());
		System.out.println(p3.toString());
		
	}
	
}
