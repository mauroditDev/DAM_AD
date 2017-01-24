package ejercicio1;

import java.util.ArrayList;
import java.util.Date;

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
		
				
		Profesor profesor=new Profesor(101, "Juan", "Perez", "García", null);
//		Seguro seguro1 = new Seguro(100, "nif123", "nombre test", "Sanchez", "Perez", 22, 5, new Date());
		
		ArrayList<Correo> correos = new ArrayList<>();
		correos.add(new Correo(111,"asdf",profesor));
		correos.add(new Correo(222,"patata",profesor));
		correos.add(new Correo(333,"wiiiiiiiiii",profesor));
		profesor.setCorreos(correos);
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		 
		session.save(profesor);
//		session.save(profesor2);
//		session.save(seguro1);
		
		session.getTransaction().commit();
		
		Profesor prof3 = (Profesor)session.get(Profesor.class, 102);
		ProfesorAnotaciones prof4 = (ProfesorAnotaciones)session.get(ProfesorAnotaciones.class, 101);
		Seguro seguro2 = (Seguro) session.get(Seguro.class, 100);
		
		System.out.println(prof3.getApe1());
		System.out.println(prof4.getApe2());
		System.out.println(seguro2.getNif());
		
		session.close();
		sessionFactory.close();
	}

}