package array;

import java.util.Arrays;

public class Partir {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int digs[];
		digs=partirDigitos(1234567890);
		System.out.println(Arrays.toString(digs));
		
		digs=partirDigitos(70148);
		System.out.println(Arrays.toString(digs));
		
		digs=partirDigitos(0);
		System.out.println(Arrays.toString(digs));

	}
	
	static int[] partirDigitos(int n){
		int dig;
		int cnt=0;
		int cociente=n;
		
		//Contamos digitos
		do{
			cnt++;
			cociente=cociente/10;
		}while(cociente!=0);
		
		//Creamos array y almacenamos digitos
		int digitos[]=new int[cnt];
		int volteado[]=new int[cnt];
		cnt=0;
		cociente=n;
		
		do{
			dig=cociente%10;
			digitos[cnt]=dig;
			cnt++;
			cociente=cociente/10;
		}while(cociente!=0);
		
		//Cargamos otro array cambiando el orden de los digitos
		for(int i=0;i<digitos.length;i++)
			volteado[digitos.length-1-i]=digitos[i];
		
		return volteado;
	}

}
