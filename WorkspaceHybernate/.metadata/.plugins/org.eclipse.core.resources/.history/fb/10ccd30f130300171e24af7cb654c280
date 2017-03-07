package bancohibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;

public class Principal {
	static PersistenciaHibernate hib=null;
	
	public static void main(String[] args) {
		try {
			//Cargamos configuración de Hibernate con la opción de poder mostrar SQL en consola
			hib=new PersistenciaHibernate("bancohibernate/hibernate.cfg.xml",false);
			
			int opcion;
			do{
				mostrarMenu();
				opcion=Entrada.entero();  
				switch(opcion)
				{
				case 1:
					altaModificacionCliente();
					break;
				case 2:
					bajaCliente();
					break;
				case 3:
					listadoClientes();
					break;
				case 4:
					altaCuenta();
					break;
				case 5:
					bajaCuenta();
					break;
				case 6:
					listadoCuentas();
					break;
				case 7:
					aniadeTitularCuenta();
					break;
				case 8:
					quitaTitularCuenta();
					break;
				case 9:
					realizarMovimientos();
					break;
				case 10:
					extractoMovimientos();
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion incorrecta");
				}
			}while(opcion!=0);
			System.out.println("Programa finalizado.");
		} catch (Exception e) {
			String errorApl=e.getMessage();
			String causa="";
			if (e.getCause()!=null)
				causa=e.getCause().getMessage();
			//Se muestra un mensaje a dos niveles (aplicacion y causa)
			JOptionPane.showMessageDialog(null, errorApl+"\n"+causa);
			e.printStackTrace();
		}
		
	}

	private static void extractoMovimientos() throws Exception {
		int id=Entrada.enteroVal("Id de cuenta?","Id de cuenta debe ser positivo!!",1,999999);
		try {
			Cuenta c=hib.buscarCuenta(id);
			if (c==null){
				System.out.println("Esta cuenta NO EXISTE !!");
				return;
			}
			System.out.println(c);
			
			Date desde=null;
			try {
				desde = Entrada.fechaVal("Fecha Desde (DD-MM-AAAA)?","Fecha incorrecta!!","dd-MM-yyyy",true).getTime();
			} catch (Exception e) {
				desde=new Date();
				desde.setTime(0); //Si se pulsa Enter se toma 1-1-1970
			}
			Date hasta=null;
			try {
				hasta = Entrada.fechaVal("Fecha Hasta (DD-MM-AAAA)?","Fecha incorrecta!!","dd-MM-yyyy",true).getTime();
			} catch (Exception e) {
				hasta=new Date(); //Si se pulsa Enter se toma fecha actual
			}
			if (desde.getTime() > hasta.getTime()){
				System.out.println("La fecha hasta no puede ser anterior a la fecha desde!!");
				return;
			}
			//Obtiene extracto			
			List<Movimiento> movs = hib.extraerMovimientos(c,desde,hasta);
			//Muestra extracto
			SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
			System.out.println("=== Extracto de la cuenta "+id+ "(Movimientos:"+movs.size()+") === (Desde:"+sdf.format(desde)+ " Hasta:"+sdf.format(hasta)+")");
			for (Movimiento m:movs){
				System.out.println(m);
			}
		} catch (Exception e) {
			throw new Exception("Error al realizar extracto de movimientos de la cuenta "+id, e);
		}
		
	}

	private static void realizarMovimientos() throws Exception {
		int id=Entrada.enteroVal("Id de cuenta?","Id de cuenta debe ser positivo!!",1,999999);
		try {
			Cuenta c=hib.buscarCuenta(id);
			if (c==null){
				System.out.println("Esta cuenta NO EXISTE !!");
				return;
			}
			System.out.println(c);
			int importe=Entrada.enteroVal("Importe?","Importe erroneo!!",null);
			hib.registrarMovimiento(c,importe);
			System.out.println("Importe:"+importe+" Nuevo saldo:"+c.getSaldo());
		} catch (Exception e) {
			throw new Exception("Error al realizar movimiento en cuenta "+id, e);
		}
		
	}

	private static void quitaTitularCuenta() throws Exception {
		String nif=null;
		int id=0;
		try {
			nif=Entrada.cadenaVal("NIF?","Introduzca digitos+letra!!","[0-9]+[A-Za-z]");
			Cliente cl=null;
			cl=hib.buscarCliente(nif);
			if (cl==null){
				System.out.println("Este nif NO EXISTE!!");
				return;
			}
			System.out.println(cl);
			id=Entrada.enteroVal("Id de cuenta?","Id de cuenta debe ser positivo!!",1,999999);
			Cuenta cu=hib.buscarCuenta(id);
			if (cu==null){
				System.out.println("Esta cuenta NO EXISTE !!");
				return;
			}
			if (!hib.quitaTitularCuenta(cl,cu)){
				System.out.println("Este NIF no es titular de esta cuenta");
				return;
			}
			System.out.println("Se ha quitado nif "+nif+" de la cuenta "+id);
		} catch (Exception e) {
			throw new Exception("Error al quitar titular "+nif+" a la cuenta "+id, e);
		}
		
	}

	private static void aniadeTitularCuenta() throws Exception {
		String nif=null;
		int id=0;
		try {
			nif=Entrada.cadenaVal("NIF?","Introduzca digitos+letra!!","[0-9]+[A-Za-z]");
			Cliente cl=null;
			cl=hib.buscarCliente(nif);
			if (cl==null){
				System.out.println("Este nif NO EXISTE!!");
				return;
			}
			System.out.println(cl);
			id=Entrada.enteroVal("Id de cuenta?","Id de cuenta debe ser positivo!!",1,999999);
			Cuenta cu=hib.buscarCuenta(id);
			if (cu==null){
				System.out.println("Esta cuenta NO EXISTE !!");
				return;
			}
			if (!hib.aniadeTitularCuenta(cl,cu)){
				System.out.println("Este NIF ya es titular de esta cuenta");
				return;
			}
			System.out.println("Se ha asociado el titular "+nif+" a la cuenta "+id);
		} catch (Exception e) {
			throw new Exception("Error al asociar titular "+nif+" a la cuenta "+id, e);
		}
	}

	private static void bajaCuenta() throws Exception {
		int id=Entrada.enteroVal("Id de cuenta?","Id de cuenta debe ser positivo!!",1,999999);
		try {
			Cuenta c=hib.buscarCuenta(id);
			if (c==null)
				System.out.println("Esta cuenta NO EXISTE !!");
			else{
				System.out.println(c);
				char resp=Entrada.caracterVal("Desea darla de baja S/N?","Introduzca S o N!!","[SN]");
				if (resp=='S'){
					hib.borrarCuenta(c);
					System.out.println("Cuenta dada de baja con clave: "+id);
				}
			}
		} catch (Exception e) {
			throw new Exception("Error al dar de baja la cuenta "+id, e);
		}
	}

	private static void listadoCuentas() throws Exception {
		try {
			Query query = hib.getSesion().createQuery("SELECT c FROM Cuenta c");
			List<Cuenta> cuentas = query.list();
			for (Cuenta c : cuentas) 
			   System.out.println(c);
		} catch (Exception e) {
			throw new Exception("Error al listar clientes", e);
		}
	}
	private static void altaCuenta() throws Exception {
		System.out.println("Saldo?");
		int saldo=Entrada.entero();
		Cuenta c=new Cuenta(saldo);
		try {
				int clave=hib.guardarCuenta(c);
				System.out.println("Cuenta dada de alta con clave: "+clave);
		} catch (Exception e) {
			throw new Exception("Error al dar de alta la cuenta "+c.toString(), e);
		}
		
	}

	private static void listadoClientes() throws Exception {
		try {
			Query query = hib.getSesion().createQuery("SELECT c FROM Cliente c");
			List<Cliente> clientes = query.list();
			for (Cliente c : clientes) 
			   System.out.println(c);
		} catch (Exception e) {
			throw new Exception("Error al listar clientes", e);
		}
		
	}

	private static void altaModificacionCliente() throws Exception {
		String nif=Entrada.cadenaVal("NIF?","Introduzca digitos+letra!!","[0-9]+[A-Za-z]");
		Cliente c=null;
		try {
			c=hib.buscarCliente(nif);
			if (c!=null){
				System.out.println("MODIFIQUE CAMPOS DE:"+c);
				try {
					c.setNombre(Entrada.cadenaVal("Nombre ["+c.getNombre()+"]?","Introduzca al menos tres caracteres!!",".{3,}",true));
				} catch (Exception e) {
					//Se ha pulsado ENTER al introducir el dato, no se modifica el atributo
				}
				try {
					c.setDireccion(Entrada.cadenaVal("Direccion ["+c.getDireccion()+"])","",null,true));
				} catch (Exception e) {
					//Se ha pulsado ENTER al introducir el dato, no se modifica el atributo
				}
				try {
					c.setFNac(Entrada.fechaVal("Fecha ["+new SimpleDateFormat("dd-MM-yyyy").format(c.getFNac())+"]?","Fecha incorrecta!!","dd-MM-yyyy",true).getTime());
				} catch (Exception e) {
					//Se ha pulsado ENTER al introducir el dato, no se modifica el atributo
				}
				int clave=hib.guardarCliente(c);
				System.out.println("Cliente modificado con clave: "+clave);
			}
			else{
				System.out.println("ESTE NIF NO EXISTE!!");
				char resp=Entrada.caracterVal("Desea darlo de alta S/N?","Introduzca S o N!!","[SN]");
				if (resp=='S'){
					c=new Cliente();
					c.setNif(nif);
					c.setNombre(Entrada.cadenaVal("Nombre?","Introduzca al menos tres letras!!",".{3,}"));
					c.setDireccion(Entrada.cadenaVal("Direccion?","",null));
					c.setFNac(Entrada.fechaVal("Fecha (DD-MM-AAAA)?","Fecha incorrecta!!","dd-MM-yyyy").getTime());
					int clave=hib.guardarCliente(c);
					System.out.println("Cliente dado de alta con clave: "+clave);
				}
			}
		} catch (Exception e) {
			throw new Exception("Error al dar de alta/modificar el cliente "+c.toString(), e);
		}
		
	}
	
	private static void bajaCliente () throws Exception {
		String nif=Entrada.cadenaVal("NIF?","Introduzca digitos+letra!!","[0-9]+[A-Za-z]");
		try {
			Cliente c=hib.buscarCliente(nif);
			if (c==null)
				System.out.println("Este nif NO EXISTE !!");
			else{
				System.out.println(c);
				char resp=Entrada.caracterVal("Desea darlo de baja S/N?","Introduzca S o N!!","[SN]");
				if (resp=='S'){
					hib.borrarCliente(c);
					System.out.println("Cliente dado de baja con nif: "+nif);
				}
			}
		} catch (Exception e) {
			throw new Exception("Error al dar de baja la cuenta "+nif, e);
		}
	}

	private static void mostrarMenu() {
		System.out.println("\n====================");
		System.out.println("1-Alta/Modificación cliente");
		System.out.println("2-Baja cliente");
		System.out.println("3-Listado clientes");
		System.out.println("4-Alta cuenta");
		System.out.println("5-Baja cuenta");
		System.out.println("6-Listado cuentas");
		System.out.println("7-Añadir titular a cuenta");
		System.out.println("8-Añadir titular de cuenta");
		System.out.println("9-Realizar movimientos");
		System.out.println("10-Extracto movimientos");

		System.out.println("0-Salir");
	}
}
