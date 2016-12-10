package practica1;

import java.awt.EventQueue;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;

@SuppressWarnings("serial")
public class ExtractoUI extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ExtractoUI frame = new ExtractoUI(new ArrayList<Movimiento>());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public ExtractoUI(ArrayList<Movimiento> extracto) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 549, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 12, 523, 250);
		contentPane.add(scrollPane);
		
		String[] data = new String[extracto.size()];
		
		JList<String> list = new JList<>(data);
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		
		for(int i = 0; i<extracto.size();i++){
			data[i]="id: "+String.valueOf(extracto.get(i).id)+" | ";
			data[i]+="cuenta: "+extracto.get(i).cuenta.id+" | ";
			data[i]+="fecha y hora: "+dateFormat.format(new Date(extracto.get(i).f_h))+" | ";
			data[i]+="importe: "+extracto.get(i).importe;
		}
		scrollPane.setViewportView(list);
	}

}
