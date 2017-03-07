package banco;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.ejb.criteria.expression.SearchedCaseExpression;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Banco {
	SessionFactory sessionFactory;
	Configuration configuration;
	ServiceRegistry serviceRegistry;
	Transaction esta;
	Session session;
	
	public Banco() {
		configuration = new Configuration();
		configuration.configure();
		serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		session = sessionFactory.openSession();
		esta = session.beginTransaction();
//		
//		session.close();
//		sessionFactory.close();
	}

	public boolean reiniciarTablas() {
		// TODO Auto-generated method stub
		return false;
	}

	public ArrayList<Cuenta> getCuentas() {
		Query query = session.createQuery("SELECT c FROM Cuenta c");
		List<Cuenta> cuentas = query.list();
		ArrayList<Cuenta> res = new ArrayList<>();
		Iterator<Cuenta> iter = cuentas.iterator();
		while(true){
			try{
				res.add(iter.next());
			}catch(NoSuchElementException exc){
				break;
			}
		}
		return res;
	}

	public Cuenta getCuenta(String idCuenta) {
		Cuenta res = (Cuenta) session.load(Cuenta.class, Integer.valueOf(idCuenta));
		return res;
	}

	public ArrayList<Cliente> getClientes() {
		Query query = session.createQuery("SELECT c FROM Cliente c");
		List<Cliente> clis = query.list();
		ArrayList<Cliente> res = new ArrayList<>();
		Iterator<Cliente> iter = clis.iterator();
		while(true){
			try{
				res.add(iter.next());
			}catch(NoSuchElementException exc){
				break;
			}
		}
		return res;
	}

	public Cliente getClient(String idCliente) {
		Cliente res = (Cliente) session.load(Cliente.class, Integer.valueOf(idCliente));
		return res;
	}
	
	public boolean guardarCuenta(Cuenta cuenta){
		//Transaction esta = session.beginTransaction();
		session.save(cuenta);
		//esta.commit();
		if(cuenta.getId()<=0)
			return false;
		return true;
	}
	
	public boolean guardarCliente(Cliente cliente){
		session.save(cliente);
		if(cliente.getId()<=0)
			return false;
		return true;
	}

	public boolean persistir(){
		try{
			esta.commit();
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		esta = session.beginTransaction();
		return true;
		
	}
	
	public boolean eliminarCuenta(String text) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
