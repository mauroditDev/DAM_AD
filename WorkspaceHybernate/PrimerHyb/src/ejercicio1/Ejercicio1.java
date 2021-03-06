package ejercicio1;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
public class Ejercicio1 {

	public static void main(String[] args) {
		SessionFactory sessionFactory;
		Configuration configuration = new Configuration();
		configuration.configure();
		ServiceRegistry serviceRegistry = 
				new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
				
		Profesor profesor=new Profesor(101, "Juan", "Perez", "García");
		Set<Modulo> hs = new HashSet<Modulo>();
		Modulo m1 = new Modulo(666,"patata");
		Modulo m2 = new Modulo(777,"tomate");
		Modulo m3 = new Modulo(888,"coche");
		Modulo m4 = new Modulo(999,"casa");
		Modulo m5 = new Modulo(555,"perro");
		Modulo m6 = new Modulo(333,"uiuiui");
		hs.add(m1) ;hs.add(m2);hs.add(m3);hs.add(m4 );
		Set<Modulo> hs2 = new HashSet<Modulo>();
		hs2.add( m1);hs2.add( m5);hs2.add( m6);
		Profesor profesor2=new Profesor(202, "Maria", "Lopez", "Asda");

		profesor.setModulos(hs); profesor2.setModulos(hs2);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		System.out.println(profesor.getModulos());
		
		session.save(profesor);
		session.save(profesor2);
		
		session.getTransaction().commit();
				
		//Seguro seguro2 = (Seguro) session.get(Seguro.class, 100);
		

		//System.out.println(seguro2.getNif());
		
		session.close();
		sessionFactory.close();
	}

}
