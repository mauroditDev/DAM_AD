package ud2p1;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class Ejercicio_4 {

	public static void main(String[] args) {
		
		try{
			//se comprueba que tenga 2 argumentos
			if(args.length == 2){
				File a1 = new File(args[0]);
				
				//se comprueba un origen válido
				if(a1.exists()){
					DataInputStream dataIn = new DataInputStream(new FileInputStream(a1));
					File a2 = new File(args[1]);
					boolean cont = true;
					
					//se comprueba si se va a sobreescribir un archivo existente
					if(a2.exists()){
						Scanner sc = new Scanner(System.in);
						String aux = "a";
						
						//se pide confirmación del usuario para sobreescritura del archivo
						while(!aux.equalsIgnoreCase("y") && !aux.equalsIgnoreCase("n")){
							System.out.println(a2.getAbsolutePath() + "\tya existe\n"+
									"seguro que desea sobreescribirlo (y/n)");
							aux = sc.nextLine();
							if(aux.equalsIgnoreCase("n"))
								cont = false;
						}
						sc.close();
						
					}
					
					if(cont){
						DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(a2));
						
						while(dataIn.available() > 0){
							dataOut.write(dataIn.read());
						}
						
						dataOut.close();
						System.out.println("archivo copiado correctamente");
					}
					else
						System.out.println("proceso cancelado por el usuario");
					
					
					
					dataIn.close();
				}
				else{
					System.out.println("no se encuentra el archivo de origen");
				}
			}
			else{
				System.out.println("este programa requiere dos argumentos");
		}
		}catch(Exception e){
			System.err.println(e.getClass().toString());
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

	}

}
