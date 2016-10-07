package impresionCarpetas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ImpresionCarpeta {

	public static void main(String[] args) {
		//String para almacenar la ruta del archivo
		String ruta;
		int espacios = 0;
		//compruba si se dieron argumentos en la ejecución
		if(args.length > 0)
			ruta = args[0]; //si se dieron se trata el primero como la ruta
		else{
			/*
			 * si no se dieron argumentos se requiere el ingreso de una ruta
			 * en forma de String
			 */
			Scanner sc = new Scanner(System.in);
			System.out.println("introduzca ruta de la carpeta:");
			ruta = sc.next();
			sc.close();
		}
		
		
		File archivo = new File(ruta);
		mostrarDirectorio(archivo,espacios);
		

	}
	
	public static void mostrarDirectorio(File archivo,int espacios){
		if(archivo.exists()){
			if(archivo.isDirectory()){
				System.out.println(archivo.getAbsolutePath());
				File[] lista;
				lista = archivo.listFiles();
				int i=0;
				for(; i<lista.length;i++){
					if(!lista[i].isHidden() && lista[i].canRead()){
						for(int j = 0; j<espacios; j++)
							System.out.print("\t");
						System.out.print("\u02ea");
						if(lista[i].isDirectory()){
							System.out.print("d  ");
							System.out.print(lista[i].getName());
							mostrarDirectorio(lista[i],espacios+1);
						}
						else{
							System.out.print("-  ");
							System.out.print(lista[i].getName());
							System.out.print("---");
							System.out.print(lista[i].length());
							System.out.print("bytes\n");
						}
					}
					
				}
				if(i==0){
					System.out.println("La carpeta está vacía");
				}
			}
			else{
				System.out.println("Eso es no una carpeta!");
			}
		}
		else{
			System.out.println("La carpeta no existe");
		}
	}
	
	public static ArrayList<File> buscarArchivoPorTamano(File rutaComienzo, char criterio, long tamano){
		ArrayList<File> res = new ArrayList<File>();
		
		//TODO: función que se come un criterio ('+' para mayor que, '-' para menor que),
		//y según este criterio busca todos los archivos de tamaño dado, los almacena en ArrayList
		//y devuelve.
		
		
		return res;
	}

}
