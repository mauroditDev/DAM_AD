package practica1;

public class Sucursal {

	public Sucursal(String id, String cp, String dir) {
		this.cp = cp;
		this.direccion = dir;
		this.idsucursal = Integer.valueOf(id);
	}
	public Sucursal(int id, String cp, String dir) {
		this.cp = cp;
		this.direccion = dir;
		this.idsucursal = id;
	}
	public Sucursal() {
		this.cp = "";
		this.direccion = "";
		this.idsucursal = 0;
	}
	public int idsucursal;
	public String direccion;
	public String cp;
	
}
