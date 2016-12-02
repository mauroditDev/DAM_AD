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

public class CuentaUI extends JDialog {

	private DBmanager dbManager;
	private ArrayList<Cuenta> cuentas;
	
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JTextField textFieldTitular;
	private JTextField textFieldSaldo;
	private JTextField textFieldId;
	private JList<String> list;
	private JScrollPane scrollPane;


	/**
	 * Create the dialog.
	 */
	public CuentaUI(DBmanager dbman) {
		setTitle("Cuentas");
		dbManager = dbman;
		setBounds(100, 100, 450, 503);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		textFieldTitular = new JTextField();
		textFieldTitular.setBounds(12, 53, 159, 19);
		contentPanel.add(textFieldTitular);
		textFieldTitular.setColumns(10);
		
		JLabel lblNombre = new JLabel("Titulares");
		lblNombre.setBounds(12, 26, 70, 15);
		contentPanel.add(lblNombre);
		
		JLabel lblFechaNacimiento = new JLabel("Saldo");
		lblFechaNacimiento.setBounds(208, 26, 159, 15);
		contentPanel.add(lblFechaNacimiento);
		
		textFieldSaldo = new JTextField();
		textFieldSaldo.setColumns(10);
		textFieldSaldo.setBounds(208, 53, 159, 19);
		contentPanel.add(textFieldSaldo);
		
		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(22, 113, 159, 19);
		contentPanel.add(textFieldId);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(22, 86, 70, 15);
		contentPanel.add(lblId);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 144, 424, 286);
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
							if(javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
								"confirme que quiere modificar a id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								Cuenta cue = new Cuenta(textFieldTitular.getText(),textFieldId.getText(),
										textFieldSaldo.getText());
								if(!dbManager.modCuenta(cue)){
									javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
											"El Cuenta no existe o ha ocurrido"
											+ "un error en la modificación","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
								else{
									javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
											"Cuenta modificado con éxito","Éxito!",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
									rellenarTabla();
								}
							}
						}
						else if(vacia() && textFieldId.getText().length()>0){
							Cuenta cue = dbManager.getCuenta(textFieldId.getText());
							if(cue.id != -99){
								textFieldSaldo.setText(String.valueOf(cue.saldo));
								String texto = "";
								for(Integer titular: cue.titulares){
									texto += String.valueOf(titular);
									texto += ", ";
								}
								texto = texto.substring(0, texto.length()-2);
								textFieldTitular.setText(texto);
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
										"El Cuenta no existe o ha ocurrido un error","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
									"requerido un Id","advertencia",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
						
					}
				});
				
				JButton btnEliminar = new JButton("Eliminar");
				btnEliminar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldId.getText().length()>0){
							if(javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
									"seguro que desea eliminar al Cuenta id:"+textFieldId.getText(),"advertencia",
									javax.swing.JOptionPane.YES_OPTION)==0){
								if(dbManager.eliminarCuenta(textFieldId.getText())){
									javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
											"Cuenta eliminado","éxito",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
									rellenarTabla();
								}
								else{
									javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
											"El Cuenta no existe o ha ocurrido un error","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
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
							Cuenta cuenta = new Cuenta(textFieldTitular.getText(),0,
									Integer.valueOf(textFieldSaldo.getText()));
							key = dbManager.addCuenta(cuenta);
							if(key==-1){
								javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
										"Error en la inserción","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
										"Cuenta introducido con éxito","Éxito!",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
								textFieldId.setText(String.valueOf(key));
								rellenarTabla();
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
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
		if(textFieldSaldo.getText().length()==0 &&
			textFieldTitular.getText().length()==0){
			return true;
		}
		return false;
	}
	private boolean llena(){
		if(textFieldSaldo.getText().length()>0 &&
				textFieldTitular.getText().length()>0){
			return true;
		}
		return false;
	}
	
	private void rellenarTabla(){
		

		cuentas = dbManager.getCuentas();
		String[] data = new String[cuentas.size()*4];
		
		list = new JList<>(data);
		
		for(int i = 0; i<cuentas.size();i++){
			data[i]="id: "+String.valueOf(cuentas.get(i).id)+" | ";
			data[i]+="titulares (" + cuentas.get(i).titulares.size()+"):  ";
			for(Integer titular: cuentas.get(i).titulares){
				data [i] += String.valueOf(titular) + ", ";
			}
			data[i] = data[i].substring(0,data[i].length()-2);
			data[i]+=" | ";
			data[i]+="saldo: "+String.valueOf(cuentas.get(i).saldo);
			
		}
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String sel = list.getSelectedValue();
				sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
				Cuenta cuenta = dbManager.getCuenta(sel.trim());
				String tits = "";
				for(Integer titular: cuenta.titulares){
					tits += String.valueOf(titular) + ", ";
				}
				textFieldTitular.setText(tits);
				textFieldSaldo.setText(cuenta.saldo.toString());
				textFieldId.setText(sel.trim());
				
			}
				
		});
		
		
		scrollPane.setViewportView(list);

	}
}