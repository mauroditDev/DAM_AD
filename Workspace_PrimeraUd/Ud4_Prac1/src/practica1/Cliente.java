package practica1;

public class Cliente {

	public Cliente(String id, String nombre, String fnac, String dir) {
		this.id = Integer.valueOf(id);
		this.nombre = nombre;
		this.f_nac = fnac;
		direccion = dir;
	}
	
	public Cliente(int id, String nombre, String fnac, String dir) {
		this.id = id;
		this.nombre = nombre;
		this.f_nac = fnac;
		direccion = dir;
	}
	
	public Cliente() {
		this.id = 0;
		this.nombre = "";
		this.f_nac = "";
		direccion = "";
	}
	
	public int id;
	public String f_nac;
	public String nombre;
	public String direccion;
	
}
