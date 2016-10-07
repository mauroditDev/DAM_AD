package impresionCarpetas;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ImpresionCarpeta {

	/*
	 * función main para el listado de archivos y carpetas en arbol
	 * 
	public static void main(String[] args) {
		//String para almacenar la ruta del archivo
		String ruta;
		int espacios = 0;
		//compruba si se dieron argumentos en la ejecución
		if(args.length > 0)
			ruta = args[0]; //si se dieron se trata el primero como la ruta
		else{
			Scanner sc = new Scanner(System.in);
			System.out.println("introduzca ruta de la carpeta:");
			ruta = sc.next();
			sc.close();
		}
		
		
		File archivo = new File(ruta);
		mostrarDirectorio(archivo,espacios);
		

	}
	*/
	
	/*
	 *función main para la búsqueda de los archivos según un criterio 
	 *
	 */
	public static void main(String[] args) {
		//String para almacenar la ruta del archivo
		String ruta;
		String criterio;
		char crit;
		Long tamano;
		int espacios = 0;
		//compruba si se dieron argumentos en la ejecución
		if(args.length > 0){
			ruta = args[0]; //si se dieron se trata el primero como la ruta
			crit = args[1].charAt(0);
			tamano = Long.parseLong(args[2]);
		}
		else{
			/*
			 * si no se dieron argumentos se requiere el ingreso de una ruta
			 * en forma de String
			 */
			Scanner sc = new Scanner(System.in);
			System.out.println("introduzca ruta de la carpeta:");
			ruta = sc.next();
			System.out.println("criterio de busqueda '+', '-' o '=': ");
			criterio = sc.next();
			crit = criterio.charAt(0);
			System.out.println("itamaño de la búsqueda:");
			tamano = Long.parseLong(sc.next());
			sc.close();
		}
		
		
		File archivo = new File(ruta);
		ArrayList<File> resultado = buscarArchivoPorTamano(archivo,crit,tamano);
		for(int i = 0; i < resultado.size(); i++){
			System.out.println(resultado.get(i).getAbsolutePath().replaceAll(ruta, "")+ " ---- "+resultado.get(i).length());
		}
		System.out.println();
		

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
		
		File[] lista = null;
		if(rutaComienzo.exists() && rutaComienzo.isDirectory())
			 lista = rutaComienzo.listFiles();
		
		if(lista!=null){
			
			for(int i = 0; i< lista.length; i++){
				if(lista[i].isDirectory() && !lista[i].isHidden()){
					res.addAll(buscarArchivoPorTamano(lista[i],criterio,tamano));
				}
				else{
					if(!lista[i].isHidden()){
						switch(criterio){
						case '+':
							if(lista[i].length() > tamano)
								res.add(lista[i]);
							break;
						case '-':
							if(lista[i].length() < tamano)
								res.add(lista[i]);
							break;
						case '=':
							if(lista[i].length() == tamano)
								res.add(lista[i]);
							break;
						}
					}	
				}
			}
		}
		
		return res;
	}

}
