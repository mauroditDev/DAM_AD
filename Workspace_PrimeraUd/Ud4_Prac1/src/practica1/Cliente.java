package practica1;

import java.util.Date;

public class Cliente {

	public Cliente(String id, String nombre, Date fnac, String dir, String nif) {
		this.id = Integer.valueOf(id);
		this.nombre = nombre;
		this.f_nac = fnac;
		direccion = dir;
		this.nif = nif;
	}
	
	public Cliente(int id, String nombre, Date fnac, String dir, String nif) {
		this.id = id;
		this.nombre = nombre;
		this.f_nac = fnac;
		direccion = dir;
		this.nif = nif;
	}
	
	public Cliente() {
		this.id = -1;
		this.nombre = "";
		this.f_nac = new Date();
		this.nif = "";
		direccion = "";
	}
	
	public Integer id;
	public Date f_nac;
	public String nombre;
	public String direccion;
	public String nif;
	
}
