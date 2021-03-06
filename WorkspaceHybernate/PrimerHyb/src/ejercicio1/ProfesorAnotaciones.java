package ejercicio1;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table (name="Profesor")
public class ProfesorAnotaciones implements Serializable  {

	@Id
	@Column (name="id")
	private int id;
	
	@Column
	private String nombre;
	
	@Column (name="ape_1")
	private String ape1;
	
	@Column (name="ape_2")
	private String ape2;
	
	public ProfesorAnotaciones(){}

	public ProfesorAnotaciones(int id, String nombre, String ape1, String ape2) {
		this.id = id;
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
	}

	
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
}