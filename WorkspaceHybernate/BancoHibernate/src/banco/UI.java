package banco;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UI extends JFrame {

	DateFormat dateFormat;
	private ArrayList<Cuenta> cuentas;
	private Cuenta cuentaSel;
	private ArrayList<Cliente> clientes;
	private Cliente clienteSel;
	private Banco dbManager;
	private JPanel contentPane;
	private JMenuItem mntmCrearBd;
	private JList<String> listCli;
	private JList<String> listTit;
	private JLabel label_Tit;
	private JLabel label_Cue;
	private JScrollPane scrollPaneClientes;
	private JScrollPane scrollPaneTitular;
	private UI ui;
	private JTextField textFieldCantidad;
	private JTextField textFieldDesde;
	private JTextField textFieldHasta;
	private JButton btnReintegro;
	private JButton btnIngreso;
	private JButton btnExtracto;
	private JButton btnAnadir;

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
		setTitle("Operaciones con Cuentas");
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		ui = this;
		cuentaSel = new Cuenta();
		clienteSel = new Cliente();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1040, 416);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnTablas = new JMenu("Base de Datos");
		menuBar.add(mnTablas);
		
		mntmCrearBd = new JMenuItem("Crear BD");
		mntmCrearBd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				
//				if(!dbManager.createTables()){
//					javax.swing.JOptionPane.showConfirmDialog(null, "Algo ha ido mal", "Advertencia",
//							javax.swing.JOptionPane.PLAIN_MESSAGE);
//				}
				
				javax.swing.JOptionPane.showConfirmDialog(null, "Esta función está en desarrollo", "Lo sentimos...",
				javax.swing.JOptionPane.PLAIN_MESSAGE);
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
				new ClienteUI(dbManager, ui).setVisible(true);
			}
		});
		mnMantenimiento.add(mntmClientes);
		
		JMenuItem mntmSucursal = new JMenuItem("Sucursal");
		mntmSucursal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new SucursalUI(dbManager,ui).setVisible(true);
			}
		});
		mnMantenimiento.add(mntmSucursal);
		
		JMenuItem mntmCuenta = new JMenuItem("Cuenta");
		mntmCuenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new CuentaUI(dbManager,ui).setVisible(true);
			}
		});
		mnMantenimiento.add(mntmCuenta);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnReintegro = new JButton("Reintegro");
		btnReintegro.setEnabled(false);
		btnReintegro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if(cuentaSel.getId() == -1){
					JOptionPane.showConfirmDialog(ui, "Necesita seleccionar una cuenta","Error",
							JOptionPane.PLAIN_MESSAGE);
				}
				else{
					boolean sw=false;
					Iterator<Cliente> iter = cuentaSel.getClientes().iterator();
					Cliente titular = null;
					while(true){
						try{
							titular = (Cliente) iter.next();
						}catch(NoSuchElementException e){
							break;
						}
						if(titular.getId() == clienteSel.getId()){
							sw = true;
						}
						
					}
					if(sw){
						if(	!textFieldCantidad.getText().matches("[0-9]+")){
							JOptionPane.showConfirmDialog(ui, "La cantidad está mal introducida","Error",
									JOptionPane.PLAIN_MESSAGE);
						}
						else{
							if(cuentaSel.getSaldo() < Integer.valueOf(textFieldCantidad.getText().trim())){
								JOptionPane.showConfirmDialog(ui, "Saldo insuficiente",
										"Error", JOptionPane.PLAIN_MESSAGE);
							}
							else{
								int saldo = cuentaSel.getSaldo()+Integer.valueOf(textFieldCantidad.getText())*-1;
								cuentaSel.setSaldo(saldo);
							}
						}
					}
					else{
						JOptionPane.showConfirmDialog(ui, "Cliente no seleccionado o no autorizado",
								"Error", JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});
		btnReintegro.setBounds(12, 295, 159, 25);
		contentPane.add(btnReintegro);
		
		btnIngreso = new JButton("Ingreso");
		btnIngreso.setEnabled(false);
		btnIngreso.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cuentaSel.getId() == -1){
					JOptionPane.showConfirmDialog(ui, "Necesita seleccionar una cuenta","Error",
							JOptionPane.PLAIN_MESSAGE);
				}
				else{
					
					if(	!textFieldCantidad.getText().matches("[0-9]+")){
						JOptionPane.showConfirmDialog(ui, "La cantidad está mal introducida","Error",
								JOptionPane.PLAIN_MESSAGE);
					}
					else{
						cuentaSel.setSaldo(cuentaSel.getSaldo()+Integer.valueOf(textFieldCantidad.getText()));
					}
				}
			}
		});
		btnIngreso.setBounds(12, 332, 159, 25);
		contentPane.add(btnIngreso);
		
		btnExtracto = new JButton("Extracto");
		btnExtracto.setEnabled(false);
		btnExtracto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cuentaSel.getId() !=-1){
					
					try {
						Date desde = dateFormat.parse(textFieldDesde.getText());
						Date hasta = dateFormat.parse(textFieldHasta.getText());
						imprimirExtr(cuentaSel.extracto(desde,hasta));
						reset();
					} catch (ParseException e1) {
						e1.printStackTrace();
						JOptionPane.showConfirmDialog(ui, "Debe seleccionar fechas válidas"
								+ "\n\t(dd/mm/aaaa)",
								"Error", JOptionPane.PLAIN_MESSAGE);
					}
				}
				else{
					JOptionPane.showConfirmDialog(ui, "Debe seleccionar una cuenta",
							"Error", JOptionPane.PLAIN_MESSAGE);
				}
			}

			private void imprimirExtr(ArrayList<Movimiento> extracto) {
				if(extracto.isEmpty())
					JOptionPane.showConfirmDialog(ui, "Sin movimientos en esa cuenta en ese periodo",
							"Error", JOptionPane.PLAIN_MESSAGE);
				else{
					new ExtractoUI(extracto).setVisible(true);
				}
			}
		});
		btnExtracto.setBounds(611, 307, 159, 25);
		contentPane.add(btnExtracto);
		
		btnAnadir = new JButton("Añadir/Quitar");
		btnAnadir.setEnabled(false);
		btnAnadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(clienteSel.getId() == -1){
					JOptionPane.showConfirmDialog(ui, "Debe seleccionar un cliente",
							"Error", JOptionPane.PLAIN_MESSAGE);
				}
				else{
					if(cuentaSel.getId() == -1){
						JOptionPane.showConfirmDialog(ui, "Debe seleccionar una cuenta",
								"Error", JOptionPane.PLAIN_MESSAGE);
					}
					else{
						if(vincular(clienteSel, cuentaSel)){
							reset();
						}
						else{
							JOptionPane.showConfirmDialog(ui, "No pudo actualizarse la cuenta",
									"Error", JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
			}

			private boolean vincular(Cliente clienteSel, Cuenta cuentaSel) {
				boolean esCliente = false;
				Iterator<Cliente> iter = cuentaSel.getClientes().iterator();
				Cliente titular = null;
				while(true){
					try{
						titular = iter.next();
					}catch(NoSuchElementException e){
						break;
					}
					if(titular.getId() == clienteSel.getId()){
						esCliente = true;
					}
				}
				if(!esCliente){
					clienteSel.addCuenta(cuentaSel);
					cuentaSel.addCliente(clienteSel);
					clienteSel.setNombre(clienteSel.getNombre()+"GG");
					
					System.out.println("##Cuenta: "+cuentaSel.getId());
					Iterator<Cliente> cli = cuentaSel.getClientes().iterator();
					while(true){
						try{System.out.println("---cliente: "+cli.next().getId());}
						catch(NoSuchElementException excfe){
							break;
						}
					}
					System.out.println("##Cliente: "+clienteSel.getId());
					Iterator<Cuenta> cue = clienteSel.getCuentas().iterator();
					while(true){
						try{System.out.println("---cuenta: "+cue.next().getId());}
						catch(NoSuchElementException excfe){
							break;
						}
					}
					
					
					dbManager.guardarCuenta(cuentaSel);
					//dbManager.guardarCliente(clienteSel);
					return true;
				}
				else{
					clienteSel.setNombre(clienteSel.getNombre()+"GG");
					clienteSel.removeCuenta(cuentaSel);
					cuentaSel.removeCliente(clienteSel);
					dbManager.guardarCuenta(cuentaSel);
					dbManager.guardarCliente(clienteSel);
					return true;
				}
			}
		});
		btnAnadir.setBounds(363, 17, 159, 25);
		contentPane.add(btnAnadir);
		
		scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setBounds(12, 54, 589, 229);
		contentPane.add(scrollPaneClientes);
		
		listCli = new JList<>();
		scrollPaneClientes.setViewportView(listCli);
		
		scrollPaneTitular = new JScrollPane();
		scrollPaneTitular.setBounds(613, 54, 393, 229);
		contentPane.add(scrollPaneTitular);
		
		listTit = new JList<>();
		scrollPaneTitular.setViewportView(listTit);
		
		JLabel lblCuenta = new JLabel("Cuenta");
		lblCuenta.setBounds(668, 17, 70, 15);
		contentPane.add(lblCuenta);
		
		JLabel lblTitular = new JLabel("Titular");
		lblTitular.setBounds(28, 17, 70, 15);
		contentPane.add(lblTitular);
		
		label_Cue = new JLabel("");
		label_Cue.setBounds(722, 17, 125, 15);
		contentPane.add(label_Cue);
		
		label_Tit = new JLabel("");
		label_Tit.setBounds(85, 17, 100, 15);
		contentPane.add(label_Tit);
		
		textFieldCantidad = new JTextField();
		textFieldCantidad.setBounds(227, 313, 114, 19);
		contentPane.add(textFieldCantidad);
		textFieldCantidad.setColumns(10);
		
		JLabel label = new JLabel("€:");
		label.setBounds(183, 315, 40, 15);
		contentPane.add(label);
		
		textFieldDesde = new JTextField();
		textFieldDesde.setText("01/01/2000");
		textFieldDesde.setBounds(892, 296, 114, 19);
		contentPane.add(textFieldDesde);
		textFieldDesde.setColumns(10);
		
		textFieldHasta = new JTextField();
		textFieldHasta.setText(dateFormat.format(new Date()));
		textFieldHasta.setBounds(892, 327, 114, 19);
		contentPane.add(textFieldHasta);
		textFieldHasta.setColumns(10);
		
		JLabel lblDesde = new JLabel("Desde:");
		lblDesde.setBounds(812, 300, 70, 15);
		contentPane.add(lblDesde);
		
		JLabel lblHasta = new JLabel("Hasta:");
		lblHasta.setBounds(812, 331, 70, 15);
		contentPane.add(lblHasta);
		
		JButton btnRefrescar = new JButton("Refrescar");
		btnRefrescar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("cuentas:");
				if(dbManager.persistir()){
					javax.swing.JOptionPane.showConfirmDialog(null, "bien hecho", "qué guay",
							javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
				else{
					javax.swing.JOptionPane.showConfirmDialog(null, "MAL", "Lo sentimos...",
							javax.swing.JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnRefrescar.setBounds(857, 12, 114, 25);
		contentPane.add(btnRefrescar);
		dbManager = new Banco();
		
		rellenarTablaTit();
		rellenarTablaCli();
	}
	
	public void rellenarTablaTit(){
		
		cuentas = dbManager.getCuentas();
		String[] data = new String[cuentas.size()];
		listTit = new JList<>(data);
		
		for(int i = 0; i<cuentas.size();i++){
			data[i]="id: "+String.valueOf(cuentas.get(i).getId())+" | ";
			data[i]+="titulares (" + cuentas.get(i).getClientes().size()+"):  ";
			Iterator<Cliente> iter = cuentas.get(i).getClientes().iterator();
			Cliente titular = null;
			while(true){
				try{
					titular = iter.next();
				}catch(NoSuchElementException e){
					break;
				}
				data [i] += String.valueOf(titular.getId()) + ", ";
			}
			data[i] = data[i].substring(0,data[i].length()-2);
			data[i]+=" | ";
			data[i]+="saldo: "+String.valueOf(cuentas.get(i).getSaldo());
		}
		
		listTit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sel = listTit.getSelectedValue();
				if(sel!=null){
					sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
					cuentaSel = dbManager.getCuenta(sel.trim());
					label_Cue.setText(String.valueOf(cuentaSel.getId()));
					btnIngreso.setEnabled(true);
					btnExtracto.setEnabled(true);
					if(clienteSel.getId() != -1){
						btnReintegro.setEnabled(true);
						btnAnadir.setEnabled(true);
					}
				}
			}
				
		});
		
		scrollPaneTitular.setViewportView(listTit);
		
	}
	
	public void rellenarTablaCli(){
			

			clientes = dbManager.getClientes();
			String[] data = new String[clientes.size()];
			
			listCli = new JList<>(data);
			
			for(int i = 0; i<clientes.size();i++){
				data[i]="id: "+String.valueOf(clientes.get(i).getId())+" | ";
				data[i]+="nom: "+clientes.get(i).getNombre()+" | ";
				data[i]+="nac: "+dateFormat.format(clientes.get(i).getFNac())+" | ";
				data[i]+="dir: "+clientes.get(i).getDireccion();
			}
			listCli.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					String sel = listCli.getSelectedValue();
					if(sel!=null){
						sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
						clienteSel = dbManager.getClient(sel.trim());
						label_Tit.setText(String.valueOf(clienteSel.getId()));
						if(cuentaSel.getId() != -1){
							btnReintegro.setEnabled(true);
							btnAnadir.setEnabled(true);
						}
							
					}
				}
					
			});
			
			scrollPaneClientes.setViewportView(listCli);

	}
	
	public void reset(){

		cuentaSel = new Cuenta();
		clienteSel = new Cliente();
		rellenarTablaTit();
		rellenarTablaCli();
		btnIngreso.setEnabled(false);
		btnReintegro.setEnabled(false);
		btnAnadir.setEnabled(false);
		btnExtracto.setEnabled(false);
		
	}
}
