package ejercicio2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class Ejercicio2 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio2 frame = new Ejercicio2();
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
	public Ejercicio2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 151);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRutaALos = new JLabel("Ruta a los archivos xml");
		lblRutaALos.setBounds(12, 29, 424, 15);
		contentPane.add(lblRutaALos);
		
		textField = new JTextField();
		textField.setBounds(12, 56, 424, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnObtenerTxt = new JButton("Obtener txt");
		btnObtenerTxt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().isEmpty()){
					JOptionPane.showConfirmDialog(null,
							"La ruta está vacía","Error de campo",
							JOptionPane.PLAIN_MESSAGE);
				}
				else{
					File archivo = new File(textField.getText());
					if(!archivo.exists()){
						JOptionPane.showConfirmDialog(null,
								"La ruta insertada no existe","Error",
								JOptionPane.PLAIN_MESSAGE);
					}
					else{
						if(archivo.isDirectory()){
							if(!new File(archivo.getAbsolutePath()+"/TotalEstaciones.xml").exists() &&
									!new File(archivo.getAbsolutePath()+"/TotalEstaciones.xml").exists()){
								JOptionPane.showConfirmDialog(null,
										"La ruta debe no contiene los xml requeridos","Error",
										JOptionPane.PLAIN_MESSAGE);
							}
							else{
								JOptionPane.showConfirmDialog(null,
										"Éxito!","éxito",
										JOptionPane.PLAIN_MESSAGE);
								construirTxts(archivo);
							}
						}
						else{
							JOptionPane.showConfirmDialog(null,
									"La ruta debe ser un directorio","Error",
									JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}
		});
		btnObtenerTxt.setBounds(171, 87, 117, 25);
		contentPane.add(btnObtenerTxt);
	}

	protected void construirTxts(File archivo) {
		
		try{
			
			PrintWriter pw = new PrintWriter(new FileWriter(
					archivo.getAbsolutePath()+"/TotalEstaciones.txt"));
			
			DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = dbfac.newDocumentBuilder();
			Document doc = docBuilder.parse(archivo.getAbsolutePath()+"/TotalEstaciones.xml");
			Element raiz = (Element)doc.getFirstChild();
			NodeList lista = raiz.getChildNodes();
			for(int i = 0; i< lista.getLength(); i++){
				if(lista.item(i).getNodeType() == Node.ELEMENT_NODE){
					if(lista.item(i).hasAttributes()){
						pw.write(lista.item(i).getAttributes().item(0).getNodeValue());
						pw.write(";");
						NodeList elVentas = lista.item(i).getChildNodes();
						for(int j = 0; j<elVentas.getLength(); j++){
							if(elVentas.item(j).getNodeType() == Node.ELEMENT_NODE){
								pw.write(elVentas.item(j).getTextContent());
								pw.write("\n");
							}
						}
					}
					
				}
			}
			
			pw.close();
			pw = new PrintWriter(new FileWriter(
					archivo.getAbsolutePath()+"/TotalFechas.txt"));
			
			doc = docBuilder.parse(archivo.getAbsolutePath()+"/TotalFechas.xml");
			raiz = (Element)doc.getFirstChild();
			lista = raiz.getChildNodes();
			for(int i = 0; i< lista.getLength(); i++){
				if(lista.item(i).getNodeType() == Node.ELEMENT_NODE){
					System.out.println(lista.item(i).getNodeName());
					if(lista.item(i).hasAttributes()){
						pw.write(lista.item(i).getAttributes().item(0).getNodeValue());
						pw.write(";");
						NodeList elVentas = lista.item(i).getChildNodes();
						for(int j = 0; j<elVentas.getLength(); j++){
							if(elVentas.item(j).getNodeType() == Node.ELEMENT_NODE){
								pw.write(elVentas.item(j).getTextContent());
								pw.write("\n");
							}
						}
					}
					
				}
			}
			pw.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
