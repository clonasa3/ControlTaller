package dao;

import java.util.List;

import entity.Persona;

public interface PersonaDao {

	public List<Persona> selectAllPersona();
	public List<Persona> findPersonaByDniAndName(String dni, String name);
	public Persona selectPersonaDni(String dni);
	public Persona selectPersonaId(int idPersona);
	public void updatePersona(Persona persona);
	public void savePersona(Persona persona);
	public void deletePersona(Persona persona);
}
