package ejercicio1;

import java.io.Serializable;
import java.util.List;
import java.util.Set;




public class Profesor implements Serializable  {


	private String nombre;
	private String ape1;
	private String ape2;
	private List<Correo> correos;


	public Profesor(){}

	public Profesor(int id, String nombre, String ape1, String ape2, List correos) {
		this.id = id;
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
		this.correos = correos;
		
	}

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApe1() {
		return ape1;
	}

	public void setApe1(String ape1) {
		this.ape1 = ape1;
	}

	public String getApe2() {
		return ape2;
	}

	public void setApe2(String ape2) {
		this.ape2 = ape2;
	}

	public List<Correo> getCorreos() {
		return correos;
	}

	public void setCorreos(List<Correo> correos) {
		this.correos = correos;
	}
	
}