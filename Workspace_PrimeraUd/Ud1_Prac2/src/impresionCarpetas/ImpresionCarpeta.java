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
		int k = 0;
		String ruta;
		String aux;
		char crit;
		Long tamano;
		boolean ocultos;
		//compruba si se dieron argumentos en la ejecución
		if(args.length > 0){
			ruta = args[k++]; //si se dieron se trata el primero como la ruta
			crit = args[k++].charAt(0);
			tamano = Long.parseLong(args[k++]);
			ocultos = Boolean.parseBoolean(args[k++]);
		}
		else{
			/*
			 * si no se dieron argumentos se requiere el ingreso de una ruta
			 * en forma de String
			 */
			Scanner sc = new Scanner(System.in);
			System.out.println("introduzca ruta de la carpeta:");
			ruta = sc.next();
			System.out.println("criterio de busqueda '+', '-'");
			aux = sc.next();
			while(!aux.equals("+")&&!aux.equals("-")){
				System.out.println("+ o -");
				aux = sc.next();	
			}
			crit = aux.charAt(0);
			System.out.println("tamaño de la búsqueda:");
			tamano = Long.parseLong(sc.next());
			System.out.println("incluye ocultos? ('true' o 'false')");
			aux = sc.next();
			while(!aux.equals("true")&&!aux.equals("false")){
				System.out.println("true o false");
				aux = sc.next();	
			}
			ocultos = Boolean.parseBoolean(aux);
			sc.close();
		}
		
		
		File archivo = new File(ruta);
		ArrayList<File> resultado = buscarArchivoPorTamano(archivo,crit,tamano,ocultos);
		if(resultado == null){
			System.out.println("la ruta no existe o apunta a un archivo");
		}
		else{
			int i = 0;
			for(i = 0; i < resultado.size(); i++){
				System.out.println(mostrarInfo(resultado.get(i)).replaceAll(ruta, ""));
			}
			if(i==0)
				System.out.println("Ningún resultado");
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
	
	
	
	
	
	public static ArrayList<File> buscarArchivoPorTamano(File rutaComienzo, char criterio, long tamano,boolean inclOcultos){
		
		ArrayList<File> res = null;
		
		File[] lista = null;
		if(rutaComienzo.exists() && rutaComienzo.isDirectory())
			 lista = rutaComienzo.listFiles();
		
		if(lista!=null){
			res = new ArrayList<File>();
			for(int i = 0; i< lista.length; i++){
				if(lista[i].isDirectory()){
					if((!inclOcultos && !lista[i].isHidden())||(inclOcultos))
					res.addAll(buscarArchivoPorTamano(lista[i],criterio,tamano,inclOcultos));
				}
				else{
					if((!inclOcultos && !lista[i].isHidden())||(inclOcultos)){
						switch(criterio){
						case '+':
							if(lista[i].length() > tamano)
								res.add(lista[i]);
							break;
						case '-':
							if(lista[i].length() < tamano)
								res.add(lista[i]);
							break;
						default:
							res = null;
						}
					}	
				}
			}
		}
		
		return res;
	}
	
	public static String mostrarInfo(File ruta){
		String res = null;
		
		if(ruta.exists()){
			if(ruta.isDirectory())
				res = "d ";
			else
				res = "- ";
			
			res = res.concat(ruta.getAbsolutePath());
			
			if(ruta.isFile())
				res = res.concat(" (".concat(String.valueOf(ruta.length()))).concat(")");
			
			if(ruta.isHidden())
				res = res.concat("(*)");
			
		}
		
		return res;
	}

}
