package impresionCarpetas;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JScrollPane;

import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Gui extends JFrame {

	private File origen;
	private ArrayList<File> result;
	private long tamano;
	private Date fecha;
	private String nombre;
	private String texto;

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
	private JTextField textFieldNombre;
	private JCheckBox chckbxNombre;
	private JCheckBox chckbxFecha;
	private JCheckBox chckbxTamano;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
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
		setBounds(100, 100, 697, 652);
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
		btnSelect.setBounds(44, 552, 149, 25);
		contentPane.add(btnSelect);
		
		
		rdbtnMayor = new JRadioButton("Mayores");
		rdbtnMayor.setEnabled(false);
		rdbtnMayor.setSelected(true);
		buttonGroup.add(rdbtnMayor);
		rdbtnMayor.setBounds(86, 505, 87, 23);
		contentPane.add(rdbtnMayor);
		
		rdbtnMenores = new JRadioButton("Menores");
		rdbtnMenores.setEnabled(false);
		buttonGroup.add(rdbtnMenores);
		rdbtnMenores.setBounds(193, 505, 92, 23);
		contentPane.add(rdbtnMenores);
		
		chckbxOcultos = new JCheckBox("Incluir ocultos");
		chckbxOcultos.setBounds(324, 521, 129, 23);
		contentPane.add(chckbxOcultos);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(origen != null){
					texto = new String();
					SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
					sdf.setLenient(false);
					try {
						fecha = sdf.parse(textFieldFecha.getText());
						result = ImpresionCarpeta.buscarArchivoPorFecha(origen, rdbtnAntes.isSelected(), fecha.getTime(), chckbxOcultos.isSelected());
					} catch (ParseException parseE) {
						if(chckbxFecha.isSelected())
							javax.swing.JOptionPane.showConfirmDialog(null, "La fecha está mal introducida dd-MM-yyyy", "Formulario incorrecto", 
				                javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
					try{
						tamano = Long.parseLong(textFieldSize.getText());
						if(result != null && result.size()>0){
							if( rdbtnMayor.isSelected())
								result = ImpresionCarpeta.buscarArchivoPorTamano(result, '+', tamano, chckbxOcultos.isSelected());
							else
								result = ImpresionCarpeta.buscarArchivoPorTamano(result, '-', tamano, chckbxOcultos.isSelected());
						}
						else{
							if( rdbtnMayor.isSelected())
								result = ImpresionCarpeta.buscarArchivoPorTamano(origen, '+', tamano, chckbxOcultos.isSelected());
							else
								result = ImpresionCarpeta.buscarArchivoPorTamano(origen, '-', tamano, chckbxOcultos.isSelected());
						}
					}catch(NumberFormatException ex){
						if(chckbxTamano.isSelected())
							javax.swing.JOptionPane.showConfirmDialog(null, "Tiene que introducir un numero entero", "Formulario incorrecto", 
				                javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
					
					nombre = textFieldNombre.getText();
					
					if(chckbxNombre.isSelected()){
						if(result != null && result.size()>0){
							result = ImpresionCarpeta.buscarArchivoPorNombre(result, nombre, chckbxOcultos.isSelected());
						}
						else{
							result = ImpresionCarpeta.buscarArchivoPorNombre(origen, nombre, chckbxOcultos.isSelected());
						}
					}
					
					for(int i = 0; i<result.size();i++){
						texto = texto.concat(ImpresionCarpeta.mostrarInfo(result.get(i)).replace(origen.getAbsolutePath(),".").concat("\n"));
					}
					textAreaResult.setText(texto);
					textAreaResult.setEnabled(true);
					textFieldEncontrados.setText(String.valueOf(result.size()));
					
					
				}
				else{
					javax.swing.JOptionPane.showConfirmDialog(null, "Tiene que introducir un origen para la búsqueda", "Formulario incorrecto", 
			                javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
				
				
				
					
				
			}
		});
		btnBuscar.setBounds(554, 589, 117, 25);
		contentPane.add(btnBuscar);
		
		
		
		JLabel lblTamanoEnBytes = new JLabel("Tamaño en bytes");
		lblTamanoEnBytes.setBounds(44, 478, 164, 15);
		contentPane.add(lblTamanoEnBytes);
		
		textFieldSize = new JTextField();
		textFieldSize.setEnabled(false);
		textFieldSize.setBounds(183, 473, 336, 25);
		contentPane.add(textFieldSize);
		textFieldSize.setColumns(10);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(32, 25, 639, 326);
		contentPane.add(scrollPane_1);
		
		textAreaResult = new JTextArea();
		scrollPane_1.setViewportView(textAreaResult);
		textAreaResult.setEnabled(false);
		textAreaResult.setEditable(false);
		
		textFieldPath = new JTextField();
		textFieldPath.setEditable(false);
		textFieldPath.setBounds(205, 552, 466, 30);
		contentPane.add(textFieldPath);
		textFieldPath.setColumns(10);
		
		JLabel lblEntradas = new JLabel("Encontrados:");
		lblEntradas.setBounds(457, 525, 99, 15);
		contentPane.add(lblEntradas);
		
		textFieldEncontrados = new JTextField();
		textFieldEncontrados.setEditable(false);
		textFieldEncontrados.setBounds(574, 520, 70, 25);
		contentPane.add(textFieldEncontrados);
		textFieldEncontrados.setColumns(10);
		
		rdbtnAntes = new JRadioButton("Antes");
		rdbtnAntes.setEnabled(false);
		rdbtnAntes.setSelected(true);
		buttonGroup_1.add(rdbtnAntes);
		rdbtnAntes.setBounds(353, 442, 70, 23);
		contentPane.add(rdbtnAntes);
		
		rdbtnDespues = new JRadioButton("Después");
		rdbtnDespues.setEnabled(false);
		buttonGroup_1.add(rdbtnDespues);
		rdbtnDespues.setBounds(437, 442, 87, 23);
		contentPane.add(rdbtnDespues);
		
		textFieldFecha = new JTextField();
		textFieldFecha.setEnabled(false);
		textFieldFecha.setBounds(44, 442, 301, 27);
		contentPane.add(textFieldFecha);
		textFieldFecha.setColumns(10);
		
		JLabel lblFechaModificacin = new JLabel("Fecha Modificación (yyyy-MM-dd):");
		lblFechaModificacin.setBounds(44, 415, 254, 15);
		contentPane.add(lblFechaModificacin);
		
		JLabel lblNombreDelArchivo = new JLabel("Nombre del archivo (expresión regular):");
		lblNombreDelArchivo.setBounds(32, 363, 301, 15);
		contentPane.add(lblNombreDelArchivo);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setEnabled(false);
		textFieldNombre.setBounds(149, 388, 522, 25);
		contentPane.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
		chckbxFecha = new JCheckBox("Fecha");
		chckbxFecha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enablerFecha(chckbxFecha.isSelected());
			}
		});
		chckbxFecha.setBounds(544, 442, 129, 23);
		contentPane.add(chckbxFecha);
		
		chckbxNombre = new JCheckBox("Nombre");
		chckbxNombre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enablerNombre(chckbxNombre.isSelected());
			}
		});
		chckbxNombre.setBounds(544, 359, 129, 23);
		contentPane.add(chckbxNombre);
		
		chckbxTamano = new JCheckBox("Tamaño");
		chckbxTamano.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enablerTamano(chckbxTamano.isSelected());
			}
		});
		chckbxTamano.setBounds(542, 495, 129, 23);
		contentPane.add(chckbxTamano);
		
		JButton btnGuardar = new JButton("Guardar Informe");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileOutputStream fos;
				try {
					fos = new FileOutputStream(origen.getAbsolutePath()+"salida.txt");
					PrintWriter pw = new PrintWriter(fos);
					pw.print(texto);
					pw.close();
				} catch (FileNotFoundException e1) {

				}
				
			}
		});
		btnGuardar.setBounds(44, 589, 195, 25);
		contentPane.add(btnGuardar);
	}
	
	public void enablerNombre(boolean estado){
		textFieldNombre.setEnabled(estado);
	}
	
	public void enablerFecha(boolean estado){
		textFieldFecha.setEnabled(estado);
		rdbtnAntes.setEnabled(estado);
		rdbtnDespues.setEnabled(estado);
	}
	
	public void enablerTamano(boolean estado){
		textFieldSize.setEnabled(estado);
		rdbtnMayor.setEnabled(estado);
		rdbtnMenores.setEnabled(estado);
	}
}
