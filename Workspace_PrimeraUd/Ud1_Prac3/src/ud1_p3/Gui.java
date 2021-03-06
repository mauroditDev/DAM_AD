package ud1_p3;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;


@SuppressWarnings("serial")
public class Gui extends JFrame {

	private JPanel contentPane;
	private JButton btnBorrar;
	private JButton btnRenombrar;
	private JButton btnCrearCarpeta;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
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
	public Gui() {
		setTitle("Borrar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnBorrar = new JButton("Borrar");
		btnBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = Ud1p3.ventanaSeleccion();
				if(f != null)
					if(javax.swing.JOptionPane.showConfirmDialog(null, "¿Seguro que desea borrar?", "Confirmación usuario", 
			                javax.swing.JOptionPane.OK_CANCEL_OPTION) == 0){
						if(!f.delete()){
							javax.swing.JOptionPane.showConfirmDialog(null, "No se pudo borrar el fichero", "Operación fallida", 
					                javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(null,"Fichero borrado!","Éxito!",javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
					}
				
			}
		});
		btnBorrar.setBounds(161, 35, 117, 25);
		contentPane.add(btnBorrar);
		
		btnRenombrar = new JButton("Renombrar");
		btnRenombrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f = Ud1p3.ventanaSeleccion();
				if(f!=null){
					if(!f.renameTo(Ud1p3.ventanaGuardado())){
						javax.swing.JOptionPane.showConfirmDialog(null, "No se pudo mover/renombrar", "Operación fallida", 
				                javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
					else{
						javax.swing.JOptionPane.showConfirmDialog(null, "Renombrado/movido con éxito!", "Éxito!",
								javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		btnRenombrar.setBounds(161, 103, 117, 25);
		contentPane.add(btnRenombrar);
		
		btnCrearCarpeta = new JButton("Crear Carpeta");
		btnCrearCarpeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File f = Ud1p3.ventanaGuardado();
				if(f != null){
					if(!f.mkdirs()){
						javax.swing.JOptionPane.showConfirmDialog(null, "No se pudo crear estructura de ficheros", "Operación fallida", 
				                javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
					else{
						javax.swing.JOptionPane.showConfirmDialog(null, "Creado con éxito","Éxito!",
								javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		btnCrearCarpeta.setBounds(143, 180, 162, 25);
		contentPane.add(btnCrearCarpeta);
	}
}
