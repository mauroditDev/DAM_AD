package miArray;

public class MiArray {
	private int valores[];
	
	public MiArray(int size){
		this.valores = new int[size];
		for(int i=0; i<size;i++){
			valores[i]=((int)Math.random()*100)+1;
		}
	}
	
	public int minArray(){
		int a=101;
		
		for(int i =0;i<valores.length;i++){
			if(valores[i]<a)
				a = valores[i];
		}
		
		return a;
	}
	
	public int maxArray(){
		int a=0;
		
		for(int i =0;i<valores.length;i++){
			if(valores[i]>a)
				a = valores[i];
		}
		
		return a;
	}
	
	public int posMin(){
		int a=101;
		int q=0;
		
		for(int i =0;i<valores.length;i++){
			if(valores[i]<a){
				a = valores[i];
				q = i;
			}
		}
		
		return q;
	}
	
	public int posMax(){
		int a=0;
		int q=0;
		
		for(int i =0;i<valores.length;i++){
			if(valores[i]>a){
				a = valores[i];
				q=i;
			}
		}
		
		return q;
	}
		
	public void mostrarArray(){
		for(int i =0; i< valores.length; i++){
			System.out.println(valores[i]);
		}
	}
	
	public void insertar(int valor, int index){
		int[] nuevoArray = new int[valores.length+1]; 
		for(int i = valores.length;i>=0;i--){
			if(i==index){
				nuevoArray[i]=valor;
				i--;
			}
			nuevoArray[i]=valores[i-1];
			
		}
	}
	
}
