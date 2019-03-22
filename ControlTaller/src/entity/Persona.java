package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name="Persona")
public class Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int idPersona;
	
	@Column(name="nombre")
	private String nombre;
	
	@Column(name="dni")
	private String dni;
	
	@Column(name="direccio")
	private String direccio;
	
	@Column(name="poblacio")
	private String poblacio;
	
	@Column(name="tlf_fix")
	private String tlfFix;
	
	@Column(name="tlf_mobil")
	private String tlfMobil;
	
	@Column(name="email")
	private String email;
	
	
	@Column(name="llista_vehicles")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="personaVehiculo", fetch = FetchType.EAGER, orphanRemoval=true)
	private List<Vehicle> llistaVehicles = new ArrayList<Vehicle>();

	public Persona() {}
	
	

	
	
	public Persona(String nombre, String dni, String direccio, String poblacio, String tlfFix,
			String tlfMobil, String email) {
		this.nombre = nombre;
		this.dni = dni;
		this.direccio = direccio;
		this.poblacio = poblacio;
		this.tlfFix = tlfFix;
		this.tlfMobil = tlfMobil;
		this.email = email;
	}





	public Persona(String nombre, String dni, String direccio, String poblacio, String tlfFix, String tlfMobil,
			String email, List<Vehicle> llistaVehicles) {
		super();
		this.nombre = nombre;
		this.dni = dni;
		this.direccio = direccio;
		this.poblacio = poblacio;
		this.tlfFix = tlfFix;
		this.tlfMobil = tlfMobil;
		this.email = email;
		this.llistaVehicles = llistaVehicles;
	}


	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return the poblacio
	 */
	public String getPoblacio() {
		return poblacio;
	}

	/**
	 * @param poblacio the poblacio to set
	 */
	public void setPoblacio(String poblacio) {
		this.poblacio = poblacio;
	}

	/**
	 * @return the tlfFix
	 */
	public String getTlfFix() {
		return tlfFix;
	}

	/**
	 * @param tlfFix the tlfFix to set
	 */
	public void setTlfFix(String tlfFix) {
		this.tlfFix = tlfFix;
	}

	/**
	 * @return the tlfMobil
	 */
	public String getTlfMobil() {
		return tlfMobil;
	}

	/**
	 * @param tlfMobil the tlfMobil to set
	 */
	public void setTlfMobil(String tlfMobil) {
		this.tlfMobil = tlfMobil;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the llistaVehicles
	 */
	public List<Vehicle> getLlistaVehicles() {
		return llistaVehicles;
	}

	/**
	 * @param llistaVehicles the llistaVehicles to set
	 */
	public void setLlistaVehicles(List<Vehicle> llistaVehicles) {
		this.llistaVehicles = llistaVehicles;
	}

	

	/**
	 * @return the idPersona
	 */
	public int getIdPersona() {
		return idPersona;
	}


	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(int idPersona) {
		this.idPersona = idPersona;
	}


	/**
	 * @return the direccio
	 */
	public String getDireccio() {
		return direccio;
	}


	/**
	 * @param direccio the direccio to set
	 */
	public void setDireccio(String direccio) {
		this.direccio = direccio;
	}





	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Persona [idPersona=" + idPersona + ", nombre=" + nombre + ", dni=" + dni + ", direccio=" + direccio
				+ ", poblacio=" + poblacio + ", tlfFix=" + tlfFix + ", tlfMobil=" + tlfMobil + ", email=" + email
				+ ", llistaVehicles=" + llistaVehicles + "]";
	}



	
	

}
