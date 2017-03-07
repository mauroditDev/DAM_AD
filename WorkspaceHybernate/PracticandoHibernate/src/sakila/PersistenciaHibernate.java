package sakila;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

// Modificaciones a los archivos de mapeo generados por JBoss Tools:
// hibernate.cfg.xml: Quitamos el atributo name del elemento session-factory--> <session-factory>
// Cliente.hbm.xml: Ponemos el atributo inverse=true en uno de los lados de la relaci�n many_to_many --> <set name="cuentas" table="titular" inverse="true" lazy="true" fetch="select">
// Cuenta.hbm.xml: Ponemos el atributo cascade="all" en el lado de la relacion one_to_many --> <set name="movimientos" table="movimiento" cascade="all" inverse="true" lazy="true" fetch="select">
public class PersistenciaHibernate {
	Session sesion;
	
    public PersistenciaHibernate(String fichCfg, boolean mostrarSQL) {
		SessionFactory sessionFactory;
	    Configuration configuration = new Configuration();
	    configuration.configure(fichCfg);
	    if (mostrarSQL)
	    	configuration.setProperty("hibernate.show_sql", "true");
	    ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	    sesion=sessionFactory.openSession();
	}

	public Country buscarCountry(String name) {
		Query q=sesion.createQuery("SELECT c FROM Country c WHERE country=:name");
		q.setString("name", name);
		Country cou = (Country)q.uniqueResult();
		return cou;
	}

	public List<City> buscarCiudades(Country country){
		Query q=sesion.createQuery("SELECT c FROM City c WHERE country_id=:country");
		q.setInteger("country", country.getCountryId());
		List<City> res = q.list();
		return res;
	}
	
	/*
	public void borrarCliente(Cliente c){
		sesion.beginTransaction();
		sesion.delete(c);
		sesion.getTransaction().commit();
	}
	public int guardarCuenta(Cuenta c){
		sesion.beginTransaction();
		sesion.saveOrUpdate(c);
		sesion.getTransaction().commit();
		return c.getId();
	}
	
	public Session getSesion() {
		return sesion;
	}

	public Cuenta buscarCuenta(int id) {
		return (Cuenta)sesion.get(Cuenta.class,id);
	}

	public void borrarCuenta(Cuenta c) {
		sesion.beginTransaction();
		sesion.delete(c);
		sesion.getTransaction().commit();
	}

	public boolean aniadeTitularCuenta(Cliente cl, Cuenta cu) {
		if (cl.getCuentas().contains(cu))
			return false;
		cl.getCuentas().add(cu);
		cu.getClientes().add(cl);
		sesion.beginTransaction();
		sesion.saveOrUpdate(cl);
		sesion.getTransaction().commit();
		return true;
	}

	public boolean quitaTitularCuenta(Cliente cl, Cuenta cu) {
		if (!cl.getCuentas().contains(cu))
			return false;
		cl.getCuentas().remove(cu);
		cu.getClientes().remove(cl);
		sesion.beginTransaction();
		sesion.saveOrUpdate(cl);
		sesion.getTransaction().commit();
		return true;
	}

	public void registrarMovimiento(Cuenta c, int importe) {
		sesion.beginTransaction();
		//Creamos movimiento
		Movimiento m=new Movimiento(c, new Date(), importe);
		//sesion.saveOrUpdate(m); // NO ES NECESARIO YA QUE HEMOS PUESTO cascade="all" en el mapeo Cuenta.hbm.xml
		//A�adimos movimiento a cuenta
		c.getMovimientos().add(m);
		//Actualizamos saldo
		c.setSaldo(c.getSaldo()+importe);
		sesion.saveOrUpdate(c);
		sesion.getTransaction().commit();
		
	}

	public List<Movimiento> extraerMovimientos(Cuenta c, Date desde, Date hasta) {
		Query query = sesion.createQuery("SELECT m FROM Movimiento m WHERE cuenta.id=:cuenta AND FH BETWEEN :desde AND :hasta ORDER BY FH");
		query.setParameter("cuenta", c.getId());
		query.setParameter("desde", desde);
		query.setParameter("hasta", hasta);
		List<Movimiento> movs = query.list();
		return movs;
	}
	*/
}
