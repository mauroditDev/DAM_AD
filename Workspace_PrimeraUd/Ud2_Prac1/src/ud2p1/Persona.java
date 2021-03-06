package ud2p1;

import java.io.Serializable;

public class Persona implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2532554957846287933L;
	
	int	edad; // >0
	String nombre;
	char sexo; //M - Masculino, F - Femenino
	
	public Persona(int	edad, String nombre, char sexo) {
		this.edad = edad;
		this.nombre	= nombre;
		this.sexo = sexo;
	}
	
	public String toString() {
		return "Persona [edad="	+ edad + ", nombre=" + nombre + ", sexo=" + sexo + "]";
	}
	
}
