package practica1;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class ClienteUI extends JDialog {

	private Banco dbManager;
	private ArrayList<Cliente> clientes;
	Date fecha;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNombre;
	private JTextField textFieldF_nac;
	private JTextField textFieldDireccion;
	private JTextField textFieldId;
	private JList<String> list;
	private JScrollPane scrollPane;
	private JTextField textFieldNif;
	DateFormat dateFormat;
	private JButton btnEliminar;
	private JButton btnBuscar;
	private JButton btnAnadir;
	private JButton okButton;


	/**
	 * Create the dialog.
	 */
	public ClienteUI(Banco dbman, final UI padre) {
		padre.setEnabled(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		setTitle("Clientes");
		dbManager = dbman;
		setBounds(100, 100, 496, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(12, 53, 159, 19);
		contentPanel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		textFieldNombre.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						btnAnadir.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						okButton.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
				else{
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						okButton.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(true);
						btnEliminar.setEnabled(true);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
			}
		});
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(12, 26, 70, 15);
		contentPanel.add(lblNombre);
		
		JLabel lblFechaNacimiento = new JLabel("Fecha Nacimiento");
		lblFechaNacimiento.setBounds(208, 26, 159, 15);
		contentPanel.add(lblFechaNacimiento);
		
		textFieldF_nac = new JTextField();
		textFieldF_nac.setColumns(10);
		textFieldF_nac.setBounds(208, 53, 159, 19);
		contentPanel.add(textFieldF_nac);
		textFieldF_nac.setText(dateFormat.format(new Date()));
		textFieldF_nac.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						btnAnadir.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						okButton.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
				else{
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						okButton.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(true);
						btnEliminar.setEnabled(true);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
			}
		});
		
		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setBounds(12, 84, 70, 15);
		contentPanel.add(lblDireccion);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setColumns(10);
		textFieldDireccion.setBounds(12, 111, 413, 19);
		textFieldDireccion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						btnAnadir.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						okButton.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
				else{
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						okButton.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(true);
						btnEliminar.setEnabled(true);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
			}
		});
		contentPanel.add(textFieldDireccion);
		
		textFieldId = new JTextField();
		textFieldId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						btnAnadir.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						okButton.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
				else{
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						okButton.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(true);
						btnEliminar.setEnabled(true);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
			}
		});
		textFieldId.setColumns(10);
		textFieldId.setBounds(12, 169, 159, 19);
		contentPanel.add(textFieldId);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(12, 142, 70, 15);
		contentPanel.add(lblId);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 200, 470, 230);
		contentPanel.add(scrollPane);
		list = new JList<String>();
		scrollPane.setViewportView(list);
		
		JLabel lblNif = new JLabel("NIF");
		lblNif.setBounds(208, 142, 70, 15);
		contentPanel.add(lblNif);
		
		textFieldNif = new JTextField();
		textFieldNif.setColumns(10);
		textFieldNif.setBounds(208, 169, 159, 19);
		textFieldNif.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						btnAnadir.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						okButton.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
				else{
					if(!textFieldNif.getText().isEmpty() && !textFieldDireccion.getText().isEmpty() &&
							!textFieldNombre.getText().isEmpty()){
						okButton.setEnabled(true);
						btnBuscar.setEnabled(false);
						btnEliminar.setEnabled(false);
						btnAnadir.setEnabled(false);
					}
					else{
						btnBuscar.setEnabled(true);
						btnEliminar.setEnabled(true);
						btnAnadir.setEnabled(false);
						okButton.setEnabled(false);
					}
				}
			}
		});
		contentPanel.add(textFieldNif);
		
		rellenarTabla();
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Modificar");
				okButton.setEnabled(false);
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldId.getText().length()>0 ){
							if(javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
								"confirme que quiere modificar a id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								try{
									Cliente cli = new Cliente(textFieldId.getText(), textFieldNombre.getText(),
										dateFormat.parse(textFieldF_nac.getText()), textFieldDireccion.getText(), textFieldNif.getText());
									if(!dbManager.modCliente(cli)){
										javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
												"El cliente no existe o ha ocurrido"
												+ "un error en la modificación","Error",
												javax.swing.JOptionPane.PLAIN_MESSAGE);
									}
									else{
										javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
												"Cliente modificado con éxito","Éxito!",
												javax.swing.JOptionPane.PLAIN_MESSAGE);
										reset();
									}
								}catch(Exception e2){
									e2.printStackTrace();
								}
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"Seleccione un id de cliente para modificar","Error",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
							reset();
						}
					}
				});
				
				btnEliminar = new JButton("Eliminar");
				btnEliminar.setEnabled(false);
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldId.getText().length()>0){
							if(javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"seguro que desea eliminar al cliente id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								if(dbManager.eliminarCliente(textFieldId.getText())){
									javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
											"Cliente eliminado","éxito",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
									reset();
								}
								else{
									javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
											"El cliente no existe o ha ocurrido un error","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"Es necesario un Id de Cliente","Error",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
					}
				});
				
				btnBuscar = new JButton("Buscar");
				btnBuscar.setEnabled(false);
				btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if(textFieldId.getText().isEmpty()&&textFieldNif.getText().isEmpty()){
							javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"La búsqueda es por Nif o por Id","Error",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
						else{
							if(textFieldId.getText().isEmpty()){
								Cliente cliente = dbManager.getClientNif(textFieldNif.getText().trim());
								if(cliente.id != -1){
									textFieldF_nac.setText(dateFormat.format(cliente.f_nac));
									textFieldNombre.setText(cliente.nombre);
									textFieldDireccion.setText(cliente.direccion);
									textFieldId.setText(cliente.id.toString());
								}
								else
									javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
											"El cliente no existe","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
							else{
								Cliente cliente = dbManager.getClient(textFieldId.getText().trim());
								if(cliente.id != -1){
									textFieldF_nac.setText(dateFormat.format(cliente.f_nac));
									textFieldNombre.setText(cliente.nombre);
									textFieldDireccion.setText(cliente.direccion);
									textFieldNif.setText(cliente.nif);
								}
								else
									javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
											"El cliente no existe","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
						}
					}
				});
				buttonPane.add(btnBuscar);
				buttonPane.add(btnEliminar);
				
				btnAnadir = new JButton("Añadir");
				btnAnadir.setEnabled(false);
				btnAnadir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int key = 0;
						if(llena() && textFieldId.getText().length()==0){
							try{key = dbManager.addCliente(textFieldNombre.getText(),
									dateFormat.parse(textFieldF_nac.getText()),
									textFieldDireccion.getText(),textFieldNif.getText());
							}catch(Exception e2){}
							if(key==-1){
								javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
										"Error en la inserción","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
										"Cliente introducido con éxito","Éxito!",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
								textFieldId.setText(String.valueOf(key));
								reset();
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"Datos mal introducidos\n"
									+ "\tEl formato debe ser: dd/mm/aaaa\n"
									+ "\tDirección y nombre deben estar rellenados\n"
									+ "\tId debe estar vacío","Error",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
					}
				});
				buttonPane.add(btnAnadir);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Salir");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						padre.reset();
						padre.setEnabled(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	private boolean llena(){
		if(!textFieldF_nac.getText().isEmpty()){
			try{
				fecha = dateFormat.parse(textFieldF_nac.getText());
			}
			catch(Exception e){
//				javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
//						"El formato debe ser: dd/mm/aaaa","Error",
//						javax.swing.JOptionPane.PLAIN_MESSAGE);
				return false;
			}
			if(	textFieldNombre.getText().length()>0 &&
				textFieldDireccion.getText().length()>0 &&
				textFieldNif.getText().length()>0){
				return true;
			}
		}
		return false;
	}
	
	private void rellenarTabla(){
		

		clientes = dbManager.getClientes();
		String[] data = new String[clientes.size()*4];
		
		list = new JList<>(data);
		
		for(int i = 0; i<clientes.size();i++){
			data[i]="id: "+String.valueOf(clientes.get(i).id)+" | ";
			data[i]+="nom: "+clientes.get(i).nombre+" | ";
			data[i]+="nac: "+dateFormat.format(clientes.get(i).f_nac)+" | ";
			data[i]+="dir: "+clientes.get(i).direccion+ "| ";
			data[i]+="nif: "+clientes.get(i).nif;
		}
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sel = list.getSelectedValue();
				if(sel!=null){
					sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
					Cliente cliente = dbManager.getClient(sel.trim());
					textFieldF_nac.setText(dateFormat.format(cliente.f_nac));
					textFieldNombre.setText(cliente.nombre);
					textFieldDireccion.setText(cliente.direccion);
					textFieldId.setText(sel.trim());
					textFieldNif.setText(cliente.nif);
					okButton.setEnabled(true);
					btnEliminar.setEnabled(true);
				}
			}
				
		});
		
		scrollPane.setViewportView(list);

	}
	
	public void reset(){
		rellenarTabla();
		okButton.setEnabled(false);
		btnEliminar.setEnabled(false);
		btnAnadir.setEnabled(false);
		btnBuscar.setEnabled(false);
		textFieldF_nac.setText(dateFormat.format(new Date()));
		textFieldNombre.setText("");
		textFieldDireccion.setText("");
		textFieldId.setText("");
		textFieldNif.setText("");
		
	}
	
}
