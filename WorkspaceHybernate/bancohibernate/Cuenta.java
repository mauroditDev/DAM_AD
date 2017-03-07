package bancohibernate;

// Generated 09-feb-2017 6:49:01 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;

/**
 * Cuenta generated by hbm2java
 */
public class Cuenta implements java.io.Serializable {

	private Integer id;
	private Integer saldo;
	private Set clientes = new HashSet(0);
	private Set movimientos = new HashSet(0);

	public Cuenta() {
	}

	public Cuenta(Integer saldo, Set clientes, Set movimientos) {
		this.saldo = saldo;
		this.clientes = clientes;
		this.movimientos = movimientos;
	}

	public Cuenta(int s) {
		saldo=s;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Integer saldo) {
		this.saldo = saldo;
	}

	public Set getClientes() {
		return this.clientes;
	}

	public void setClientes(Set clientes) {
		this.clientes = clientes;
	}

	public Set getMovimientos() {
		return this.movimientos;
	}

	public void setMovimientos(Set movimientos) {
		this.movimientos = movimientos;
	}

	@Override
	public String toString() {
		String clis="[";
		for (Cliente c:(Set<Cliente>)getClientes())
			clis+=c.getNif()+" ";
		clis+="]";
		return "Cuenta [id=" + id + ", saldo=" + saldo + ", clientes="
				+ clis + ", movimientos=" + getMovimientos().size() + "]";
	}

}