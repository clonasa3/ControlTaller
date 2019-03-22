package servei;

import java.util.List;

import entity.Persona;

public interface PersonaService {

	public List<Persona> cercarTotesLesPersones();
	public List<Persona> cercarPersonaDniNom(String dni, String nom);
	public Persona cercarUnaPersonaDni(String dni);
	public Persona cercarUnaPersonaId(int idPersona);
	public void guardarPersona(Persona persona);
	public void actualitzarPersona(Persona persona);
	public void eliminarPersona(Persona persona);
}
