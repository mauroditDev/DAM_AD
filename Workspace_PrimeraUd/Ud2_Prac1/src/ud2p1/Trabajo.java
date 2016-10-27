package ud2p1;

import java.io.Serializable;

public class Trabajo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3470294736970520366L;
	
	int	tipo;
	String descripcion;
	
	public Trabajo( int	tipo, String descripcion) {
		this.tipo = tipo;
		this.descripcion = descripcion;
	}
	
	public String toString() {
		return "Trabajo [tipo=" + tipo + ", descripcion=" + descripcion + "]";
	}
	
}
