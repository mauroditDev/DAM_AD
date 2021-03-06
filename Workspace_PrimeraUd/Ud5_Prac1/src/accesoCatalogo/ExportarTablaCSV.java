package accesoCatalogo;

import java.awt.BorderLayout;
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
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;

import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class ExportarTablaCSV extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private JTextField usuario;
	DBmanager bd;
	Acceso acceso = new Acceso();
	private JPasswordField password;
	private JLabel estadoConex;
	private JPanel panelExport;
	private JComboBox<String> bases;
	private JComboBox<String> tablas;
	private JButton btnExportar;
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
					ExportarTablaCSV frame = new ExportarTablaCSV();
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
	public ExportarTablaCSV() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ExportarTablaCSV.class.getResource("/jdbcTablaCSV/export.png")));
		// setIconImage(Toolkit.getDefaultToolkit().getImage(ExportarTablaCSV.class.getResource("/JDBC/tablaCSV/bien.png")));
		setTitle("Exportar datos de una tabla a fichero CSV");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 522, 531);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 204));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(102, 153, 255));
		panel.setBorder(new TitledBorder(null, "Conexion a MySQL",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 5, 485, 230);
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

		JButton btnNewButton = new JButton("Conectar");
		btnNewButton
				.setToolTipText("Estblece conexi\u00F3n con el servidor seleccionado");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				conectarConBD();

			}
		});
		btnNewButton.setBounds(297, 22, 120, 33);
		panel.add(btnNewButton);

		password = new JPasswordField();
		password.setBounds(120, 92, 165, 20);
		panel.add(password);

		estadoConex = new JLabel("");
		estadoConex.setBounds(335, 64, 61, 55);
		panel.add(estadoConex);
		
		JRadioButton rdbtnMysql = new JRadioButton("MySql");
		rdbtnMysql.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		buttonGroup.add(rdbtnMysql);
		rdbtnMysql.setBounds(34, 150, 149, 23);
		rdbtnMysql.setActionCommand("MySql");
		panel.add(rdbtnMysql);
		
		JRadioButton rdbtnDerby = new JRadioButton("Derby");
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
		rdbtnDerby.setBounds(34, 177, 149, 23);
		rdbtnDerby.setActionCommand("Derby");
		panel.add(rdbtnDerby);
		
		JLabel lblSistemaDeLa = new JLabel("Sistema de la DB");
		lblSistemaDeLa.setBounds(10, 127, 272, 15);
		panel.add(lblSistemaDeLa);

		panelExport = new JPanel();
		panelExport.setBackground(new Color(51, 204, 153));
		panelExport.setLayout(null);
		panelExport.setBorder(new TitledBorder(null, "Tabla a exportar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelExport.setBounds(10, 247, 485, 250);
		panelExport.setVisible(false);
		contentPane.add(panelExport);

		btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String base=(String) bases.getSelectedItem();
				String tabla=(String) tablas.getSelectedItem();
				int tipo = 0;
				switch(buttonGroup.getSelection().getActionCommand()){
					case "MySql":
						tipo = 0;
						break;
					case "Derby":
						tipo = 1;
						break;
				}
				File res = acceso.exportarCsv(ip.getText(), usuario.getText(), String.valueOf(password.getPassword()), tipo, base, tabla);
				if( res != null){
					JOptionPane.showMessageDialog(contentPane, "exportado con éxito en " + res.getAbsolutePath());
				}
			}
		});
		btnExportar
				.setToolTipText("Exporta los datos de la tabla seleccionada a un fichero CSV");
		btnExportar.setBounds(178, 123, 120, 33);
		panelExport.add(btnExportar);

		bases = new JComboBox<String>();
		bases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 //JOptionPane.showMessageDialog(contentPane,bases.getSelectedItem());
				// Borra elementos de la combo que puduese haber de selecciones
				// de BD previas
				 
				
				tablas.removeAllItems();
				ArrayList<String> lista;
				int tipo = 0;
				switch(buttonGroup.getSelection().getActionCommand()){
					case "MySql":
						tipo = 0;
						break;
					case "Derby":
						tipo = 1;
						break;
				}
				lista = bd.getTablasFromCat((String) bases.getSelectedItem(),tipo);
				System.out.println(tipo);
				for (String b : lista) {
					tablas.addItem(b);
				}
			}
		});
		bases.setBounds(10, 55, 221, 25);
		panelExport.add(bases);

		JLabel lblNewLabel = new JLabel("Seleccione Base de Datos");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 34, 221, 14);
		panelExport.add(lblNewLabel);

		JLabel lblSeleccioneTabla = new JLabel("Seleccione Tabla");
		lblSeleccioneTabla.setHorizontalAlignment(SwingConstants.CENTER);
		lblSeleccioneTabla.setBounds(307, 34, 156, 14);
		panelExport.add(lblSeleccioneTabla);

		tablas = new JComboBox<String>();
		tablas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		tablas.setBounds(307, 55, 166, 25);
		panelExport.add(tablas);
	}

	protected void conectarConBD() {


		panelExport.setVisible(false);

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
			panelExport.setVisible(true);
			bases.removeAllItems();
			tablas.removeAllItems();
			ArrayList<String> lista = new ArrayList<String>();
			switch(tipo){
			case 0:
				lista = bd.getCatalogos();
				break;
			case 1:
				lista = bd.getEsquemas();
				break;
			}
			
			for (String b : lista) {
					bases.addItem(b);
			}

		} catch (Exception e) {
			panelExport.setVisible(false);
			estadoConex
					.setIcon(new ImageIcon(getClass().getResource("mal.png")));
		}

	}

	public JLabel getEstadoConex() {
		return estadoConex;
	}

	public JPanel getPanelExport() {
		return panelExport;
	}

	public JComboBox getBases() {
		return bases;
	}

	public JComboBox getTablas() {
		return tablas;
	}
	public JButton getBtnExportar() {
		return btnExportar;
	}
}
