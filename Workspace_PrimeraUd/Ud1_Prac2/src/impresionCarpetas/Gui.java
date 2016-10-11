package impresionCarpetas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Gui extends JFrame {

	private File origen;
	private ArrayList<File> result;
	private long tamano;

	private JPanel contentPane;
	private JTextField textFieldSize;
	private JRadioButton rdbtnMayor;
	private JRadioButton rdbtnMenores;
	private JCheckBox chckbxOcultos;
	private JButton btnSelect;
	private JButton btnBuscar;
	private JTextArea textAreaResult;
	private final ButtonGroup buttonGroup = new ButtonGroup();

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSelect = new JButton("Seleccionar Carpeta");
		btnSelect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				origen = ImpresionCarpeta.ventanaSeleccion();
			}
			
		});
		btnSelect.setBounds(54, 298, 197, 25);
		contentPane.add(btnSelect);
		
		
		rdbtnMayor = new JRadioButton("Mayores");
		buttonGroup.add(rdbtnMayor);
		rdbtnMayor.setBounds(159, 241, 149, 23);
		contentPane.add(rdbtnMayor);
		
		rdbtnMenores = new JRadioButton("Menores");
		buttonGroup.add(rdbtnMenores);
		rdbtnMenores.setBounds(159, 267, 149, 23);
		contentPane.add(rdbtnMenores);
		
		chckbxOcultos = new JCheckBox("Incluir ocultos");
		chckbxOcultos.setBounds(337, 252, 164, 23);
		contentPane.add(chckbxOcultos);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					tamano = Long.parseLong(textFieldSize.getText());
					if( rdbtnMayor.isSelected()){
						result = ImpresionCarpeta.buscarArchivoPorTamano(origen, '+', tamano, chckbxOcultos.isSelected());
						String texto = new String();
						for(int i = 0; i<result.size();i++){
							texto = texto.concat(ImpresionCarpeta.mostrarInfo(result.get(i)).replace(origen.getAbsolutePath(),"").concat("\n"));
						}
						textAreaResult.setText(texto);
						textAreaResult.setEnabled(true);
						texto = "";
					}
					else{
						result = ImpresionCarpeta.buscarArchivoPorTamano(origen, '-', tamano, chckbxOcultos.isSelected());
						String texto = new String();
						for(int i = 0; i<result.size();i++){
							texto = texto.concat(ImpresionCarpeta.mostrarInfo(result.get(i)).concat("\n"));
						}
						textAreaResult.setText(texto);
						textAreaResult.setEnabled(true);
						texto = "";
					}
				}catch(NumberFormatException ex){
					javax.swing.JOptionPane.showConfirmDialog(null, "Tiene que introducir un numero entero", "Formulario incorrecto", 
			                javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
				
				
					
				
			}
		});
		btnBuscar.setBounds(288, 298, 117, 25);
		contentPane.add(btnBuscar);
		
		
		
		JLabel lblTamanoEnBytes = new JLabel("Tamano en bytes");
		lblTamanoEnBytes.setBounds(54, 227, 164, 15);
		contentPane.add(lblTamanoEnBytes);
		
		textFieldSize = new JTextField();
		textFieldSize.setBounds(193, 225, 114, 19);
		contentPane.add(textFieldSize);
		textFieldSize.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(54, 25, 457, 182);
		contentPane.add(scrollPane_1);
		
		textAreaResult = new JTextArea();
		scrollPane_1.setViewportView(textAreaResult);
		textAreaResult.setEnabled(false);
		textAreaResult.setEditable(false);
	}
}
