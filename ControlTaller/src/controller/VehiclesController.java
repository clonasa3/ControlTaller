package controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.GestorVistes.Vistes;
import entity.Persona;
import entity.Vehicle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import servei.PersonaServiceImpl;
import servei.VehicleServiceImpl;

public class VehiclesController {
	
	//Logger
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	//TEXT FIELDS
	@FXML
	private TextField txtVehicleId;
	@FXML
	private TextField txtVehcileMarca;
	@FXML
	private TextField txtVehicleClientId;
	@FXML
	private TextField txtVehcileModel;
	@FXML
	private TextField txtVehicleIdentificador;	
	@FXML
	private TextArea txtVehicleObservacions;
	@FXML
	private Button btnTaller;
	@FXML
	private Button btnGuardarVehicle;
	@FXML
	private Button btnEliminarVehicle;
	@FXML
	private Button btnModificarVehicle;
	@FXML
	private Button btnHome;
	@FXML
	private ToggleButton tglBuscarVehiculos;
	private int posicioSeleccionada=-3;
	private int posicioGuardada;
	//Table View de Vehicles
	@FXML
	private TableView<Vehicle> twVehicles;
	//Columnes del Table View
	private TableColumn<Vehicle, Integer> colIdVehicle;
	private TableColumn<Vehicle, String> colModelVehicle;
	private TableColumn<Vehicle, String> colMarcaVehicle;
	private TableColumn<Vehicle, String> colIdentificadorVehicle;
	private TableColumn colAveriesResoltes;
	private TableColumn colAveriesObertes;
	
	private GestorVistes gv = GestorVistes.getGestorVista();
	
	private VehicleServiceImpl vehicleServeiImpl = new VehicleServiceImpl();
	private PersonaServiceImpl personaServiceImpl = new PersonaServiceImpl();
	
	/*Persona que rebem desde ClientsController, és a dir
	 * tenim una persona seleccionada a clientController i cliquem anar a veure els seus vehicles.
	 */
	private Persona personaPassada;
	private boolean filtresActivats = false;
	//Vehicle seleccionat al dobleClick
	private Vehicle vehicleSeleccionat;

	public VehiclesController() {
		// TODO Auto-generated constructor stub
	}

	public void vistaTaller(javafx.event.ActionEvent event) {
		/*Aqui hem de passar al taller el vechile que volem posar-li incidències
		 * per fer-ho hem d'afegir al controlador del taller un vehcilePassat i li fem el Set desde aquí
		 * està tot fet al gestor de vistes
		 */
		gv.canviarVista(event, Vistes.CRUDTaller.toString(), vehicleSeleccionat);
	}
	public void tornarHome(javafx.event.ActionEvent event) {
		gv.canviarVista(event, Vistes.IniciTaller.toString());
	}
	
	public Persona getPersonaPassada() {
		return personaPassada;
	}
	/**
	 * Assigna el valor del id de la persona al camp del vehicla i ens retorna el llistat de vehicles de la persona perque seleccionem el que toca
	 * i ens carregui les dades.
	 * @param personaPassada
	 */
	public void setPersonaPassada(Persona personaPassada) {
		log.info("VEHICLES CONTROLLER PERSONA PASSADA");
		this.personaPassada=personaPassada;
		//Mostrar al txtBox
		
		txtVehicleClientId.setText(String.valueOf(this.personaPassada.getIdPersona()));
		log.info("PERSONA ID:"+personaPassada.getIdPersona());
		
		GestorVistes.emplenarTableView(personaPassada.getLlistaVehicles(), twVehicles);

	}
	//TODO Fer comporvacions dels camps abans de persistir-los a la Base de dades
	/**
	 * Recull les dades del CRUD de Vehicles i genera un nou Objecte Vehicle que el guarda a la Base de dades.
	 * 
	 */
	public void guardarVehicle() {
		Vehicle vehicleEntrat = new Vehicle();
		vehicleEntrat.setMarcaVehicle(txtVehcileMarca.getText());
		vehicleEntrat.setNomVehicle(txtVehcileModel.getText());
		vehicleEntrat.setIdentificadorVehicle(txtVehicleIdentificador.getText());
		vehicleEntrat.setObsrevacionsVehicle(txtVehicleObservacions.getText());
		if(txtVehicleClientId!=null) {
			log.info("LA PERSONA NO ES NULA");
			vehicleEntrat.setPersonaVehichlo(personaServiceImpl.cercarUnaPersonaId(Integer.parseInt(txtVehicleClientId.getText())));
			log.info("PERSONA PROPIETARIA DEL VEHICLE: " + personaServiceImpl.cercarUnaPersonaId(Integer.parseInt(txtVehicleClientId.getText())));
		}
		vehicleServeiImpl.guardarVehicle(vehicleEntrat);
		log.info("S'ha Guardat el Vehicle correctament: " + vehicleEntrat.toString()); 
		refrescarCamps();
		refrescarTaula();
	}
	
	public void borrarVehicle() {
		Vehicle vEliminar = vehicleServeiImpl.selectVehicle(Integer.parseInt(txtVehicleId.getText()));
		vehicleServeiImpl.eliminarVehicle(vEliminar);
		refrescarCamps();
		refrescarTaula();
	}
	
	public void modificarVehicle() {
		Vehicle vModificar = vehicleServeiImpl.selectVehicle(Integer.parseInt(txtVehicleId.getText()));
		vModificar.setMarcaVehicle(txtVehcileMarca.getText());
		vModificar.setNomVehicle(txtVehcileModel.getText());
		vModificar.setIdentificadorVehicle(txtVehicleIdentificador.getText());
		vModificar.setObsrevacionsVehicle(txtVehicleObservacions.getText());
		vehicleServeiImpl.updateVehicle(vModificar);
		refrescarCamps();
	}

	public void dobleClick() {
		posicioSeleccionada=twVehicles.getSelectionModel().getSelectedIndex();
		if(posicioSeleccionada==posicioGuardada) {
			vehicleSeleccionat = twVehicles.getSelectionModel().getSelectedItem();
			omplirVehicle(vehicleSeleccionat);
		}else {
			posicioGuardada=posicioSeleccionada;
		}
		
	}
	//TODO revisar nulls o camps buits
	public void omplirVehicle(Vehicle vPassat) {
		txtVehicleId.setText(Integer.toString(vPassat.getCodiVehicle()));
		txtVehcileModel.setText(vPassat.getNomVehicle());
		txtVehcileMarca.setText(vPassat.getMarcaVehicle());
		txtVehicleClientId.setText(Integer.toString(vPassat.getPersonaVehichlo().getIdPersona()));
		txtVehicleIdentificador.setText(vPassat.getIdentificadorVehicle());
		txtVehicleObservacions.setText(vPassat.getObsrevacionsVehicle());
	}
	
	public void refrescarTaula() {
		if(personaPassada != null)
		{
			GestorVistes.emplenarTableView(vehicleServeiImpl.llistarVehiclesPersona(personaPassada),twVehicles);
		}
		else {
			GestorVistes.emplenarTableView(vehicleServeiImpl.llistarVehicles(),twVehicles);
		}
		
	}
	
	public void refrescarCamps() {
		txtVehicleId.setText("");
		txtVehcileModel.setText("");
		txtVehcileMarca.setText("");
		txtVehicleIdentificador.setText("");
		txtVehicleObservacions.setText("");
		
		refrescarTaula();
	}
	/**
	 * Metode que si esl filtres estan activats permet fer servir la consulta de filtres
	 * per Model,Marca, id, IdentificadorVehicle
	 */
	@FXML
	public void filtresVehicle() {
		if(filtresActivats) {
			if(vehicleSeleccionat==null) {
				log.info("MODEL: " + txtVehcileModel.getText() + " MARCA: " + txtVehcileMarca.getText() + " ID: " + txtVehicleId.getText() + " IDENTIFICADOR: " + 	txtVehicleIdentificador.getText());
				if(txtVehicleId.getText()==null || txtVehicleId.getText().equals("")) {
					GestorVistes.emplenarTableView(vehicleServeiImpl.llistarVehiclesModelAndMarcaAndIdentificador(txtVehcileModel.getText(), txtVehcileMarca.getText(), txtVehicleIdentificador.getText()), twVehicles);
				}else {
					GestorVistes.emplenarTableView(vehicleServeiImpl.llistarVehiclesModelAndMarcaAndId(txtVehcileModel.getText(), txtVehcileMarca.getText(), Integer.parseInt(txtVehicleId.getText()), txtVehicleIdentificador.getText()), twVehicles);
				}
				
			}
		}

	}
	/**
	 * Permet activar el sistema de filtres de la vista
	 */
	@FXML
	public void activarFiltres() {
		if(filtresActivats) {
			filtresActivats = false;
		}else {
			filtresActivats=true;
		}
	}
		
	@FXML
	private void initialize() {
		//--------Col ID Vehicle------//
		colIdVehicle = new TableColumn<>("ID");
		colIdVehicle.setCellValueFactory(new PropertyValueFactory<Vehicle,Integer>("codiVehicle"));
		//--------Col Marca Vehicle------//
		colMarcaVehicle = new TableColumn<>("MARCA");
		colMarcaVehicle.setCellValueFactory(new PropertyValueFactory<Vehicle,String>("marcaVehicle"));
		//--------Col Marca Vehicle------//
		colModelVehicle = new TableColumn<>("MODEL");
		colModelVehicle.setCellValueFactory(new PropertyValueFactory<>("nomVehicle"));
		
		//--------Col Identificador Vehicle------//
		colIdentificadorVehicle= new TableColumn<>("IDENTIFICADOR");
		colIdentificadorVehicle.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("identificadorVehicle"));
		
		//--------Col Marca Vehicle------//
		colAveriesResoltes = new TableColumn<>("Av. Resoltes");
		
		//--------Col Marca Vehicle------//
		colAveriesObertes =  new TableColumn<>("Av. Pendents");
		
		twVehicles.getColumns().addAll(colIdVehicle,colMarcaVehicle,colModelVehicle,colIdentificadorVehicle,colAveriesObertes,colAveriesResoltes);
		GestorVistes.emplenarTableView(vehicleServeiImpl.llistarVehicles(), twVehicles);
	}
}
