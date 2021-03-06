package ud1_p3;

import java.io.File;

import javax.swing.JFileChooser;

public class Ud1p3 {
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
		
		public static File ventanaGuardado(File ruta){
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(ruta);
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int seleccion = fileChooser.showSaveDialog(null);
			if(seleccion == JFileChooser.APPROVE_OPTION){
				return fileChooser.getSelectedFile();
			}
			else
				return null;
		}
		
		public static File ventanaGuardado(){
			return ventanaGuardado(new File("/"));
		}
		
		
}
