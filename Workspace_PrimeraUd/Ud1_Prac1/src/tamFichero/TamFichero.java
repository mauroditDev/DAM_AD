package tamFichero;

import java.io.File;
import java.util.Scanner;

public class TamFichero {

	public static void main(String[] args){
		//String para almacenar la ruta del archivo
		String ruta;
		
		//compruba si se dieron argumentos en la ejecución
		if(args.length > 0)
			ruta = args[0]; //si se dieron se trata el primero como la ruta
		else{
			/*
			 * si no se dieron argumentos se requiere el ingreso de una ruta
			 * en forma de String
			 */
			Scanner sc = new Scanner(System.in);
			System.out.println("introduzca ruta del archivo:");
			ruta = sc.next();
			sc.close();
		}
		
		
		File archivo = new File(ruta);
		
		//se comprueba existencia del archivo
		if(!archivo.exists())
			System.out.println("el archivo no existe");
		else{
			//se ataja el caso de los directorios
			if(archivo.isDirectory())
				System.out.println("es una carpeta, no un archivo");
			
			else{
				//caso correcto:
				//se imprime la dimensión
				System.out.println("Dimension del archivo: "+archivo.length());
				
				//se imprime la ruta del archivo
				System.out.println("Ruta del archivo: "+archivo.getAbsolutePath());
				
				//se imprimen los permisos del usuario que ejecuta la aplicación
				System.out.print("mis permisos:");
				if(archivo.canRead())
					System.out.print("r");
				else
					System.out.print("-");
				
				if(archivo.canWrite())
					System.out.print("w");
				else
					System.out.print("-");
				
				if(archivo.canExecute())
					System.out.print("x");
				else
					System.out.print("-");
				
				System.out.println();
			}
		}
			
		
	}
	
	
	
}
