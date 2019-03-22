package servei;

import java.util.List;

import dao.PersonaDaoImpl;
import entity.Persona;

public class PersonaServiceImpl implements PersonaService {

	private PersonaDaoImpl personaDao = new PersonaDaoImpl();
	
	public PersonaServiceImpl() {
		
	}

	@Override
	public List<Persona> cercarTotesLesPersones() {
		return personaDao.selectAllPersona();
	}
	
	@Override
	public List<Persona> cercarPersonaDniNom(String dni, String nom){
		return personaDao.findPersonaByDniAndName(dni, nom);
	}
	
	@Override
	public Persona cercarUnaPersonaDni(String dni) {
		return personaDao.selectPersonaDni(dni);
	}

	@Override
	public Persona cercarUnaPersonaId(int idPersona) {
		return personaDao.selectPersonaId(idPersona);
	}

	@Override
	public void guardarPersona(Persona persona) {
		personaDao.savePersona(persona);
		
	}

	@Override
	public void actualitzarPersona(Persona persona) {
		personaDao.updatePersona(persona);
		
	}

	@Override
	public void eliminarPersona(Persona persona) {
		personaDao.deletePersona(persona);
		
	}
	
	

}
