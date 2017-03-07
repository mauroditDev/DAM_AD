package bancohibernate;

// Generated 09-feb-2017 6:49:01 by Hibernate Tools 3.4.0.CR1

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Cliente generated by hbm2java
 */
public class Cliente implements java.io.Serializable {

	private Integer id;
	private String nombre;
	private Date FNac;
	private String direccion;
	private String nif;
	private Set cuentas = new HashSet(0);

	public Cliente() {
	}

	public Cliente(String nombre, Date FNac, String direccion, String nif) {
		this.nombre = nombre;
		this.FNac = FNac;
		this.direccion = direccion;
		this.nif = nif;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
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

	public String getNif() {
		return this.nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public Set getCuentas() {
		return this.cuentas;
	}

	public void setCuentas(Set cuentas) {
		this.cuentas = cuentas;
	}

	@Override
	public String toString() {
		String ctas="[";
		for (Cuenta c:(Set<Cuenta>)getCuentas())
			ctas+=c.getId()+" ";
		ctas+="]";
		return "Cliente [id=" + id + ", nombre=" + nombre + ", FNac=" + new SimpleDateFormat("dd-MM-yyyy").format(getFNac())
				+ ", direccion=" + direccion + ", nif=" + nif + ", cuentas="
				+ ctas + "]";
	}

}
