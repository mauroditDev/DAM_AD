package array;

import java.util.Arrays;

public class Array {

	public static void main(String[] args) {
		
		int b = (int)(Math.random()*1000000);
		int[] a = partir(b);
		
		System.out.println(b);
		System.out.println(Arrays.toString(a));
		

	}
	
	private static int[] partir(int n){
		
		int[] aux = new int[0];
		
		while(n>0){
			
			aux = Arrays.copyOf(aux, aux.length+1);
			aux[aux.length-1] = n%10;
			n = n/10;
			
		}
		
		int[] res = new int [aux.length];
		
		for(int i = 0; i<aux.length; i++){
			res[i]=aux[aux.length-(i+1)];
		}
		
		return res;
		
	}

}
