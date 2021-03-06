package ud2p2;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.RandomAccessFile;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Ejercicio_7 extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file;
	private JPanel contentPane;
	private JTextField textFieldEntrada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio_7 frame = new Ejercicio_7();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Ejercicio_7() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnSelecionarTexto = new JButton("Selecionar Texto");
		btnSelecionarTexto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				file = ventanaSeleccion();
			}
		});
		btnSelecionarTexto.setBounds(79, 27, 188, 25);
		contentPane.add(btnSelecionarTexto);
		
		JLabel lblAConvertir = new JLabel("A convertir");
		lblAConvertir.setBounds(79, 92, 115, 25);
		contentPane.add(lblAConvertir);
		
		JButton btnConvertir = new JButton("Convertir");
		btnConvertir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textFieldEntrada.getText().length()>0){
					if(file!=null)
						convertir(textFieldEntrada.getText(),file);
					else
						javax.swing.JOptionPane.showConfirmDialog(null,
								"Introduce el archivo en que buscar", "Formulario incorrecto", 
				                javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
				else
					javax.swing.JOptionPane.showConfirmDialog(null,
							"Introduce el texto a buscar", "Formulario incorrecto", 
			                javax.swing.JOptionPane.PLAIN_MESSAGE);
			}
		});
		btnConvertir.setBounds(132, 182, 117, 25);
		contentPane.add(btnConvertir);
		
		textFieldEntrada = new JTextField();
		textFieldEntrada.setBounds(196, 95, 214, 25);
		contentPane.add(textFieldEntrada);
		textFieldEntrada.setColumns(10);
	}
	
	public static File ventanaSeleccion(File ruta){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(ruta);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
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
	
	private static void convertir(String texto, File file){
		try {
			RandomAccessFile raf = new RandomAccessFile(file, "rw");
			String line;
			
			while((line = raf.readLine() )!= null){
				System.out.println(line);
				int aux = line.indexOf(texto);
				if(aux !=-1){
					raf.seek(raf.getFilePointer() -1);
					if(raf.readByte()=='\n'){
						raf.seek(raf.getFilePointer() - (line.length()+1) );
						raf.writeBytes(line.replaceAll(texto, texto.toUpperCase())+"\n");
					}
					else{
						raf.seek(raf.getFilePointer() - line.length() );
						raf.writeBytes(line.replaceAll(texto, texto.toUpperCase()));
					}
				}
			}
			raf.close();
			System.out.println("cerrado");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
