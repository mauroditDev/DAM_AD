package practica1;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UI extends JFrame {

	private DBmanager dbManager;
	private JPanel contentPane;
	private JMenuItem mntmCrearBd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
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
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTablas = new JMenu("Base de Datos");
		menuBar.add(mnTablas);
		
		mntmCrearBd = new JMenuItem("Crear BD");
		mntmCrearBd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(!dbManager.createTables()){
						javax.swing.JOptionPane.showConfirmDialog(null, "Algo ha ido mal", "Advertencia",
								javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
				} catch (YaExisteException e) {
					javax.swing.JOptionPane.showConfirmDialog(null, "La base de datos ya existe", "Advertencia",
							javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		mnTablas.add(mntmCrearBd);
		
		JMenuItem mntmReiniciarbd = new JMenuItem("ReiniciarBD");
		mntmReiniciarbd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(javax.swing.JOptionPane.showConfirmDialog(null, "¿Seguro que quiere eliminar todo el contenido"
						+ "de la base de datos?","Pide Confirmación", javax.swing.JOptionPane.YES_OPTION)==0){
					if(dbManager.reiniciarTablas()){
						javax.swing.JOptionPane.showConfirmDialog(null, "La base de datos ha sido reiniciada", "OK",
								javax.swing.JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		mnTablas.add(mntmReiniciarbd);
		
		JMenu mnMantenimiento = new JMenu("Mantenimiento");
		menuBar.add(mnMantenimiento);
		
		JMenuItem mntmClientes = new JMenuItem("Clientes");
		mntmClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new ClienteUI(dbManager).setVisible(true);
			}
		});
		mnMantenimiento.add(mntmClientes);
		
		JMenuItem mntmSucursal = new JMenuItem("Sucursal");
		mntmSucursal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SucursalUI(dbManager).setVisible(true);
			}
		});
		mnMantenimiento.add(mntmSucursal);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		dbManager = new DBmanager();
	}

}
