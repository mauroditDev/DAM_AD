package ud2p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ImprimirCsv {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("/media/Datos/Datos_Mauro/Acceso_Datos/origen.csv")));
			String texto = "";
			while(true){
				String a;
				a = br.readLine();
				if(a==null)
					break;
				else{
					//Coche c = new Coche();
					String[] b = a.split(";");
					for(int i =0; i<b.length; i++){
						texto += b[i] + "\t\t";
					}
					texto += '\n';
				}
			}
			 System.out.println(texto);
			 br.close();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}

	}

}
