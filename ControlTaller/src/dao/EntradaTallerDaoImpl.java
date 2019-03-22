package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.EntradaTaller;
import entity.Vehicle;
import javafx.scene.control.TableColumn;

public class EntradaTallerDaoImpl implements EntradaTallerDao{
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("taller");
	EntityManager em  = emf.createEntityManager();
	

	
	Logger log = LoggerFactory.getLogger(this.getClass());

	public EntradaTallerDaoImpl()  {
		
	}

	@Override
	public List<EntradaTaller> llistarEntradesTaller() {
		List<EntradaTaller> llistaEntradaTaller = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			llistaEntradaTaller = em.createQuery("from EntradaTaller").getResultList();
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}

		return llistaEntradaTaller;
	}
	
	@Override
	public List<EntradaTaller> llistarEntradesVehcile(Vehicle vehicle){
		List<EntradaTaller> llistaEntradaTaller = new ArrayList<>();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			Query query = em.createQuery("SELECT entrada FROM EntradaTaller entrada WHERE"
					+ " entrada.vehicleTaller.id = :vehicleId");
			query.setParameter("vehicleId", vehicle.getCodiVehicle());
			llistaEntradaTaller = query.getResultList();
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}

		return llistaEntradaTaller;
	}
	/*CONSULTA JPQL String consulta = "SELECT entrada FROM EntradaTaller entrada WHERE entrada.vehicleTaller = :vehicleTaller";*/

	@Override
	public EntradaTaller selectEntradaTaller(int id) {
		EntradaTaller et = new EntradaTaller();
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			et = em.find(EntradaTaller.class, id);
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}		
		return et;
	}

	@Override
	public void eliminarEntrada(int id) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.remove(em.find(EntradaTaller.class, id));
			em.getTransaction().commit();
		}catch (Exception e) {
			throw e;
		}finally {
			em.close();
		}
		
	}

	@Override
	public void guardarEntrada(EntradaTaller entradaTaller) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.persist(entradaTaller);
			em.getTransaction().commit();
			log.info("Entrada a taller guardada correctament");
		}catch (Exception e) {
			log.error("ERROR GUARDANT ENTRADA A TALLER");
			log.error(e.getMessage());
			throw e;
		}finally {
			em.close();
		}
		
	}
	
	@Override
	public void updateEntradaTaller(EntradaTaller entrada) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			em.merge(entrada);
			em.getTransaction().commit();
			log.info("Entrada a taller actualitzada correctament");
		}catch (Exception e) {
			log.error("ERROR ACTUALITZANT ENTRADA A TALLER");
			log.error(e.getMessage());
			throw e;
		}finally {
			em.close();
		}
	}
	
	
	

}
