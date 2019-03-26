package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Persona;
import entity.Vehicle;
import servei.PersonaServiceImpl;
import servei.VehicleServiceImpl;

public class VehicleDaoImpl implements VehicleDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("taller");
	EntityManager em = emf.createEntityManager();
	
	PersonaServiceImpl psi = new PersonaServiceImpl();
	
	Logger log = LoggerFactory.getLogger(this.getClass());

	public VehicleDaoImpl() {
	}

	@Override
	public List<Vehicle> llistarVehicles() {
		List<Vehicle> llistaVehicles = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			 llistaVehicles=(List<Vehicle>) em.createQuery("from Vehicle").getResultList();
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}
		return llistaVehicles;
	}
	
	@Override
	public List<Vehicle> llistarVechilesPersona(Persona persona){
		List<Vehicle> llistaVehicles = new ArrayList<>();
		//String consulta = "SELECT vehicle from Vehcile  WHERE vechile.personaVehiculo.id equals :idPersonaPassada";
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			String consulta = "SELECT vehicle from Vehicle vehicle  WHERE vehicle.personaVehiculo.id = :idPersonaPassada";
			Query query = em.createQuery(consulta);
			query.setParameter("idPersonaPassada", persona.getIdPersona());
			llistaVehicles=query.getResultList();
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return llistaVehicles;
	}

	@Override
	public Vehicle selectVehicle(int id) {
		Vehicle vRetornar = new Vehicle();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			 vRetornar = em.find(Vehicle.class, id);
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}		

		return vRetornar;
	}
	//Atencio el camp que volem igualar en aquest cas v.personaVehiculas es tipus Persona i per tant hem de passar un parametre de tipus Persona per poder fer la Query
	/*CONSULTA JPQL String consulta = "SELECT v FROM Vehicle v WHERE v.personaVehichlo = :personaVehiculo";*/
	
	@Override
	public void eliminarVehicle(Vehicle vehicle) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			vehicle=em.merge(vehicle);
			/* Eliminar directament el vehcile no funciona, així que el que fem és recuperem la persona
			 * que té el vehcile i borrem el vehcile de la llista que té aquesta persona, per la propietat cascade
			 * al borrar-lo de la seva llista es borrarà de vehicles.
			 */
			Persona p = psi.cercarUnaPersonaId(vehicle.getPersonaVehichlo().getIdPersona());
			int indexVehicleEliminar = retornarIndexVehcileEliminarDinsPersona(p,vehicle);
			if(indexVehicleEliminar!=-1) {
				p.getLlistaVehicles().remove(indexVehicleEliminar);
				em.merge(p);
			}
			em.getTransaction().commit();
			
		}catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			em.close();
		}
		
	}

	/***
	 * Aquest mètode cerca dins de la llista de vehicles de Persona p si troba el vehicle que estem intentant
	 * eliminar, i de retorn torna el index si el troba o bé -1 si no el troba
	 * @param p
	 * @param vehicle
	 * @return indexVehicleEliminar
	 */
	private int retornarIndexVehcileEliminarDinsPersona(Persona p, Vehicle vehicle) {
		List<Vehicle> llistaVehciles = p.getLlistaVehicles();
		//Ho fem així ja que amb indexOf de la llista no ens funciona
		int indexVehicleEliminar = 0;
		boolean trobatVEliminar = false;
		while(indexVehicleEliminar<llistaVehciles.size() && !trobatVEliminar) {
			if(llistaVehciles.get(indexVehicleEliminar).getCodiVehicle() == vehicle.getCodiVehicle()) {
				trobatVEliminar=true;
			}
			else {
				indexVehicleEliminar++;
			}
		}
		
		if(!trobatVEliminar) {
			indexVehicleEliminar=-1;
		}
		
		return indexVehicleEliminar;
	}
	
	@Override
	public void guardarVehicle(Vehicle vehicle) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			//Posem la persona del vehicle en estat persistent
			Persona personaVehcilePersistent = vehicle.getPersonaVehichlo();
			personaVehcilePersistent=em.merge(personaVehcilePersistent);
			//Tornem a fer set de la persona al vcehicle
			vehicle.setPersonaVehichlo(personaVehcilePersistent);
			em.persist(vehicle);
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}

		
		
	}

	@Override
	public void updateVehicle(Vehicle vehicle) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(vehicle);
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}
		
	}

	@Override
	public List<Vehicle> llistarVehiclesModelAndMarcaAndId(String model, String marca, int id, String identificadorVehicle) {
		List<Vehicle> llistaVehicles = new ArrayList<>();
		String consulta = "Select v FROM Vehicle v WHERE v.nomVehicle LIKE :model AND v.marcaVehicle LIKE :marca AND v.codiVehicle LIKE :id AND v.identificadorVehicle LIKE :identificadorVehicle";
		try {
			em=emf.createEntityManager();
			Query query = em.createQuery(consulta);
			query.setParameter("model", "%"+model+"%");
			query.setParameter("marca", "%"+marca+"%");
			query.setParameter("id", "%" + id + "%");
			query.setParameter("identificadorVehicle", "%"+identificadorVehicle+"%");
			em.getTransaction().begin();
			llistaVehicles=query.getResultList();
			em.getTransaction().commit();
			
		}catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			em.close();
		}
		return llistaVehicles;
	}
	@Override
	public List<Vehicle> llistarVehiclesModelAndMarcaAndIdentificador(String model, String marca, String identificadorVehicle) {
		List<Vehicle> llistaVehicles = new ArrayList<>();
		String consulta = "Select v FROM Vehicle v WHERE v.nomVehicle LIKE :model AND v.marcaVehicle LIKE :marca AND v.identificadorVehicle LIKE :identificadorVehicle";
		try {
			em=emf.createEntityManager();
			Query query = em.createQuery(consulta);
			query.setParameter("model", "%"+model+"%");
			query.setParameter("marca", "%"+marca+"%");
			query.setParameter("identificadorVehicle", "%"+identificadorVehicle+"%");
			em.getTransaction().begin();
			llistaVehicles=query.getResultList();
			em.getTransaction().commit();
			
		}catch (Exception e) {
			log.error(e.getMessage());
		}finally {
			em.close();
		}
		return llistaVehicles;
	}

	@Override
	public List<Vehicle> llistarVehicleMarca(String marca) {
		// TODO Auto-generated method stub
		return null;
	}



}
