package entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="EntradesTaller")
public class EntradaTaller implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private int id;
	@Column(name="entrada_taller")
	private LocalDate entrada;
	
	@Column(name="sortida_taller")
	private LocalDate sortida;
	
	@Column(name="observacio_entrada_taller")
	private String observacioEntradaTaller;
	
	@Column(name="actuacio_entrada_taller")
	private String actuacioEntradaTaller;
	
	@Column(name="imatge_entrada_taller", columnDefinition="mediumblob")
	private byte[] imatgeIncidencia;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="vehicle_taller_id")
	private Vehicle vehicleTaller;
	
	

	public EntradaTaller() {
		
	}



	public EntradaTaller(LocalDate entrada, LocalDate sortida, String observacioEntradaTaller,
			Vehicle vehicleTaller) {
		super();
		this.entrada = entrada;
		this.sortida = sortida;
		this.observacioEntradaTaller = observacioEntradaTaller;
		this.vehicleTaller = vehicleTaller;
	}



	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the entrada
	 */
	public LocalDate getEntrada() {
		return entrada;
	}


	/**
	 * @param entrada the entrada to set
	 */
	public void setEntrada(LocalDate entrada) {
		this.entrada = entrada;
	}


	/**
	 * @return the sortida
	 */
	public LocalDate getSortida() {
		return sortida;
	}


	/**
	 * @param sortida the sortida to set
	 */
	public void setSortida(LocalDate sortida) {
		this.sortida = sortida;
	}


	/**
	 * @return the observacioEntradaTaller
	 */
	public String getObservacioEntradaTaller() {
		return observacioEntradaTaller;
	}


	/**
	 * @param observacioEntradaTaller the observacioEntradaTaller to set
	 */
	public void setObservacioEntradaTaller(String observacioEntradaTaller) {
		this.observacioEntradaTaller = observacioEntradaTaller;
	}
	
	public String getActuacioEntradaTaller() {
		return actuacioEntradaTaller;
	}



	public void setActuacioEntradaTaller(String actuacioEntradaTaller) {
		this.actuacioEntradaTaller = actuacioEntradaTaller;
	}
	
	



	public byte[] getImatgeIncidencia() {
		return imatgeIncidencia;
	}



	public void setImatgeIncidencia(byte[] imatgeIncidencia) {
		this.imatgeIncidencia = imatgeIncidencia;
	}



	/**
	 * @return the vehicleTaller
	 */
	public Vehicle getVehicleTaller() {
		return vehicleTaller;
	}



	/**
	 * @param vehicleTaller the vehicleTaller to set
	 */
	public void setVehicleTaller(Vehicle vehicleTaller) {
		this.vehicleTaller = vehicleTaller;
	}

	/**
	 * This method returns the ID of a vehicle, we use it in TallerController in initalize for fill the column
	 * where the id of the vehicle must appear.
	 * @return the id of vehicle
	 */
	public int getIdVehicle() {
		return vehicleTaller.getCodiVehicle();
	}
	
	public String getNomClient() {
		return vehicleTaller.getPersonaVehichlo().getNombre();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EntradaTaller [id=" + id + ", entrada=" + entrada + ", sortida=" + sortida
				+ ", observacioEntradaTaller=" + observacioEntradaTaller + ", actuacioEntradaTaller="
				+ actuacioEntradaTaller + "]";
	}

	



	
	
	
	
	

}
