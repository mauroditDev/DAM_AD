package ud2p1;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.JFileChooser;

public class Ejercicio_3 {
	
	static public void main(String[] args){
		try {
			DataInputStream dataIn = new DataInputStream(
					new FileInputStream(
							ventanaSeleccion()
					));
			if(dataIn.readByte()==Integer.parseInt("ff", 16)&&
					dataIn.read()==Integer.parseInt("d8", 16)){
				System.out.println("es un jpg");
				
				boolean sw = true;
				while(sw){
					if(dataIn.read()==Integer.parseInt("ff", 16))
						if(dataIn.read()==Integer.parseInt("c0", 16))
							sw = false;
				}
				dataIn.skip(3);
				
				int h = dataIn.read();
				h = h * 256 + dataIn.read();
				
				int w = dataIn.read();
				w = w * 256 + dataIn.read();
				
				System.out.println("Altura: "+h+"\nAncho: "+w);
				
				dataIn.close();
				
			}
			else{
				System.out.println("NO es un jpg");
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static File ventanaSeleccion(File ruta){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(ruta);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setApproveButtonText("Seleccionar");
		int seleccion = fileChooser.showOpenDialog(null);
		if(seleccion == JFileChooser.APPROVE_OPTION){
			return fileChooser.getSelectedFile();
		}
		else
			return null;
	}
	
	public static File ventanaSeleccion(){
		return ventanaSeleccion(new File("/"));
	}

}
