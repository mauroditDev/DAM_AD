package sumar;

public class Sumar {

	public static void main(String args[]){
		
		/*
		System.out.println(suma(2,2+1));
		System.out.println(suma());
		System.out.println(suma(2,1+suma()));
		Colores c1 = new Colores("verde");
		Colores c2 = new Colores();
		c2.setColor("rojo");
		System.out.println(c1.getColor() + " " + c2.getColor());
		*/
		
		System.out.println(args.length+" argumentos");
		
		for(int i = 0; i<args.length; i++){
			
			System.out.println(args[i]);
			
		}
		
		/*
		Sumar s = new Sumar();
		Sumar s2 = new Sumar(10,15);
		System.out.println(s.suma_this());
		System.out.println(s2.suma_this());
		*/
		
		
	}
	
	public static int suma(int a, int b){
		return a+b;
	}
	
	public static int suma(){
		return 100;
	}
	
	public static int suma(int a){
		return 100+a;
	}
	
	public int suma_this(){
		
		return valor1 + valor2;
		
	}
	
	private int valor1;
	public int getValor1() {
		return valor1;
	}

	public void setValor1(int valor1) {
		this.valor1 = valor1;
	}

	public int getValor2() {
		return valor2;
	}

	public void setValor2(int valor2) {
		this.valor2 = valor2;
	}

	private int valor2;
	
	public Sumar(){
		
		this.valor1=0;
		this.valor2=0;
		
	}
	
	public Sumar(int a, int b){
		
		this.valor1=a;
		this.valor2=b;
		
	}
	
}
