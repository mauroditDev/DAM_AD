package accesoCatalogo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ImportarCSVTabla extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private JTextField usuario;
	DBmanager bd;
	Acceso acceso = new Acceso();
	private JPasswordField password;
	private JLabel estadoConex;
	private JPanel panelImport;
	private JButton btnImportar;
	private JTextField ruta;
	private JRadioButton rdbtnMySql;
	private JRadioButton rdbtnDerby;
	private JLabel lblSistemaDeLa;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JLabel lblIpONombre;
	private JLabel lblPuerto;
	private JLabel lblMensaje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImportarCSVTabla frame = new ImportarCSVTabla();
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
	public ImportarCSVTabla() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ImportarCSVTabla.class.getResource("/jdbcTablaCSV/import.png")));
		// setIconImage(Toolkit.getDefaultToolkit().getImage(ExportarTablaCSV.class.getResource("/JDBC/tablaCSV/bien.png")));
		setTitle("Importar datos desde un fichero CSV a una tabla");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 458);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBorder(new TitledBorder(null, "Conexion a MySQL",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 5, 485, 224);
		contentPane.add(panel);
		panel.setLayout(null);

		lblIpONombre = new JLabel("Servidor MySQL");
		lblIpONombre.setBounds(10, 31, 109, 14);
		panel.add(lblIpONombre);

		ip = new JTextField();
		ip.setText("127.0.0.1");
		ip.setBounds(120, 28, 165, 20);
		panel.add(ip);
		ip.setColumns(10);

		lblPuerto = new JLabel("Usuario");
		lblPuerto.setBounds(10, 64, 94, 14);
		panel.add(lblPuerto);

		usuario = new JTextField();
		usuario.setText("root");
		usuario.setBounds(120, 61, 165, 20);
		panel.add(usuario);
		usuario.setColumns(10);

		lblMensaje = new JLabel("Contrase\u00F1a");
		lblMensaje.setBounds(10, 92, 94, 14);
		panel.add(lblMensaje);

		JButton btnConectar = new JButton("Conectar");
		btnConectar
				.setToolTipText("Estblece conexi\u00F3n con el servidor seleccionado");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				conectarConBD();

			}
		});
		btnConectar.setBounds(297, 22, 120, 33);
		panel.add(btnConectar);
		getRootPane().setDefaultButton(btnConectar);

		password = new JPasswordField();
		password.setBounds(120, 92, 165, 20);
		panel.add(password);

		estadoConex = new JLabel("");
		estadoConex.setBounds(335, 64, 61, 55);
		panel.add(estadoConex);
		
		rdbtnMySql = new JRadioButton("MySql");
		rdbtnMySql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ip.setText("127.0.0.1");
				usuario.setText("root");
				usuario.setEnabled(true);
				usuario.setVisible(true);
				password.setText("");
				password.setEnabled(true);
				password.setVisible(true);
				lblIpONombre.setText("Servidor MySQL");
				lblMensaje.setText("Contraseña");
				lblPuerto.setText("Usuario");
			}
		});
		buttonGroup.add(rdbtnMySql);
		rdbtnMySql.setActionCommand("MySql");
		rdbtnMySql.setBounds(10, 162, 149, 23);
		panel.add(rdbtnMySql);
		
		rdbtnDerby = new JRadioButton("Derby");
		rdbtnDerby.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ip.setText("");
				usuario.setText("");
				usuario.setEnabled(false);
				usuario.setVisible(false);
				password.setText("");
				password.setEnabled(false);
				password.setVisible(false);
				lblIpONombre.setText("Carpeta Derby");
				lblMensaje.setText("");
				lblPuerto.setText("");
			}
		});
		buttonGroup.add(rdbtnDerby);
		rdbtnDerby.setActionCommand("Derby");
		rdbtnDerby.setBounds(10, 189, 149, 23);
		panel.add(rdbtnDerby);
		
		lblSistemaDeLa = new JLabel("Sistema de la BD");
		lblSistemaDeLa.setBounds(12, 138, 147, 15);
		panel.add(lblSistemaDeLa);

		panelImport = new JPanel();
		panelImport.setBackground(new Color(51, 204, 153));
		panelImport.setLayout(null);
		panelImport.setBorder(new TitledBorder(null, "CSV a importar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelImport.setBounds(10, 245, 485, 173);
		panelImport.setVisible(false);
		contentPane.add(panelImport);

		btnImportar = new JButton("Importar");
		btnImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				importarCSV(ruta.getText());
			}
		});
		btnImportar
				.setToolTipText("Exporta los datos de la tabla seleccionada a un fichero CSV");
		btnImportar.setBounds(192, 111, 120, 33);
		panelImport.add(btnImportar);
		
		ruta = new JTextField();
		ruta.setEditable(false);
		ruta.setFont(new Font("Tahoma", Font.PLAIN, 11));
		ruta.setColumns(10);
		ruta.setBounds(80, 44, 218, 23);
		panelImport.add(ruta);
		
		JLabel lblRutaCsv = new JLabel("Ruta CSV");
		lblRutaCsv.setForeground(Color.BLACK);
		lblRutaCsv.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblRutaCsv.setBackground(Color.WHITE);
		lblRutaCsv.setBounds(26, 36, 92, 39);
		panelImport.add(lblRutaCsv);
		
		JButton btnSeleccionar = new JButton("Seleccionar");
		btnSeleccionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ruta.setText(seleccionarCSV());
			}
		});
		btnSeleccionar.setOpaque(true);
		btnSeleccionar.setForeground(Color.BLACK);
		btnSeleccionar.setBackground(Color.LIGHT_GRAY);
		btnSeleccionar.setBounds(308, 44, 126, 23);
		panelImport.add(btnSeleccionar);
	}

	protected void importarCSV(String rutaCSV) {
		
		File archivo = new File(rutaCSV);
		int tipo = 0;
		switch(buttonGroup.getSelection().getActionCommand()){
			case "MySql":
				tipo = 0;
				break;
			case "Derby":
				tipo = 1;
				break;
		}
		if(!acceso.importarCsv(ip.getText(), usuario.getText(),new String(password.getPassword()), tipo, archivo)){
			JOptionPane.showMessageDialog(contentPane, 
					"O bien:"
					+ "\n\tLa ruta al fichero es incorrecta, debe ser: bd.tabla.csv"
					+ "\n\tLa tabla ya existe en la base de datos",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	private String seleccionarCSV() {
		JFileChooser fc=new JFileChooser(".");
		FileNameExtensionFilter filtroCSV = new FileNameExtensionFilter("Ficheros CSV (*.csv)", "csv");
		fc.setFileFilter(filtroCSV);
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (fc.showDialog(contentPane, "Seleccionar CSV")==JFileChooser.APPROVE_OPTION)
		{
			String nom= fc.getSelectedFile().getName();
			if (!nom.toLowerCase().matches("[a-z0-9]+\\.[a-z0-9]+\\.csv"))
			{
				JOptionPane.showMessageDialog(contentPane, "Debe ser: bd.tabla.csv", "Nombre de fichero incorrecto", JOptionPane.ERROR_MESSAGE);
				return null;
			} 
			return fc.getSelectedFile().getAbsolutePath();
		}
		else
			return null;
	}

	protected void conectarConBD() {

		panelImport.setVisible(false);

		try {
			int tipo = 0;
			switch(buttonGroup.getSelection().getActionCommand()){
				case "MySql":
					tipo = 0;
					break;
				case "Derby":
					tipo = 1;
					break;
			}
			bd = new DBmanager(ip.getText(), usuario.getText(),String.valueOf(password.getPassword()),tipo);
			estadoConex.setIcon(new ImageIcon(getClass().getResource("bien.png")));
			panelImport.setVisible(true);

		} catch (Exception e) {
			panelImport.setVisible(false);
			estadoConex
					.setIcon(new ImageIcon(getClass().getResource("mal.png")));
		}

	}

	public JLabel getEstadoConex() {
		return estadoConex;
	}

	
}
