package ud2p1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class ArchivoPersonas {

	public static void main(String[] args) {
		

		Persona p1=new Persona(20, "Julio Perez", 'M');
		Persona p2=new Persona(21,"Ana Gutierrez",'F');
		Persona p3=new Persona(84,"Emilia Rivilla",'F');
		
		grabarTexto(p1);
		grabarTexto(p2);
		grabarTexto(p3);
		grabarBin(p1);
		grabarBin(p2);
		grabarBin(p3);
		grabarObjeto(p1);
		grabarObjeto(p2);
		grabarObjeto(p3);
		
		leerTexto();
		leerBin();
		leerObjeto();

	}
	
	public static void grabarTexto(Persona persona){
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(
				new File("/home/alumno/vacia2/personas.txt"),true));
			
			pw.write(persona.edad);
			pw.write(persona.nombre);
			pw.write(persona.sexo);
			
			pw.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void grabarBin(Persona persona){
		
		try{
			DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(
				new File("/home/alumno/vacia2/personas.dat"),true));
			
			dataOut.writeInt(persona.edad);
			dataOut.writeUTF(persona.nombre);
			dataOut.writeChar(persona.sexo);
			
			dataOut.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void grabarObjeto(Persona persona){
		
		try{
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(
				new File("/home/alumno/vacia2/personas.obj"),true));
			
			objOut.writeObject(persona);
			
			objOut.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void leerTexto(){
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(
					new File("/home/alumno/vacia2/personas.txt")));
			
			String aux = br.readLine();
			while(aux != null){
				System.out.println(aux);
				aux = br.readLine();
			}
			
			br.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void leerBin(){
		
		try{
			DataInputStream dataIn = new DataInputStream(new FileInputStream(
				new File("/home/alumno/vacia2/personas.dat")));
			
			while(dataIn.available() > 0){
				System.out.println(dataIn.readInt());
				System.out.println(dataIn.readUTF());
				System.out.println(dataIn.readChar());
			}
			
			dataIn.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void leerObjeto(){
		
		try{
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(
				new File("/home/alumno/vacia2/personas.obj") ));
			
			Persona res = (Persona)objIn.readObject();
			while(res!=null){
				System.out.println(res.toString());
				res = (Persona)objIn.readObject();
			}
			
			objIn.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
