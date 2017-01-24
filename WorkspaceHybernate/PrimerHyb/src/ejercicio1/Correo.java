package ejercicio1;

import java.io.Serializable;

public class Correo implements Serializable {
     public int getIdCorreo() {
		return idCorreo;
	}

	public void setIdCorreo(int idCorreo) {
		this.idCorreo = idCorreo;
	}

	public String getDireccionCorreo() {
		return direccionCorreo;
	}

	public void setDireccionCorreo(String direccionCorreo) {
		this.direccionCorreo = direccionCorreo;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	private int idCorreo;
     private String direccionCorreo;
     private Profesor profesor;

     public Correo() {

     }

     public Correo(int idCorreo,String direccionCorreo,Profesor profesor) {
         this.idCorreo=idCorreo;
         this.direccionCorreo=direccionCorreo;
         this.profesor=profesor;
     }
 }