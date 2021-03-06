package ud2p2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Ejercicio_8 {

	public static void main(String[] args) {
		
		File file = new File("/home/alumno/vacia2/personas.xml");
		file.delete();

		PersonaSegundo p1 = new PersonaSegundo(20,"Julio Perez",'M');
		p1.añadirTrabajo(new Trabajo(1,"Albanil"));
		p1.añadirTrabajo(new Trabajo(1,"Encofrador"));
		PersonaSegundo p2 = new PersonaSegundo(21,"Ana Gutierrez",'F');
		p2.añadirTrabajo(new Trabajo(2,"Ingeniero"));
		PersonaSegundo p3 = new PersonaSegundo(84,"Emilia Rivilla",'F');
		p3.añadirTrabajo(new Trabajo(2,"Profesora"));
		p3.añadirTrabajo(new Trabajo(3,"Locutora de radio"));
		
		cabeceraXml();
		grabarXml(p1);
		grabarXml(p2);
		grabarXml(p3);
		pieXml();
		
		leerXml();

	}
	
	public static void cabeceraXml(){
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(
					new File("/home/alumno/vacia2/personas.xml")));
			
			pw.write("<?xml version = \"1.0\" ?>\n");
			pw.write("<personas>\n");
			
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void pieXml(){
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileWriter(
					new File("/home/alumno/vacia2/personas.xml"),true));
			
			pw.write("</personas>\n");
			
			pw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void grabarXml(PersonaSegundo persona){
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(
				new File("/home/alumno/vacia2/personas.xml"),true));
			pw.write("\t<persona>\n");
			pw.write("\t\t<edad>\n");
			pw.write(String.valueOf(persona.edad));
			pw.write("\t\t</edad>\n");
			pw.write("\t\t<nombre>\n");
			pw.write(persona.nombre);
			pw.write("\t\t</nombre>\n");
			
			pw.write("\t\t<trabajos>\n");
			for(int i = 0; i< persona.trabs.size();i++){
				pw.write("\t\t\t<trabajo>\n");
				pw.write(persona.trabs.get(i).toString());
				pw.write("\t\t\t</trabajo>\n");
			}
			pw.write("\t\t</trabajos>\n");
			
			pw.write("\t\t<sexo>\n");
			pw.write(persona.sexo);
			pw.write("\t\t</sexo>\n");
			pw.write("\n");
			pw.write("\t</persona>\n");
			
			
			pw.write("\n");
			
			pw.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void leerXml(){
		
		try{
			File file = new File("/home/alumno/vacia2/personas.xml");
			BufferedReader br = new BufferedReader(new FileReader(file));
			PersonaSegundo per = new PersonaSegundo();
			String aux = br.readLine();
			while(aux != null){
				System.out.println(aux);
				if(aux.equals("\t<persona>")){
					per = new PersonaSegundo();
				}
				if(aux.equals("\t\t<edad>\n")){
					per.edad = Integer.parseInt(br.readLine());
				}
				if(aux.equals("\t\t<nombre>\n")){
					per.nombre = br.readLine().trim();
				}
				if(aux.equals("\t\t<nombre>\n")){
					//per.sexo = br.readLine().trim();
				}
				if(aux.equals("\t</persona>")){
					System.out.println(per.toString());
				}
				aux = br.readLine();
			}
			
			br.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
}
