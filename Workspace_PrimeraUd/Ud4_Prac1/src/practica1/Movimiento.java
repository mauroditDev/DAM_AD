package practica1;

public class Movimiento {
	public int id;
	public long f_h;
	public int importe;
	public Cuenta cuenta;
	
	public Movimiento(){
		id = importe = -1;
		f_h = 0;
		cuenta = new Cuenta();
	}
	
}
