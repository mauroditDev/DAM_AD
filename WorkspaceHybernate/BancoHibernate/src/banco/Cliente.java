package banco;

// Generated 07-mar-2017 9:02:27 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Cliente generated by hbm2java
 */
public class Cliente implements java.io.Serializable {

	private int id;
	private String nombre;
	private Date FNac;
	private String direccion;
	private Set cuentas = new HashSet(0);

	public Cliente() {
	}

	public Cliente(String nombre, Date FNac, String direccion, Set cuentas) {
		this.nombre = nombre;
		this.FNac = FNac;
		this.direccion = direccion;
		this.cuentas = cuentas;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFNac() {
		return this.FNac;
	}

	public void setFNac(Date FNac) {
		this.FNac = FNac;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Set getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(Set cuentas) {
		this.cuentas = cuentas;
	}

}