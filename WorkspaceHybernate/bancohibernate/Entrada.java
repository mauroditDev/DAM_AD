package bancohibernate;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class Entrada {
	static String inicializar() {
		String buzon = "";
		InputStreamReader flujo = new InputStreamReader(System.in);
		BufferedReader teclado = new BufferedReader(flujo);
		try {
			buzon = teclado.readLine();
		} catch (Exception e) {
			System.out.append("Entrada incorrecta)");
		}
		return buzon;
	}

	public static Integer entero() {
		int valor = Integer.parseInt(inicializar());
		return valor;
	}

	public static double real() {
		double valor = Double.parseDouble(inicializar());
		return valor;
	}
	
	public static double realVal(String mensajeSolicitud,String mensajeError,String plantilla,boolean excepcionPorEnter) {
		String dato;
		
		boolean bien;
		do {
			bien=true;
			if (mensajeSolicitud!=null)
				System.out.print(mensajeSolicitud);
			dato=cadena();//Leemos String
			if (excepcionPorEnter && dato.length()==0)
				throw new RuntimeException("Se ha pulsado ENTER");
			bien=realEsValido(dato,plantilla);
			if (!bien)
				mostrarError(mensajeError);			
		} while (!bien);
		//Devolvemos el entero validado
		return Double.valueOf(dato);
	}
	
	public static double realVal(String mensajeSolicitud,String mensajeError,String plantilla) {
		return realVal(mensajeSolicitud,mensajeError,plantilla,false);
	}
	
	public static boolean realEsValido(String dato, String plantilla) {
		if (plantilla!=null)
			if (!dato.matches(plantilla))
				return false;
		try {
			double valor=Double.valueOf(dato);//Hacemos una conversion para ver si se lanza excepcion
		} catch (NumberFormatException e) { 
			return false;
		}
		return true;
	}
	
	public static double realVal(String mensajeSolicitud,String mensajeError,double min,double max,boolean excepcionPorEnter) {
		String dato;
		
		boolean bien;
		do {
			bien=true;
			if (mensajeSolicitud!=null)
				System.out.print(mensajeSolicitud);
			dato=cadena();//Leemos String
			if (excepcionPorEnter && dato.length()==0)
				throw new RuntimeException("Se ha pulsado ENTER");
			bien=realEsValido(dato,min, max);
			if (!bien)
				mostrarError(mensajeError);			
		} while (!bien);
		//Devolvemos el entero validado
		return Double.valueOf(dato);
	}
	
	public static double realVal(String mensajeSolicitud,String mensajeError,double min,double max) {
		return realVal(mensajeSolicitud,mensajeError,min,max,false);
	}

	public static boolean realEsValido(String dato, double min, double max) {
		double valor;
		try {
			valor=Double.valueOf(dato); //Hacemos una conversion para ver si se lanza excepcion
		} catch (NumberFormatException e) { 
			return false;
		}
		return valor>=min && valor<=max;
	}
	
	

	public static String cadena() {
		String valor = inicializar();
		return valor;
	}

	public static String cadenaVal(String mensajeSolicitud,String mensajeError,String plantilla,boolean excepcionPorEnter){
			String dato;
			boolean bien;
			do {
				bien=true;
				if (mensajeSolicitud!=null)
					System.out.print(mensajeSolicitud);
				dato=cadena();//Leemos String
				if (excepcionPorEnter && dato.length()==0)
					throw new RuntimeException("Se ha pulsado ENTER");
				if (plantilla!=null)
					bien=dato.matches(plantilla);
				if (!bien)
					mostrarError(mensajeError);			
			} while (!bien);
			return dato;
	}
	
	public static String cadenaVal(String mensajeSolicitud,String mensajeError,String plantilla){
		return cadenaVal(mensajeSolicitud,mensajeError,plantilla,false);
	}
	
	public static char caracter() {
		String valor = inicializar();
		return valor.charAt(0);
	}
	
	public static char caracterVal(String mensajeSolicitud,String mensajeError,String plantilla,boolean excepcionPorEnter){
		String dato;
		boolean bien;
		do {
			bien=true;
			if (mensajeSolicitud!=null)
				System.out.print(mensajeSolicitud);
			dato=cadena();//Leemos String
			if (excepcionPorEnter && dato.length()==0)
				throw new RuntimeException("Se ha pulsado ENTER");
			bien=dato.length()==1;
			if (plantilla!=null)
				bien&=dato.matches(plantilla);
			if (!bien)
				mostrarError(mensajeError);			
		} while (!bien);
		return dato.charAt(0);
	}
	
	public static char caracterVal(String mensajeSolicitud,String mensajeError,String plantilla){
		return caracterVal(mensajeSolicitud,mensajeError,plantilla,false);
	}
	
	public static GregorianCalendar fecha(String plantilla) throws ParseException {
		String t=cadena();
		SimpleDateFormat sdf=new SimpleDateFormat(plantilla);
		sdf.setLenient(false);
		Date d=sdf.parse(t);
		GregorianCalendar gc=new GregorianCalendar();
		
		gc.setTime(d);
		return gc;
	}
	
	public static GregorianCalendar fechaVal(String mensajeSolicitud,String mensajeError,String plantilla,boolean excepcionPorEnter){
		String dato;
		GregorianCalendar gc=null;
		boolean bien;
		do {
			bien=true;
			if (mensajeSolicitud!=null)
				System.out.print(mensajeSolicitud);
			dato=cadena();//Leemos String
			if (excepcionPorEnter && dato.length()==0)
				throw new RuntimeException("Se ha pulsado ENTER");
			gc=fechaEsValida(dato,plantilla); //Obtiene un objeto GregorianCalendar(), si dato no valido null
			bien=gc!=null;
			if (!bien)
				mostrarError(mensajeError);			
		} while (!bien);
	
		return gc;
	}
	
	public static GregorianCalendar fechaVal(String mensajeSolicitud,String mensajeError,String plantilla){
		return fechaVal(mensajeSolicitud,mensajeError,plantilla,false);
	}
	
	public static GregorianCalendar fechaEsValida(String dato, String plantilla) {
		//Devuelve null si dato no se es conforme a la plantilla
		if (plantilla==null)
			return null;
		SimpleDateFormat sdf=new SimpleDateFormat(plantilla);
		sdf.setLenient(false);
		Date d;
		try {
			d = sdf.parse(dato);
		} catch (ParseException e) {
			return null;
		}
		GregorianCalendar gc=new GregorianCalendar();
		gc.setTime(d);
		return gc;
	}

	public static int enteroVal(String mensajeSolicitud,String mensajeError,String plantilla,boolean excepcionPorEnter) {
		String dato;
		
		boolean bien;
		do {
			bien=true;
			if (mensajeSolicitud!=null)
				System.out.print(mensajeSolicitud);
			dato=cadena();//Leemos String
			if (excepcionPorEnter && dato.length()==0)
				throw new RuntimeException("Se ha pulsado ENTER");
			bien=enteroEsValido(dato,plantilla);
			if (!bien)
				mostrarError(mensajeError);			
		} while (!bien);
		//Devolvemos el entero validado
		return Integer.valueOf(dato);
	}
	
	public static int enteroVal(String mensajeSolicitud,String mensajeError,String plantilla) {
		return enteroVal(mensajeSolicitud,mensajeError,plantilla,false);
	}
	
	private static boolean enteroEsValido(String dato, String plantilla) {
		if (plantilla!=null)
			if (!dato.matches(plantilla))
				return false;
		try {
			int valor=Integer.valueOf(dato); //Hacemos una conversion para ver si se lanza excepcion
		} catch (NumberFormatException e) { 
			return false;
		}
		return true;
	}

	public static int enteroVal(String mensajeSolicitud,String mensajeError,int min,int max,boolean excepcionPorEnter) {
		String dato;
		
		boolean bien;
		do {
			bien=true;
			if (mensajeSolicitud!=null)
				System.out.print(mensajeSolicitud);
			dato=cadena();//Leemos String
			if (excepcionPorEnter && dato.length()==0)
				throw new RuntimeException("Se ha pulsado ENTER");
			bien=enteroEsValido(dato,min, max);
			if (!bien)
				mostrarError(mensajeError);			
		} while (!bien);
		//Devolvemos el entero validado
		return Integer.valueOf(dato);
	}
	
	public static int enteroVal(String mensajeSolicitud,String mensajeError,int min,int max) {
		return enteroVal(mensajeSolicitud,mensajeError,min,max,false);
	}

	public static boolean enteroEsValido(String dato, int min, int max) {
		int valor;
		try {
			valor=Integer.valueOf(dato); //Hacemos una conversion para ver si se lanza excepcion
		} catch (NumberFormatException e) { 
			return false;
		}
		return valor>=min && valor<=max;
	}

	public static void mostrarError(String mensajeError) {
		if (mensajeError!=null)
			System.out.println(mensajeError);
		
	}
	
}