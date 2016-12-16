package ejercicio3;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class Ejercicio3 extends JFrame {

	private JPanel contentPane;
	private JTextField txtNum;
	private JTextField txtAno;
	private JTextField txtPrem;
	private DBmanager dbManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Ejercicio3 frame = new Ejercicio3();
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
	public Ejercicio3() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		dbManager = new DBmanager();
		
		JLabel lblCrearUnSorteo = new JLabel("Crear un sorteo");
		lblCrearUnSorteo.setBounds(12, 12, 146, 15);
		contentPane.add(lblCrearUnSorteo);
		
		JLabel lblConsultarUnResultado = new JLabel("Consultar un resultado");
		lblConsultarUnResultado.setBounds(257, 12, 179, 15);
		contentPane.add(lblConsultarUnResultado);
		
		txtAno = new JTextField();
		txtAno.setText("2016");
		txtAno.setBounds(12, 81, 114, 19);
		contentPane.add(txtAno);
		txtAno.setColumns(10);
		
		txtPrem = new JTextField();
		txtPrem.setText("2");
		txtPrem.setBounds(12, 174, 114, 19);
		contentPane.add(txtPrem);
		txtPrem.setColumns(10);
		
		JLabel lblAoDelSorteo = new JLabel("Año del sorteo");
		lblAoDelSorteo.setBounds(12, 54, 146, 15);
		contentPane.add(lblAoDelSorteo);
		
		JLabel lblCantidadDePremios = new JLabel("Cantidad de premios");
		lblCantidadDePremios.setBounds(12, 147, 179, 15);
		contentPane.add(lblCantidadDePremios);
		
		JButton btnCrearSorteo = new JButton("Crear Sorteo");
		btnCrearSorteo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!txtAno.getText().matches("[0-9]{4}")){
					JOptionPane.showConfirmDialog(null, "Necesita introducir el año (aaaa)",
							"Aviso",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					if(!txtPrem.getText().matches("[0-9]{1,5}")){
						JOptionPane.showConfirmDialog(null, "Los premios deben ser una cantidad entre 0 y 99999",
								"Aviso",JOptionPane.PLAIN_MESSAGE);
					}
					else{
						if(dbManager.CreateDb(Integer.valueOf(txtAno.getText()), Integer.valueOf(txtPrem.getText()))){
							JOptionPane.showConfirmDialog(null, "Sorteo creado exitosamente!",
									"Éxito!",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}
		});
		btnCrearSorteo.setBounds(12, 237, 146, 25);
		contentPane.add(btnCrearSorteo);
		
		txtNum = new JTextField();
		txtNum.setText("1000");
		txtNum.setBounds(257, 81, 114, 19);
		contentPane.add(txtNum);
		txtNum.setColumns(10);
		
		JLabel lblNumeroAConsultar = new JLabel("Numero a consultar");
		lblNumeroAConsultar.setBounds(257, 54, 179, 15);
		contentPane.add(lblNumeroAConsultar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtNum.getText().matches("[0-9]{1,5}")){
					JOptionPane.showConfirmDialog(null, "El numero de participante debe ser entre 0 y 99999",
							"Aviso",JOptionPane.PLAIN_MESSAGE);
				}
				else{
					if(!txtAno.getText().matches("[0-9]{4}")){
						JOptionPane.showConfirmDialog(null, "El año está mal introducido (aaaa)",
								"Aviso",JOptionPane.PLAIN_MESSAGE);
					}
					else{
						int result = dbManager.comprobar(Integer.valueOf(txtAno.getText()),Integer.valueOf(txtNum.getText()));
						if(result == -99){
							JOptionPane.showConfirmDialog(null, "No hay registro de sorteo para ese año",
									"Aviso",JOptionPane.PLAIN_MESSAGE);
						}
						else{
							if(result == -1){
								JOptionPane.showConfirmDialog(null, "El numero indicado no salió premiado ese año",
										"Lo sentimos...",JOptionPane.PLAIN_MESSAGE);
							}
							else{
								JOptionPane.showConfirmDialog(null, "¡¡¡El numero indicado salió premiado ese año con "+
										String.valueOf(result)+"€!!!",
										"Enhorabuena!!",JOptionPane.PLAIN_MESSAGE);
							}
						}
					}
					
				}
			}
		});
		btnConsultar.setBounds(283, 118, 117, 25);
		contentPane.add(btnConsultar);
	}

}
