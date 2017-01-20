package ejercicio1;

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
		ProfesorAnotaciones profesor2=new ProfesorAnotaciones(102, "Pepe", "Sanchez", "Perez");
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		 
		session.save(profesor);
		session.save(profesor2);
		
		session.getTransaction().commit();
		
		Profesor prof3 = (Profesor)session.get(Profesor.class, 102);
		ProfesorAnotaciones prof4 = (ProfesorAnotaciones)session.get(ProfesorAnotaciones.class, 101);
		
		System.out.println(prof3.getApe1());
		System.out.println(prof4.getApe1());
		
		
		session.close();
	}

}
