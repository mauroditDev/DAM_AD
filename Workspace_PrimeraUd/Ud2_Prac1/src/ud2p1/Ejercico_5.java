package ud2p1;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Ejercico_5 {

	public static void main(String[] args) {
		
		File file = new File("/home/alumno/vacia2/personas.dat");
		file.delete();
		file = new File("/home/alumno/vacia2/personas.obj");
		file.delete();
		file = new File("/home/alumno/vacia2/personas.txt");
		file.delete();

		Persona p1=new Persona(20, "Julio Perez", 'M');
		Persona p2=new Persona(21,"Ana Gutierrez",'F');
		Persona p3=new Persona(84,"Emilia Rivilla",'F');
		
		grabarTexto(p1);
		grabarTexto(p2);
		grabarTexto(p3);
		grabarBin(p1);
		grabarBin(p2);
		grabarBin(p3);
		ArrayList<Persona> pers = new ArrayList<Persona>();
		pers.add(p1);
		pers.add(p2);
		pers.add(p3);
		
		grabarObjeto(pers);
		
		leerTexto();
		leerBin();
		leerObjeto();

	}
	
	public static void grabarTexto(Persona persona){
		
		try{
			PrintWriter pw = new PrintWriter(new FileWriter(
				new File("/home/alumno/vacia2/personas.txt"),true));
			
			pw.write(String.valueOf(persona.edad));
			pw.write(persona.nombre);
			pw.write(persona.sexo);
			pw.write("\n");
			
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
	
	public static void grabarObjeto(ArrayList<Persona> persona){
		
		try{
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(
				new File("/home/alumno/vacia2/personas.obj")));
			
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
			File file = new File("/home/alumno/vacia2/personas.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			
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
			File file = new File("/home/alumno/vacia2/personas.dat");
			DataInputStream dataIn = new DataInputStream(new FileInputStream(file));
			try{
				while(true){
					System.out.println(dataIn.readInt());
					System.out.println(dataIn.readUTF());
					System.out.println(dataIn.readChar());
				}
			}catch(EOFException ex){
				dataIn.close();
			}
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void leerObjeto(){
		
		try{
			File file = new File("/home/alumno/vacia2/personas.obj");
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
			
			@SuppressWarnings("unchecked")
			ArrayList<Persona> res = (ArrayList<Persona>)objIn.readObject();
			if(res != null){
				for(int i = 0; i<res.size(); i++)
					System.out.println(res.get(i).toString());
			}
			
			
			objIn.close();
			
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
	}

}
