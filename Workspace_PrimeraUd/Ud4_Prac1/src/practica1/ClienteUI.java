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
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;

public class ClienteUI extends JDialog {

	private DBmanager dbManager;
	private ArrayList<Cliente> clientes;
	
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JTextField textFieldNombre;
	private JTextField textFieldF_nac;
	private JTextField textFieldDireccion;
	private JTextField textFieldId;
	private JList<String> list;
	private JScrollPane scrollPane;


	/**
	 * Create the dialog.
	 */
	public ClienteUI(DBmanager dbman) {
		setTitle("Clientes");
		dbManager = dbman;
		setBounds(100, 100, 450, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textFieldNombre = new JTextField();
		textFieldNombre.setBounds(12, 53, 159, 19);
		contentPanel.add(textFieldNombre);
		textFieldNombre.setColumns(10);
		
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
		
		JLabel lblDireccion = new JLabel("Dirección");
		lblDireccion.setBounds(12, 84, 70, 15);
		contentPanel.add(lblDireccion);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setColumns(10);
		textFieldDireccion.setBounds(12, 111, 413, 19);
		contentPanel.add(textFieldDireccion);
		
		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(12, 169, 159, 19);
		contentPanel.add(textFieldId);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(12, 142, 70, 15);
		contentPanel.add(lblId);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 200, 424, 230);
		contentPanel.add(scrollPane);
		list = new JList<String>();
		scrollPane.setViewportView(list);
		
		rellenarTabla();
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!vacia() && textFieldId.getText().length()>0 ){
							if(javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
								"confirme que quiere modificar a id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								Cliente cli = new Cliente(textFieldId.getText(), textFieldNombre.getText(),
										textFieldF_nac.getText(), textFieldDireccion.getText());
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
									rellenarTabla();
								}
							}
						}
						else if(vacia() && textFieldId.getText().length()>0){
							Cliente cli = new Cliente();
							if(dbManager.getClient(textFieldId.getText(),cli)){
								textFieldNombre.setText(cli.nombre);
								textFieldF_nac.setText(cli.f_nac);
								textFieldDireccion.setText(cli.direccion);
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
										"El cliente no existe o ha ocurrido un error","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"requerido un Id","advertencia",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
						
					}
				});
				
				JButton btnEliminar = new JButton("Eliminar");
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
									rellenarTabla();
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
									"Datos mal introducidos\n"
									+ "\tFecha, Dirección y nombre deben estar vacíos\n"
									+ "\tId debe estar rellenado","Error",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
					}
				});
				buttonPane.add(btnEliminar);
				
				JButton btnAadir = new JButton("Añadir");
				btnAadir.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int key = 0;
						if(llena() && textFieldId.getText().length()==0){
							key = dbManager.addCliente(textFieldNombre.getText(),textFieldF_nac.getText(),
									textFieldDireccion.getText());
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
								rellenarTabla();
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
									"Datos mal introducidos\n"
									+ "\tEl formato debe ser: yyyy-mm-dd\n"
									+ "\tDirección y nombre deben estar rellenados\n"
									+ "\tId debe estar vacío","Error",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
					}
				});
				buttonPane.add(btnAadir);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	private boolean vacia(){
		if(textFieldF_nac.getText().length()==0 &&
			textFieldNombre.getText().length()==0 &&
			textFieldDireccion.getText().length()==0){
			return true;
		}
		return false;
	}
	private boolean llena(){
		if(textFieldF_nac.getText().matches("[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]")&&
				textFieldNombre.getText().length()>0 &&
				textFieldDireccion.getText().length()>0){
			return true;
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
			data[i]+="nac: "+clientes.get(i).f_nac+" | ";
			data[i]+="dir: "+clientes.get(i).direccion;
			
		}
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sel = list.getSelectedValue();
				sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
				Cliente cliente = new Cliente();
				dbManager.getClient(sel.trim(), cliente);
				textFieldF_nac.setText(cliente.f_nac);
				textFieldNombre.setText(cliente.nombre);
				textFieldDireccion.setText(cliente.direccion);
				textFieldId.setText(sel.trim());
				
			}
				
		});
		
		scrollPane.setViewportView(list);

	}
}