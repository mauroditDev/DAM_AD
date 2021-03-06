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
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class CuentaUI extends JDialog {

	private DBmanager dbManager;
	private ArrayList<Cuenta> cuentas;
	
	private final JPanel contentPanel = new JPanel();
	private JButton btnBuscar;
	private JTextField textFieldTitular;
	private JTextField textFieldSaldo;
	private JTextField textFieldId;
	private JList<String> list;
	private JScrollPane scrollPane;
	private JButton btnEliminar;
	private JButton btnAadir;


	/**
	 * Create the dialog.
	 */
	public CuentaUI(DBmanager dbman, final UI padre) {
		padre.setEnabled(false);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
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
		textFieldTitular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldTitular.getText().isEmpty() && !textFieldSaldo.getText().isEmpty()){
						btnAadir.setEnabled(true);
						btnEliminar.setEnabled(false);
						btnBuscar.setEnabled(false);
					}
					else{
						btnEliminar.setEnabled(false);
						btnAadir.setEnabled(false);
						btnBuscar.setEnabled(false);
					}
				}
				else{
					btnAadir.setEnabled(false);
					btnEliminar.setEnabled(true);
					btnBuscar.setEnabled(true);
				}
			}
		});
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
		textFieldSaldo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldTitular.getText().isEmpty() && !textFieldSaldo.getText().isEmpty()){
						btnAadir.setEnabled(true);
						btnEliminar.setEnabled(false);
						btnBuscar.setEnabled(false);
					}
					else{
						btnEliminar.setEnabled(false);
						btnAadir.setEnabled(false);
						btnBuscar.setEnabled(false);
					}
				}
				else{
					btnAadir.setEnabled(false);
					btnEliminar.setEnabled(true);
					btnBuscar.setEnabled(true);
				}
			}
		});
		
		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(22, 113, 159, 19);
		contentPanel.add(textFieldId);
		textFieldId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(textFieldId.getText().isEmpty()){
					if(!textFieldTitular.getText().isEmpty() && !textFieldSaldo.getText().isEmpty()){
						btnAadir.setEnabled(true);
						btnEliminar.setEnabled(false);
						btnBuscar.setEnabled(false);
					}
					else{
						btnEliminar.setEnabled(false);
						btnAadir.setEnabled(false);
						btnBuscar.setEnabled(false);
					}
				}
				else{
					btnAadir.setEnabled(false);
					btnEliminar.setEnabled(true);
					btnBuscar.setEnabled(true);
				}
			}
		});
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(22, 86, 70, 15);
		contentPanel.add(lblId);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 144, 424, 286);
		contentPanel.add(scrollPane);
		list = new JList<String>();
		scrollPane.setViewportView(list);
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				btnBuscar = new JButton("Buscar");
				btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(textFieldId.getText().matches("[0-9]+")){
							Cuenta cue = dbManager.getCuenta(textFieldId.getText());
							if(cue.id != -1){
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
										"La Cuenta no existe o ha ocurrido un error","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
						}
						else{
							javax.swing.JOptionPane.showConfirmDialog(CuentaUI.this,
									"Requerido un Id\n"
									+ "El Id escrito no es válido","advertencia",
									javax.swing.JOptionPane.PLAIN_MESSAGE);
						}
						
					}
				});
				
				btnEliminar = new JButton("Eliminar");
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
									reset();
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
				
				btnAadir = new JButton("Añadir");
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
								reset();
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
				btnBuscar.setActionCommand("OK");
				buttonPane.add(btnBuscar);
				getRootPane().setDefaultButton(btnBuscar);
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
		reset();
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
				if(sel!=null){
					sel = sel.substring(sel.indexOf(" "),sel.indexOf(" |"));
					Cuenta cuenta = dbManager.getCuenta(sel.trim());
					String tits = "";
					for(Integer titular: cuenta.titulares){
						tits += String.valueOf(titular) + ", ";
					}
					textFieldTitular.setText(tits);
					textFieldSaldo.setText(cuenta.saldo.toString());
					textFieldId.setText(sel.trim());
					btnEliminar.setEnabled(true);
				}
			}
				
		});
		
		
		scrollPane.setViewportView(list);

	}
	
	public void reset(){
		btnAadir.setEnabled(false);
		btnEliminar.setEnabled(false);
		btnBuscar.setEnabled(false);
		textFieldTitular.setText("");
		textFieldSaldo.setText("");
		textFieldId.setText("");
		rellenarTabla();
	}
	
	
}
