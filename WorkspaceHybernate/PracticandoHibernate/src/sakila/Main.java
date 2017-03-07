package sakila;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.println("dame el nombre de un país:");
		Scanner sc = new Scanner(System.in);
		String pais = sc.nextLine();
		PersistenciaHibernate ph = new PersistenciaHibernate("hibernate.cfg.xml", true);
		Country country = ph.buscarCountry(pais);
		for(City city:ph.buscarCiudades(country)){
			System.out.println(city.getCity());
		}

	}

}
