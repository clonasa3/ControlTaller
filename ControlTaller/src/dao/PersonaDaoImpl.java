package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Persona;

public class PersonaDaoImpl implements PersonaDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("taller");
	EntityManager em = emf.createEntityManager();
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public PersonaDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Persona> selectAllPersona() {
		List<Persona> llistaPersones = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			 llistaPersones=(List<Persona>) em.createQuery("FROM Persona").getResultList();
			em.getTransaction().commit();
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			em.close();
		}
		
		
		return llistaPersones;
	}
	
	@Override
	public List<Persona> findPersonaByDniAndName(String dni, String nom){
		List<Persona> llistaPersones = new ArrayList<>();
		String consulta="SELECT persona FROM Persona persona WHERE persona.dni like :dni and persona.nombre like :nom";
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery(consulta);
			query.setParameter("dni", "%"+dni+"%");
			
			query.setParameter("nom", "%"+nom+"%");
			log.info("Nom a cercar: "+nom);
			em.getTransaction().begin();			
			llistaPersones = (List<Persona>) query.getResultList();
			em.getTransaction().commit();
		}catch (Exception e) {
			// TODO: handle exception
			
			throw e;
		}finally {
			em.close();
		}
		return llistaPersones;
	}

	@Override
	public Persona selectPersonaDni(String dni) {
		Persona persona = new Persona();
		String consulta="SELECT p FROM Persona p WHERE p.dni = :dni";
		try {
			em = emf.createEntityManager();
			Query query = em.createQuery(consulta);
			query.setParameter("dni", dni);
			em.getTransaction().begin();			
			persona = (Persona) query.getSingleResult();
			System.out.println("Persona seleccionada per DNI: " + persona.toString());
			em.getTransaction().commit();
		}catch (Exception e) {
			// TODO: handle exception
			
			throw e;
		}finally {
			em.close();
		}
		return persona;
	}

	@Override
	public Persona selectPersonaId(int idPersona) {
		System.out.println("------------------------SOM A DINS DE PERSONADAOIMPL ---> SelectPersonaId(String idPersona)");
		Persona persona = new Persona();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			persona = em.find(Persona.class, idPersona);
			System.out.println("Persona seleccionada per ID: " + persona.toString());
			em.getTransaction().commit();
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			em.close();
		}
		return persona;
	}

	@Override
	public void updatePersona(Persona persona) {
		//Ha de ser un merge?
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(persona);
			em.getTransaction().commit();
		}catch (Exception e) {
			// TODO: handle exception
			throw e;
		}finally {
			em.close();
		}



	}

	@Override
	public void savePersona(Persona persona) {

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(persona);
			em.getTransaction().commit();
			log.info("Persona guardada correctament");
		}catch (Exception e) {
			log.error("Error guardant la persona la base de dades");
			log.error(e.getMessage());
			throw e;
		}finally {
			log.info("FINALLY TANCANT CONEXIO");
			em.close();
		}


	}
	
	private void checkOpenedEntityManager(EntityManager em) {
		if(em.isOpen()) {
			log.info("Enity Manager esta Obert");
		}else {
			log.info("Enity Manager esta Tancat");
		}
	}

	@Override
	public void deletePersona(Persona persona) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			//Quant arriva aquí la persona està detached, per tant no la podrem eliminar
			//El que fem és que li diem que la persona = persona donant el cotnexte de pesistencia amb el merge
			//que la tornara a posar en estat persistent, desrpés ja la podrem eliminar
			persona=em.merge(persona);
			em.remove(persona);
			em.getTransaction().commit();
		}catch (Exception e) {
			log.error("Error eliminant la persona");
			log.error(e.getMessage());
			throw e;
		}finally {
			em.close();
		}
		


	}

}
