package ejercicio1;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;


public class Modulo implements Serializable {
    private int idModulo;

	public int getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Profesor> getProfesores() {
		return profesores;
	}

	public void setProfesores(Set<Profesor> profesores) {
		this.profesores = profesores;
	}

    private String nombre;

     private Set<Profesor> profesores = new HashSet<>();

     public Modulo() {

     }

     public Modulo(int idModulo, String nombre) {
         this.idModulo = idModulo;
         this.nombre = nombre;

     }
}