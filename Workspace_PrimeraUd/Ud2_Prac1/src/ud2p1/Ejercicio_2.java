package ud2p1;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Scanner;

public class Ejercicio_2 {

	public static void main(String[] args) {
	
		
	        Field properties[] = Coche.class.getDeclaredFields();
			// TODO Auto-generated method stub
			try{
				Scanner sc = new Scanner(System.in);
				PrintWriter pw = new PrintWriter(new FileWriter(
						new File("/media/Datos/Datos_Mauro/Acceso_Datos/destino.csv")));
				pw.write(properties[0].getName()+';'
						+properties[1].getName()+';'
						+properties[2].getName()+'\n');;
				String matricula;
				System.out.println("Matricula:");
				matricula = sc.nextLine();
				while(!matricula.equals("")){
					pw.write(matricula+';');
					System.out.println("Marca:");
					pw.write(sc.nextLine()+';');
					System.out.println("Precio:");
					pw.write(sc.nextLine()+'\n');
					System.out.println("Matricula:");
					matricula = sc.nextLine();
				}
				pw.close();
				sc.close();
			}catch(Exception e){
				
				e.printStackTrace();
				
			}

	}

}
