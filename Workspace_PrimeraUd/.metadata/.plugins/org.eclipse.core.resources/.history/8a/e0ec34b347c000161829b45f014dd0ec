package practica1;

import java.util.ArrayList;

public class Cuenta {
	
	public Cuenta(ArrayList<Integer> titulares, int id, int saldo){
		this.titulares = titulares;
		this.id = id;
		this.saldo = saldo;
	}
	public Cuenta(String titulares, int id, int saldo){
		
		ArrayList<Integer> tit = new ArrayList<>();
		
		String [] aux = titulares.split(",");
		System.out.println(aux.length);
		for(int i = 0; i<aux.length; i++){
			tit.add(Integer.valueOf(aux[i]));
		}
		
		
		this.titulares = tit;
		this.id = id;
		this.saldo = saldo;
	}
	
	public Cuenta(String titulares, String id, String saldo){
		ArrayList<Integer> tit = new ArrayList<>();
		
		String [] aux = titulares.split(",");
		for(int i = 0; i<aux.length; i++){
			if(!aux[i].isEmpty())
				tit.add(Integer.valueOf(aux[i].trim()));
		}
		
		
		this.titulares = tit;
		this.id = Integer.valueOf(id);
		this.saldo = Integer.valueOf(saldo);
	}
	
	public Cuenta(){
		titulares = new ArrayList<>();
		id = saldo = -1;
	}
	
	public ArrayList<Integer> titulares;
	public Integer id;
	public Integer saldo;

}
