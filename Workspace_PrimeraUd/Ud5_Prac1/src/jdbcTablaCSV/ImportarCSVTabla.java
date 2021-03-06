package jdbcTablaCSV;

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

public class ImportarCSVTabla extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private JTextField usuario;
	BaseDatos bd;
	private JPasswordField password;
	private JLabel estadoConex;
	private JPanel panelImport;
	private JButton btnImportar;
	private JTextField ruta;

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
		setBounds(100, 100, 522, 380);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBorder(new TitledBorder(null, "Conexion a MySQL",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 5, 485, 133);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblIpONombre = new JLabel("Servidor MySQL");
		lblIpONombre.setBounds(10, 31, 109, 14);
		panel.add(lblIpONombre);

		ip = new JTextField();
		ip.setText("127.0.0.1");
		ip.setBounds(120, 28, 107, 20);
		panel.add(ip);
		ip.setColumns(10);

		JLabel lblPuerto = new JLabel("Usuario");
		lblPuerto.setBounds(10, 64, 94, 14);
		panel.add(lblPuerto);

		usuario = new JTextField();
		usuario.setText("root");
		usuario.setBounds(120, 61, 107, 20);
		panel.add(usuario);
		usuario.setColumns(10);

		JLabel lblMensaje = new JLabel("Contrase\u00F1a");
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
		password.setBounds(120, 92, 107, 20);
		panel.add(password);

		estadoConex = new JLabel("");
		estadoConex.setBounds(335, 64, 61, 55);
		panel.add(estadoConex);

		panelImport = new JPanel();
		panelImport.setBackground(new Color(51, 204, 153));
		panelImport.setLayout(null);
		panelImport.setBorder(new TitledBorder(null, "CSV a importar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelImport.setBounds(10, 160, 485, 173);
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
		
		String nomFich=new File(rutaCSV).getName();//Obtiene bd.tabla.csv (final de la ruta)
		String [] parte=nomFich.split("\\.");
		String base=parte[0];
		String tabla=parte[1];
		
		try {
			int n=bd.importarCSVTabla(base,tabla,rutaCSV);
			JOptionPane.showMessageDialog(contentPane,base+"."+tabla+ " se han insertado "+n+" filas");
			} catch (SQLException e) {
			JOptionPane.showMessageDialog(contentPane, e.getErrorCode() + "-"
					+ e.getMessage(), "Error al importar datos",
					JOptionPane.ERROR_MESSAGE);
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
			bd = new BaseDatos(ip.getText(), usuario.getText(),String.valueOf(password.getPassword()));
			estadoConex.setIcon(new ImageIcon(getClass().getResource("bien.png")));
			panelImport.setVisible(true);

		} catch (SQLException e) {
			panelImport.setVisible(false);
			estadoConex
					.setIcon(new ImageIcon(getClass().getResource("mal.png")));
			JOptionPane.showMessageDialog(contentPane, e.getErrorCode() + "-"
					+ e.getMessage(), "Error al conectar con MySQL",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	public JLabel getEstadoConex() {
		return estadoConex;
	}

	
}
