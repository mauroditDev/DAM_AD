package Fechas.manejo;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.TimeZone;


public class PruebeClasesFechaHora {

	
	public static void main(String[] args) {

		Date d=new Date(); //Toma fecha y hora del sistema
		System.out.println("Reloj del PC (ms):"+d.getTime());
				SimpleDateFormat sdf=new SimpleDateFormat("dd-MMMM-yyy HH:mm:ss,SSS");
		System.out.println(sdf.format(d));
		
		
		
		//Lee fecha desde el teclado
		Date d2=null;
		Scanner s=new Scanner(System.in);
		SimpleDateFormat fmtLeer=new SimpleDateFormat("dd-MM-yyyy");
		fmtLeer.setLenient(false); //Validacion estricta de fecha
		System.out.print("Introduzca fecha (DD-MM-AAAA): ");
		String fecha=s.nextLine();
		try {
			d2=fmtLeer.parse(fecha);
			System.out.println("Milisegindos desde 1-1-1970:"+d2.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		System.out.println("===========================================");
		//Zonas horarias
		System.out.println("=== Locale personalizado ===");
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",Locale.FRENCH);
		System.out.println(Locale.FRENCH.getDisplayName()+" --> "+sdf.format(d));
		
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",Locale.ITALIAN);
		System.out.println(Locale.ITALIAN.getDisplayName()+" --> "+sdf.format(d));
		
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",Locale.GERMAN);		
		System.out.println(Locale.GERMAN.getDisplayName()+" --> "+sdf.format(d));
		
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",Locale.ENGLISH);
		System.out.println(Locale.ENGLISH.getDisplayName()+" --> "+sdf.format(d));
		
		Locale esp=new Locale("es", "ES");
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",esp);
		System.out.println(esp.getDisplayName()+" --> "+sdf.format(d));
		
		Locale portu=new Locale("pt", "PT");
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",portu);
		System.out.println(portu.getDisplayName()+" --> "+sdf.format(d));
		
		Locale islandia=new Locale("is", "IS");
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",islandia);
		System.out.println(islandia.getDisplayName()+" --> "+sdf.format(d));
		
		Locale lituania=new Locale("lt", "LT");
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z",lituania);
		System.out.println(lituania.getDisplayName()+" --> "+sdf.format(d));
		//Locale del S.O.
		System.out.println("=== Locale por defecto ===");
		sdf=new SimpleDateFormat("EEEE, dd-MMMM-yyy HH:mm:ss z");
		System.out.println(sdf.format(d));
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		System.out.println(sdf.format(d));
		sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		System.out.println(sdf.format(d));
		sdf.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"));
		System.out.println(sdf.getTimeZone().getID());
		System.out.println(TimeZone.getDefault().getID());
		System.out.println(sdf.getTimeZone().getDisplayName());
		System.out.println(sdf.format(d));
	}

}
