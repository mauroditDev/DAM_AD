package examen;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Examen_2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("titulo:");
		String titulo = sc.nextLine();
		System.out.println("linea:");
		int linea = Integer.parseInt(sc.nextLine());
		
		File archivo = new File ("/home/alumno/Biblioteca/"+titulo);
		if(archivo.exists()){
			String res = buscarLinea(archivo,linea);
			if(res.length()>0)
				System.out.println(res);
			else{
				System.out.println("linea inexistente");
			}
		}else{
			System.out.println("titulo no disponible");
		}
		
		sc.close();

	}
	
	public static String buscarLinea(File archivo,int linea){
		String res = "";
		File xml = new File("/home/alumno/vacia2/catalogo.xml");
		try{
			BufferedReader br = new BufferedReader(new FileReader(xml));
			long puntero = 0; 
			String lin;
			while((lin = br.readLine()) != null){
				if(lin.indexOf(archivo.getName())!=-1){
					while((lin = br.readLine())!=null){
						if(lin.indexOf("num= \""+linea+"\"")!=-1){
							String aux = lin.substring((lin.indexOf(">")+1),(lin.lastIndexOf("<")));
							puntero = Long.parseLong(aux);
							RandomAccessFile raf = new RandomAccessFile(archivo,"rw");
							raf.seek(puntero);
							res = raf.readLine();
							br.close();
							raf.close();
							return res;
						}
					}
				}
			}
			br.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}

}
