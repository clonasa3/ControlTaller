package test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.service.spi.Manageable;

import entity.Persona;
import entity.Vehicle;
import servei.PersonaServiceImpl;
import servei.VehicleServiceImpl;

public class test {
	private static EntityManager manager;
	private static EntityManagerFactory emf;

	public static void main(String[] args) {
		// Crear gestor de persistencia
		List<Vehicle> llistaVehicles = new ArrayList<Vehicle>();
		/*
		 * emf=Persistence.createEntityManagerFactory("taller");
		 * manager=emf.createEntityManager();
		 * 
		 * 
		 * Vehicle v = new Vehicle("moto","honda","H1234","Xafat",p);
		 * llistaVehicles.add(v); p.setLlistaVehicles(llistaVehicles);
		 * manager.getTransaction().begin(); manager.persist(p); manager.persist(v);
		 * manager.getTransaction().commit(); imprimirTodo(); manager.close();
		 * entrarPersona(new
		 * Persona("Hans2","01234555aL","TopoVilla","0000000","000000","e@gmail.com"));
		 * entrarVehicle(new Vehicle("Camio", "Nissan", "5554444pppp", "GUapooo", p));
		 */
		/*
		 * Persona p = new
		 * Persona("Tadadad2o","42222222295a54","TopoVilla","0000000","000000",
		 * "e@gmail.com"); Vehicle v = new
		 * Vehicle("CAMION","PEGASOO","H1234","Xafat",p); llistaVehicles.add(v);
		 * p.setLlistaVehicles(llistaVehicles); seleccionarPersona("4p87d95a54");
		 * entrarPersona(p); entrarVehicle(v);
		 */
		//retornarVehicles();
		//imprimirTodo();
		imprimirTodo();
		entrarPersonaNoBici();
		PersonaServiceImpl pi = new PersonaServiceImpl();
		for(Persona p : pi.cercarTotesLesPersones()) {
			System.out.println(p.toString());
		}
		seleccionarPersonaId(1);
		seleccionarPersonaDni("5555");
		imprimirTodo();


	}

	private static void imprimirTodo() {

		PersonaServiceImpl pi = new PersonaServiceImpl();
		List<Persona> personas = pi.cercarTotesLesPersones();
		System.out.println("En esta base de datos hay: " + personas.size() + " personas");
		for (Persona p : personas) {
			System.out.println(p.toString());
		}

	}

	
	private static void seleccionarPersonaId(int idPersona) {
		PersonaServiceImpl pi = new PersonaServiceImpl();
		System.out.println("------------------seleccionarPersonaId-------------------");
		System.out.println("ID: " + idPersona +" PERSONA: " + pi.cercarUnaPersonaId(idPersona));
	}
	
	private static void seleccionarPersonaDni(String dni) {
		PersonaServiceImpl pi = new PersonaServiceImpl();
		System.out.println("------------------seleccionarPersonaCodi-------------------");
		System.out.println("PERSONA SELECCIONADA: "+pi.cercarUnaPersonaDni(dni).toString());
	}


	private static void entrarPersona(Persona p) {
		PersonaServiceImpl pi = new PersonaServiceImpl();
		pi.guardarPersona(p);
	}
	
	private static void entrarPersonaNoBici() {
		Persona p = new Persona("Pol", "dddddddddd", "Carrer salchicha", "Ripoll", "987987987", "666555444", "ppppp@ccccc.com");
		PersonaServiceImpl pi = new PersonaServiceImpl();
		pi.guardarPersona(p);
		
	}

	private static void eliminarPersona(String id) {

	}

	private static void entrarVehicle(Vehicle v) {
		VehicleServiceImpl vi = new VehicleServiceImpl();
		vi.guardarVehicle(v);
	}

	private static void retornarVehicles() {
		VehicleServiceImpl vi = new VehicleServiceImpl();
		List<Vehicle> vehicles = vi.llistarVehicles();
		System.out.println("Tenim : " + vehicles.size() + "  vehicles");
		for (Vehicle v : vehicles) {
			System.out.println(v.toString());
		}
	}

}
