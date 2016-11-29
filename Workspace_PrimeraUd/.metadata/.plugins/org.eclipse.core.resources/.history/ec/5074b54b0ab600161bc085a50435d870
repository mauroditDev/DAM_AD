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

public class ClienteUI extends JDialog {

	private DBmanager dbManager;
	public int estado;
	/*
	 * 0: Alta
	 * 1: Baja
	 * 2: Modificación
	 * 3: Consulta
	 */
	private final JPanel contentPanel = new JPanel();
	private JButton okButton;
	private JTextField textFieldNombre;
	private JTextField textFieldF_nac;
	private JTextField textFieldDireccion;
	private JTextField textFieldId;


	/**
	 * Create the dialog.
	 */
	public ClienteUI(DBmanager dbman, int estado) {
		setTitle("Clientes");
		setBounds(100, 100, 450, 300);
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
		lblDireccion.setBounds(12, 99, 70, 15);
		contentPanel.add(lblDireccion);
		
		textFieldDireccion = new JTextField();
		textFieldDireccion.setColumns(10);
		textFieldDireccion.setBounds(12, 126, 413, 19);
		contentPanel.add(textFieldDireccion);
		
		textFieldId = new JTextField();
		textFieldId.setColumns(10);
		textFieldId.setBounds(131, 208, 159, 19);
		contentPanel.add(textFieldId);
		
		JLabel lblId = new JLabel("Id");
		lblId.setBounds(131, 181, 70, 15);
		contentPanel.add(lblId);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						switch(ClienteUI.this.estado){
						case 0:
							if(textFieldF_nac.getText().matches("[1-2][0-9][0-9][0-9]-[0-1][0-9]-[0-3][0-9]")){
								if(!dbManager.addCliente(textFieldNombre.getText(),textFieldF_nac.getText(),
										textFieldDireccion.getText())){
									javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
											"Error en la inserción","Error",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
								else{
									javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
											"Cliente introducido con éxito","Éxito!",
											javax.swing.JOptionPane.PLAIN_MESSAGE);
								}
							}
							else{
								javax.swing.JOptionPane.showConfirmDialog(ClienteUI.this,
										"Fecha mal introducida, el formato debe ser: yyyy-mm-dd","Error",
										javax.swing.JOptionPane.PLAIN_MESSAGE);
							}
							break;
						case 1:
							
							break;
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		this.estado = estado;
		dbManager = dbman;
		switch(this.estado){
		case 0:
			textFieldId.setEnabled(false);
			break;
		case 1:
			
			break;
		}
	}
}
