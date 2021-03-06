package ejercicio_tablas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.FileWriter;
import java.io.PrintWriter;

public class GUI extends JFrame {
	private DBmanager db;
	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					GUI frame = new GUI();
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
	public GUI() {
		db = new DBmanager();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 125, 424, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNombreDeLa = new JLabel("Nombre de la base de datos:");
		lblNombreDeLa.setBounds(12, 98, 424, 15);
		contentPane.add(lblNombreDeLa);
		
		JButton btnGenerar = new JButton("Generar");
		btnGenerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					
					PrintWriter pw = new PrintWriter(new FileWriter("./"+textField.getText()+".sql"));
					pw.println(db.getEsquema(textField.getText()));
					javax.swing.JOptionPane.showConfirmDialog(null, "Éxito", "Éxito",
							javax.swing.JOptionPane.PLAIN_MESSAGE);
				}catch (Exception ex){
					ex.printStackTrace();
					javax.swing.JOptionPane.showConfirmDialog(null, "Algo ha ido mal", "Advertencia",
							javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
				
			}
		});
		btnGenerar.setBounds(319, 183, 117, 25);
		contentPane.add(btnGenerar);
	}
}
