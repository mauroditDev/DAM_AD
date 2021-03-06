package ejercicio1;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table (name="Seguro")
public class Seguro {
	
	@Id
	@Column (name="id_seguro")
	private int IdSeguro;
	
	@Column
	private String nif;
	
	@Column
	private String nombre;
	
	@Column
	private String ape1;
	
	@Column
	private String ape2;
	
	@Column
	private int edad;
	
	@Column (name="num_hijos")
	private int numHijos;
	
	@Column (name="fecha_alta")
	@Type (type="timestamp")
	private Date fechaCreacion;
	
	public Seguro(){}
	
	public Seguro(int IdSeguro, String nif, String nombre, String ape1, String ape2, int edad, int numHijos, Date fechaCreacion){
		this.IdSeguro = IdSeguro;
		this.nif = nif;
		this.nombre = nombre;
		this.ape1 = ape1;
		this.ape2 = ape2;
		this.edad = edad;
		this.numHijos = numHijos;
		this.fechaCreacion = fechaCreacion;
	}
	
	
	
	public int getIdSeguro() {
		return IdSeguro;
	}
	public void setIdSeguro(int idSeguro) {
		IdSeguro = idSeguro;
	}
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
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
	public int getEdad() {
		return edad;
	}
	public void setEdad(int edad) {
		this.edad = edad;
	}
	public int getNumHijos() {
		return numHijos;
	}
	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
}
