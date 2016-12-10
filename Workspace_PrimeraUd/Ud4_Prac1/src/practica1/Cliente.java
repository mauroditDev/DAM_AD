package practica1;

public class Cliente {

	public Cliente(String id, String nombre, String fnac, String dir, String nif) {
		this.id = Integer.valueOf(id);
		this.nombre = nombre;
		this.f_nac = fnac;
		direccion = dir;
		this.nif = nif;
	}
	
	public Cliente(int id, String nombre, String fnac, String dir, String nif) {
		this.id = id;
		this.nombre = nombre;
		this.f_nac = fnac;
		direccion = dir;
		this.nif = nif;
	}
	
	public Cliente() {
		this.id = -1;
		this.nombre = "";
		this.f_nac = "";
		this.nif = "";
		direccion = "";
	}
	
	public Integer id;
	public String f_nac;
	public String nombre;
	public String direccion;
	public String nif;
	
}
