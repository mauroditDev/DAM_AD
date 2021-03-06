package jdbcTablaCSV;

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

public class ExportarTablaCSV extends JFrame {

	private JPanel contentPane;
	private JTextField ip;
	private JTextField usuario;
	BaseDatos bd;
	private JPasswordField password;
	private JLabel estadoConex;
	private JPanel panelExport;
	private JComboBox bases;
	private JComboBox tablas;
	private JButton btnExportar;

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
		setBounds(100, 100, 522, 459);
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
		password.setBounds(120, 92, 107, 20);
		panel.add(password);

		estadoConex = new JLabel("");
		estadoConex.setBounds(335, 64, 61, 55);
		panel.add(estadoConex);

		panelExport = new JPanel();
		panelExport.setBackground(new Color(51, 204, 153));
		panelExport.setLayout(null);
		panelExport.setBorder(new TitledBorder(null, "Tabla a exportar",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelExport.setBounds(10, 160, 485, 250);
		panelExport.setVisible(false);
		contentPane.add(panelExport);

		btnExportar = new JButton("Exportar");
		btnExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String base=(String) bases.getSelectedItem();
				String tabla=(String) tablas.getSelectedItem();
				try {
					int filas=bd.exportarTablaCSV(base,tabla,null);
					String mensaje=filas+" filas exportadas en "+ new File(base+"."+tabla+".csv").getAbsolutePath();
					JOptionPane.showMessageDialog(contentPane, mensaje);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnExportar
				.setToolTipText("Exporta los datos de la tabla seleccionada a un fichero CSV");
		btnExportar.setBounds(178, 123, 120, 33);
		panelExport.add(btnExportar);

		bases = new JComboBox();
		bases.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 //JOptionPane.showMessageDialog(contentPane,bases.getSelectedItem());
				// Borra elementos de la combo que puduese haber de selecciones
				// de BD previas
				 
				
				tablas.removeAllItems();
				ArrayList<String> lista;
				try {
					lista = bd.dameTablas((String) bases.getSelectedItem());
					for (String b : lista) {
						tablas.addItem(b);
					}
				} catch (SQLException e) {
					e.printStackTrace();
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

		tablas = new JComboBox();
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
			bd = new BaseDatos(ip.getText(), usuario.getText(),String.valueOf(password.getPassword()));
			estadoConex.setIcon(new ImageIcon(getClass().getResource("bien.png")));
			panelExport.setVisible(true);
			// Borra elementos de la combo que pudiese haber de conexiones
			// previas
			bases.removeAllItems();
			tablas.removeAllItems();
			ArrayList<String> lista = bd.dameBases();
			for (String b : lista) {
					bases.addItem(b);
			}
		} catch (SQLException e) {
			panelExport.setVisible(false);
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
