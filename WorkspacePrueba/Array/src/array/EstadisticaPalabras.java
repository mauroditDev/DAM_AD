package array;

import java.util.*;

public class EstadisticaPalabras implements Comparable<EstadisticaPalabras> {
	
	static public void main(String[] args){
		int vocales = 0;
		int consonantes = 0;
		String input = Entrada.cadena();
		ArrayList<String> al = new ArrayList<String>();
		ArrayList<EstadisticaPalabras> al2 = new ArrayList<>();
		int suma = 1;
		
		
		for(int i =0; i < input.split("[\\s,.]").length;i++){
			al.add(input.split("[\\s,.]")[i].toLowerCase());
		}
		
		TreeMap<String, Integer> tm = new TreeMap<>();
		
		for(int i = 0; i<al.size(); i++){
			if(tm.put(al.get(i), 1)!=null){
				tm.put(al.get(i), tm.get(al.get(i))+suma);
				suma++;
			}
		}
		
		for(String c: tm.keySet()){
			for(int i = 0; i<c.length();i++){
				if(c.toLowerCase().charAt(i)=='a'||c.toLowerCase().charAt(i)=='e'||c.toLowerCase().charAt(i)=='i'||c.toLowerCase().charAt(i)=='o'||c.toLowerCase().charAt(i)=='u')
					vocales += tm.get(c);
				else
					consonantes += tm.get(c);
			}
			System.out.println(c+": "+tm.get(c));
			EstadisticaPalabras aux = new EstadisticaPalabras();
			aux.valor = tm.get(c);
			aux.nombre = c;
			al2.add(aux);
		}
		System.out.println("vocales: "+vocales);
		System.out.println("no vocales: "+consonantes);
		
		Collections.sort(al2);
		
		for(int i = 0; i < al2.size() ;i++){
			System.out.print(al2.get(i).nombre);
			System.out.print(": ");
			System.out.print(al2.get(i).valor);
			System.out.println();
		}
		
		
	}
	
	public Integer valor;
	
	public String nombre;
	
	public int compare(EstadisticaPalabras one,EstadisticaPalabras other){
		return (other.valor).compareTo(one.valor);
	}

	@Override
	public int compareTo(EstadisticaPalabras arg0) {
		if(this.valor < arg0.valor)
			return 1;
		if(this.valor > arg0.valor)
			return -1;
		return 0;
	}


}
