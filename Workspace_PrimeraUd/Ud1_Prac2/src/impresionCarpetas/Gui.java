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
import java.awt.Toolkit;
import java.util.GregorianCalendar;

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
	private JTextField textFieldPath;
	private JTextField textFieldEncontrados;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JRadioButton rdbtnAntes;
	private JRadioButton rdbtnDespues;
	private JTextField textFieldFecha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
        	ex.getMessage();
        }
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
		setResizable(false);
		setTitle("Buscador de Archivos");
		setIconImage(Toolkit.getDefaultToolkit().getImage("/usr/share/icons/gnome/48x48/status/folder-visiting.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 548, 569);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnSelect = new JButton("Seleccionar Carpeta");
		btnSelect.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				if(origen == null)
					origen = ImpresionCarpeta.ventanaSeleccion();
				else{
					origen = ImpresionCarpeta.ventanaSeleccion(origen);
				}
				if(origen != null)
					textFieldPath.setText(origen.getAbsolutePath());
			}
			
		});
		btnSelect.setBounds(49, 469, 149, 25);
		contentPane.add(btnSelect);
		
		
		rdbtnMayor = new JRadioButton("Mayores");
		rdbtnMayor.setSelected(true);
		buttonGroup.add(rdbtnMayor);
		rdbtnMayor.setBounds(154, 412, 149, 23);
		contentPane.add(rdbtnMayor);
		
		rdbtnMenores = new JRadioButton("Menores");
		buttonGroup.add(rdbtnMenores);
		rdbtnMenores.setBounds(154, 438, 149, 23);
		contentPane.add(rdbtnMenores);
		
		chckbxOcultos = new JCheckBox("Incluir ocultos");
		chckbxOcultos.setBounds(329, 438, 164, 23);
		contentPane.add(chckbxOcultos);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try{
					if(origen != null){
						if(textFieldFecha.getText().matches("[0-9]{4}-[0-1][0-9]-[0-3][0-9]")){
							long fecha;
							String[] aux = textFieldFecha.getText().split("-");
							GregorianCalendar(Integer.parseInt(aux[0]), Integer.parseInt(aux[1]), Integer.parseInt(aux[2]));
							fecha = 4;
							result = ImpresionCarpeta.buscarArchivoPorFecha(origen, rdbtnAntes.isSelected(), fecha, chckbxOcultos.isSelected());
							String texto = new String();
							for(int i = 0; i<result.size();i++){
								texto = texto.concat(ImpresionCarpeta.mostrarInfo(result.get(i)).replace(origen.getAbsolutePath(),".").concat("\n"));
							}
							textAreaResult.setText(texto);
							textAreaResult.setEnabled(true);
							textFieldEncontrados.setText(String.valueOf(result.size()));
							texto = "";
						}
						tamano = Long.parseLong(textFieldSize.getText());
						if( rdbtnMayor.isSelected()){
							result = ImpresionCarpeta.buscarArchivoPorTamano(origen, '+', tamano, chckbxOcultos.isSelected());
							String texto = new String();
							for(int i = 0; i<result.size();i++){
								texto = texto.concat(ImpresionCarpeta.mostrarInfo(result.get(i)).replace(origen.getAbsolutePath(),".").concat("\n"));
							}
							textAreaResult.setText(texto);
							textAreaResult.setEnabled(true);
							textFieldEncontrados.setText(String.valueOf(result.size()));
							texto = "";
						}
						else{
							result = ImpresionCarpeta.buscarArchivoPorTamano(origen, '-', tamano, chckbxOcultos.isSelected());
							String texto = new String();
							for(int i = 0; i<result.size();i++){
								texto = texto.concat(ImpresionCarpeta.mostrarInfo(result.get(i)).replace(origen.getAbsolutePath(),".").concat("\n"));
							}
							textAreaResult.setText(texto);
							textAreaResult.setEnabled(true);
							textFieldEncontrados.setText(String.valueOf(result.size()));
							texto = "";
						}
					}
				}catch(NumberFormatException ex){
					javax.swing.JOptionPane.showConfirmDialog(null, "Tiene que introducir un numero entero", "Formulario incorrecto", 
			                javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
				
				
					
				
			}
		});
		btnBuscar.setBounds(200, 506, 117, 25);
		contentPane.add(btnBuscar);
		
		
		
		JLabel lblTamanoEnBytes = new JLabel("Tamaño en bytes");
		lblTamanoEnBytes.setBounds(49, 395, 164, 15);
		contentPane.add(lblTamanoEnBytes);
		
		textFieldSize = new JTextField();
		textFieldSize.setBounds(188, 390, 138, 25);
		contentPane.add(textFieldSize);
		textFieldSize.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 25, 494, 301);
		contentPane.add(scrollPane_1);
		
		textAreaResult = new JTextArea();
		scrollPane_1.setViewportView(textAreaResult);
		textAreaResult.setEnabled(false);
		textAreaResult.setEditable(false);
		
		textFieldPath = new JTextField();
		textFieldPath.setEnabled(false);
		textFieldPath.setEditable(false);
		textFieldPath.setBounds(210, 469, 296, 30);
		contentPane.add(textFieldPath);
		textFieldPath.setColumns(10);
		
		JLabel lblEntradas = new JLabel("Encontrados:");
		lblEntradas.setBounds(329, 416, 99, 15);
		contentPane.add(lblEntradas);
		
		textFieldEncontrados = new JTextField();
		textFieldEncontrados.setEnabled(false);
		textFieldEncontrados.setEditable(false);
		textFieldEncontrados.setBounds(437, 411, 70, 25);
		contentPane.add(textFieldEncontrados);
		textFieldEncontrados.setColumns(10);
		
		rdbtnAntes = new JRadioButton("Antes");
		rdbtnAntes.setSelected(true);
		buttonGroup_1.add(rdbtnAntes);
		rdbtnAntes.setBounds(280, 359, 70, 23);
		contentPane.add(rdbtnAntes);
		
		rdbtnDespues = new JRadioButton("Después");
		buttonGroup_1.add(rdbtnDespues);
		rdbtnDespues.setBounds(358, 359, 87, 23);
		contentPane.add(rdbtnDespues);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setBounds(49, 359, 200, 27);
		contentPane.add(textFieldFecha);
		textFieldFecha.setColumns(10);
		
		JLabel lblFechaModificacin = new JLabel("Fecha Modificación:");
		lblFechaModificacin.setBounds(49, 332, 164, 15);
		contentPane.add(lblFechaModificacin);
	}
}
