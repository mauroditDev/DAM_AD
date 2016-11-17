package ejercicio_xstream;

import java.io.Serializable;
import java.util.ArrayList;

public class Persona implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6900918600617551696L;
	
	public int	edad; // >0
	public String nombre;
	public char sexo; //M - Masculino, F - Femenino
	public ArrayList<Trabajo> trabs;
	
	public Persona( int edad, String nombre,char sexo) {
		this.edad = edad;
		this.nombre	= nombre;
		this.sexo = sexo;
		this.trabs	= new ArrayList<Trabajo>();
	}
	
	public Persona() {
		
	}

	public
	String toString() {
		return "Persona [edad="	+ edad + ", nombre=" + nombre + ", trabs=" + trabs + ", sexo=" + sexo + "]";
	}
	
	public void	añadirTrabajo(Trabajo t){
		trabs.add(t);
	}
	
}
