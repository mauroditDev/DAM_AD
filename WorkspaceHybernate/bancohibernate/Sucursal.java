package bancohibernate;

// Generated 09-feb-2017 6:49:01 by Hibernate Tools 3.4.0.CR1

/**
 * Sucursal generated by hbm2java
 */
public class Sucursal implements java.io.Serializable {

	private Integer idsucursal;
	private String direccion;
	private String cp;

	public Sucursal() {
	}

	public Sucursal(String direccion, String cp) {
		this.direccion = direccion;
		this.cp = cp;
	}

	public Integer getIdsucursal() {
		return this.idsucursal;
	}

	public void setIdsucursal(Integer idsucursal) {
		this.idsucursal = idsucursal;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCp() {
		return this.cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

}