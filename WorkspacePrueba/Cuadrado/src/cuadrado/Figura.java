package cuadrado;

public class Figura {

	public static void main(String[] args) {

		if(args.length != 3){
			System.out.println("cantidad invalida de parametros");
		}
		else{
			int figura = Integer.parseInt(args[0]);
			int lado = Integer.parseInt(args[1]);
			if((lado < 2 || lado > 15) || (!args[2].equals("0") && !args[2].equals("1"))){
				System.out.println("error de parametros");
			}
			else{
				switch (figura){
					case 0:
						imprimirCuadrado(lado,args[2]);
						break;
					case 1:
						imprimirTriangulo(lado,args[2]);
						break;
					default:
						System.out.println("figura no implementada");
				}
			}
		}

	}

	private static void imprimirCuadrado(int lado, String lleno){
		
		for(int i = 0; i < lado; i++){
			for(int j=0;j<lado; j++){
				if(i==0 || i==(lado-1) || !lleno.equals("0") || j==0 || j==(lado-1)){
					System.out.print('*');
				}
				else{
					System.out.print(' ');
				}
			}
			System.out.print('\n');
		}
		
	}
	
	private static void imprimirTriangulo(int lado, String lleno){
		
		for(int i = 0; i < lado; i++){
			for(int j = 0; j < (lado*2-1); j++){
				if(borde("triangulo",i,j,lado)){
					System.out.print('*');
				}
				if(interior("triangulo",i,j,lado)&&lleno.equals("1")){
					System.out.print('*');
				}
			}
			if(i == lado-1)
				System.out.print('*');
			System.out.print('\n');
		}
		
	}
	
	private static boolean borde(String tipo,int i, int j, int lado){
		switch(tipo){
		case "triangulo":
			if(i == lado -1)
				return true;
			if(j == lado-i || j == lado+i)
				return true;
			return false;
		case "cuadrado":
			if(i==0 || i == lado-1)
				return true;
			if(j==0 || j ==lado-1)
				return true;
			return false;
		}
		return true;
	}
	
	private static boolean interior(String tipo,int i, int j, int lado){
		switch(tipo){
		case "triangulo":
			if(i == lado -1)
				return false;
			if(j > lado-i && j < lado+i)
				return true;
			return false;
		case "cuadrado":
			if(i==0 || i == lado-1)
				return false;
			if(j > 0 || j < lado-1)
				return true;
			return false;
		}
		return false;
	}
	
}
