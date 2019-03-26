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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Vehicles")
public class Vehicle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name="codi_vehicle")
	private int codiVehicle;
	@Column(name="nom_vehicle")
	private String nomVehicle;
	@Column(name="marca_vehicle")
	private String marcaVehicle;
	@Column(name="identificador_vehicle")
	private String identificadorVehicle;
	@Column(name="observacions_vehicle")
	private String obsrevacionsVehicle;
	
	
	@Column(name="llista_incidencies_vehicles")
	@OneToMany(mappedBy="vehicleTaller", cascade= CascadeType.ALL, fetch = FetchType.EAGER)
	private List<EntradaTaller> entradesVehicleTaller = new ArrayList<EntradaTaller>();

	@ManyToOne(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
	@JoinColumn(name="persona_id")
	private Persona personaVehiculo;
	
	public Vehicle() {
		
	}


	public Vehicle(String nomVehicle, String marcaVehicle, String identificadorVehicle,
			String obsrevacionsVehicle, Persona personaVehichlo) {
		super();
		this.nomVehicle = nomVehicle;
		marcaVehicle = marcaVehicle;
		this.identificadorVehicle = identificadorVehicle;
		this.obsrevacionsVehicle = obsrevacionsVehicle;
		this.personaVehiculo = personaVehichlo;
	}



	/**
	 * @return the codiVehicle
	 */
	public int getCodiVehicle() {
		return codiVehicle;
	}

	/**
	 * @param codiVehicle the codiVehicle to set
	 */
	public void setCodiVehicle(int codiVehicle) {
		this.codiVehicle = codiVehicle;
	}

	/**
	 * @return the nomVehicle
	 */
	public String getNomVehicle() {
		return nomVehicle;
	}

	/**
	 * @param nomVehicle the nomVehicle to set
	 */
	public void setNomVehicle(String nomVehicle) {
		this.nomVehicle = nomVehicle;
	}

	/**
	 * @return the marcaVehicle
	 */
	public String getMarcaVehicle() {
		return marcaVehicle;
	}

	/**
	 * @param marcaVehicle the marcaVehicle to set
	 */
	public void setMarcaVehicle(String marcaVehicle) {
		this.marcaVehicle = marcaVehicle;
	}

	/**
	 * @return the identificadorVehicle
	 */
	public String getIdentificadorVehicle() {
		return identificadorVehicle;
	}

	/**
	 * @param identificadorVehicle the identificadorVehicle to set
	 */
	public void setIdentificadorVehicle(String identificadorVehicle) {
		this.identificadorVehicle = identificadorVehicle;
	}

	/**
	 * @return the obsrevacionsVehicle
	 */
	public String getObsrevacionsVehicle() {
		return obsrevacionsVehicle;
	}

	/**
	 * @param obsrevacionsVehicle the obsrevacionsVehicle to set
	 */
	public void setObsrevacionsVehicle(String obsrevacionsVehicle) {
		this.obsrevacionsVehicle = obsrevacionsVehicle;
	}

	/**
	 * @return the entradesVehicleTaller
	 */
	public List<EntradaTaller> getEntradesVehicleTaller() {
		return entradesVehicleTaller;
	}

	/**
	 * @param entradesVehicleTaller the entradesVehicleTaller to set
	 */
	public void setEntradesVehicleTaller(List<EntradaTaller> entradesVehicleTaller) {
		this.entradesVehicleTaller = entradesVehicleTaller;
	}



	/**
	 * @return the personaVehichlo
	 */
	public Persona getPersonaVehichlo() {
		return personaVehiculo;
	}



	/**
	 * @param personaVehichlo the personaVehichlo to set
	 */
	public void setPersonaVehichlo(Persona personaVehichlo) {
		this.personaVehiculo = personaVehichlo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if(personaVehiculo!=null) {
			return "Vehicle [codiVehicle=" + codiVehicle + ", nomVehicle=" + nomVehicle + ", MarcaVehicle=" + marcaVehicle
					+ ", identificadorVehicle=" + identificadorVehicle +", personaId: "+ personaVehiculo.getIdPersona() +", obsrevacionsVehicle=" + obsrevacionsVehicle
					+ ", entradesVehicleTaller=" + entradesVehicleTaller + "]";
		}
		return "Vehicle [codiVehicle=" + codiVehicle + ", nomVehicle=" + nomVehicle + ", MarcaVehicle=" + marcaVehicle
				+ ", identificadorVehicle=" + identificadorVehicle + ", obsrevacionsVehicle=" + obsrevacionsVehicle
				+ ", entradesVehicleTaller=" + entradesVehicleTaller + "]";
	}
	
	
	

}
