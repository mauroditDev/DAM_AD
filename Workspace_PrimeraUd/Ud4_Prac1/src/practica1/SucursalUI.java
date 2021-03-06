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
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
public class SucursalUI extends JDialog {

	private DBmanager dbManager;
	private ArrayList<Sucursal> sucursales;
	
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JTextField textFieldCp;
	private JTextField textFieldDireccion;
	private JTextField textFieldId;
	private JList<String> list;
	private JScrollPane scrollPane;


	/**
	 * Create the dialog.
	 */
	public SucursalUI(DBmanager dbman, final UI padre) {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		padre.setEnabled(false);
		setTitle("Sucursales");
		dbManager = dbman;
		setBounds(100, 100, 450, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textFieldCp = new JTextField();
		textFieldCp.setBounds(12, 53, 159, 19);
		contentPanel.add(textFieldCp);
		textFieldCp.setColumns(10);
		
		JLabel lblNombre = new JLabel("Código Postal");
		lblNombre.setBounds(12, 26, 135, 15);
		contentPanel.add(lblNombre);
		
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
		
		//rellenarTabla();
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(!vacia() && textFieldId.getText().length()>0 ){
							if(javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
								"confirme que quiere modificar a id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								Sucursal suc = new Sucursal(textFieldId.getText(), textFieldCp.getText(),
										textFieldDireccion.getText());
								if(!dbManager.modSucursal(suc)){
									javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
											"La Sucursal no existe o ha ocurrido"
											+ "un error en la modificación","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
								else{
									javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
											"Sucursal modificada con éxito","Éxito!",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
									rellenarTabla();
								}
							}
						}
						else if(vacia() && textFieldId.getText().length()>0){
							Sucursal suc = dbManager.getSucursal(textFieldId.getText());
							
							if(suc.idsucursal!=0){
								textFieldCp.setText(suc.cp);
								textFieldDireccion.setText(suc.direccion);
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
										"La sucursal no existe o ha ocurrido un error","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
									"requerido un Id","advertencia",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
						
					}
				});
				
				JButton btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldId.getText().length()>0){
							if(javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
									"seguro que desea eliminar la Sucursal id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								if(dbManager.eliminarSucursal(textFieldId.getText())){
									javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
											"Sucursal eliminada","éxito",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
									rellenarTabla();
								}
								else{
									javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
											"La Sucursal no existe o ha ocurrido un error","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
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
						
						if(llena() && textFieldId.getText().length()==0){
							Sucursal suc = new Sucursal(0,textFieldCp.getText(),
									textFieldDireccion.getText());
							int key = dbManager.addSucursal(suc);
							if(key == -1){
								javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
										"Error en la inserción","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
										"Sucursal introducido con éxito","Éxito!",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
								textFieldId.setText(String.valueOf(key));
								rellenarTabla();
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(SucursalUI.this,
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
				JButton cancelButton = new JButton("Salir");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						padre.setEnabled(true);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	private boolean vacia(){
		if( textFieldCp.getText().length()==0 &&
			textFieldDireccion.getText().length()==0){
			return true;
		}
		return false;
	}
	private boolean llena(){
		if(
				textFieldCp.getText().length()>0 &&
				textFieldDireccion.getText().length()>0){
			return true;
		}
		return false;
	}
	
	private void rellenarTabla(){
		
		sucursales = dbManager.getSucursales();
		String[] data = new String[sucursales.size()*4];
		
		list = new JList<>(data);
		
		for(int i = 0; i<sucursales.size();i++){
			data[i]="id: "+String.valueOf(sucursales.get(i).idsucursal)+" | ";
			data[i]+="cp: "+sucursales.get(i).cp+" | ";
			data[i]+="dir: "+sucursales.get(i).direccion;
			
		}		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sel = list.getSelectedValue();
				sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
				Sucursal sucursal = dbManager.getSucursal(sel.trim());
				textFieldCp.setText(sucursal.cp);
				textFieldDireccion.setText(sucursal.direccion);
				textFieldId.setText(String.valueOf(sucursal.idsucursal));
			}
		});
		scrollPane.setViewportView(list);

	}
}
