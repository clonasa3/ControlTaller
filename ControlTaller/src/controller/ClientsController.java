package controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.GestorVistes.Vistes;
import entity.Persona;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import servei.PersonaServiceImpl;

public class ClientsController {
	
	//Logger
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	//CAMPS TEXTFIELD
	@FXML
	private TextField txtIdClient;
	@FXML
	private TextField txtDniClient;
	@FXML
	private TextField txtNomClient;
	@FXML
	private TextField txtEmailClient;
	@FXML
	private TextField txtDireccioClient;
	@FXML
	private TextField txtPoblacioClient;
	@FXML
	private TextField txtTelefonClient;
	@FXML
	private TextField txtMobilClient;
	@FXML
	private ToggleButton tglBuscarClientes;
	
	//TableView
	@FXML
	private TableView<Persona> twClients;
	
	//COLUMNES
	private TableColumn<Persona,String> colIdClient;
	private TableColumn<Persona,String> colDniClient;
	private TableColumn<Persona,String> colNomClient;
	private TableColumn<Persona,String> colEmailClient;
	private TableColumn<Persona,String> colDireccioClient;
	private TableColumn<Persona,String> colPoblacioClient;
	private TableColumn<Persona,String> colTelefonClient;
	private TableColumn<Persona,String> colMobilClient;
	
	//BUTTONS
	@FXML
	private Button btnVehicles;
	@FXML
	private Button btnHome;
	@FXML
	private Button btnNouPersona;
	@FXML
	private Button btnModificarPersona;
	@FXML
	private Button btnEliminarPersona;
	
	private GestorVistes gv = GestorVistes.getGestorVista();
	
	//Servei de persona per fer ús de la bd
	private PersonaServiceImpl psi = new PersonaServiceImpl();
	
	//Index NULL del Table View
	int indexTw = -99;
	int indexTwGuardat;
	
	private boolean busquedaActiva=false;
	
	ObservableList<Persona> obPersona = null;
	
	//Variable que serveix per saber si hi ha una persona seleccionada i per tant modifiquem les seves dades
	Persona personaSeleccionada = null;
	
	public ClientsController() {
		// TODO Auto-generated constructor stub
	}
	
	//Guardar persona a la BD
	@FXML
	public void guardarPersona() {
		Persona p = new Persona(txtNomClient.getText().toUpperCase(), txtDniClient.getText().toUpperCase(), txtDireccioClient.getText().toUpperCase(),
				txtPoblacioClient.getText().toUpperCase(),txtTelefonClient.getText().toUpperCase(), txtMobilClient.getText().toUpperCase(),txtEmailClient.getText().toUpperCase());
		log.error("Nova Persona: " + p.toString());
		psi.guardarPersona(p);
		resetCamps();
		GestorVistes.emplenarTableView(psi.cercarTotesLesPersones(), twClients);
	}
	
	//Modificar Persona
	@FXML
	public void modificarPersona() {
		personaSeleccionada.setNombre(txtNomClient.getText().toUpperCase());
		personaSeleccionada.setDni(txtDniClient.getText().toUpperCase());
		personaSeleccionada.setEmail(txtEmailClient.getText().toUpperCase());
		personaSeleccionada.setDireccio(txtDireccioClient.getText().toUpperCase());
		personaSeleccionada.setPoblacio(txtPoblacioClient.getText().toUpperCase());
		personaSeleccionada.setTlfFix(txtTelefonClient.getText().toUpperCase());
		personaSeleccionada.setTlfMobil(txtMobilClient.getText().toUpperCase());
		psi.actualitzarPersona(personaSeleccionada);
		resetCamps();
		GestorVistes.emplenarTableView(psi.cercarTotesLesPersones(), twClients);
		btnNouPersona.setDisable(false);
	}
	
	@FXML
	public void eliminarPersona() {
		psi.eliminarPersona(personaSeleccionada);
		resetCamps();
		GestorVistes.emplenarTableView(psi.cercarTotesLesPersones(), twClients);
		btnNouPersona.setDisable(false);
	}

	public void obrirVistaVehicles(javafx.event.ActionEvent event) {
		if(personaSeleccionada!=null) {
			gv.canviarVista(event, Vistes.CRUDVehicles.toString(), personaSeleccionada);
			Button botoClicat = (Button)event.getTarget();
			Stage stage = (Stage)botoClicat.getScene().getWindow();
			stage.close();
		}else {
			gv.canviarVista(event, Vistes.CRUDVehicles.toString());	
		}
			        
	}
	
	public void tornarHome(javafx.event.ActionEvent event) {
		gv.canviarVista(event, Vistes.IniciTaller.toString());
	}
	
	private void resetCamps() {
		txtIdClient.setText("");
		txtDniClient.setText("");
		txtNomClient.setText("");
		txtEmailClient.setText("");
		txtDireccioClient.setText("");
		txtPoblacioClient.setText("");
		txtTelefonClient.setText("");
		txtMobilClient.setText("");
		//personaSeleccionada=null;
		twClients.refresh();
	}
	
	//Metode de doble click que guarda el index seleccionat i en cas de que siguin iguals considera
	//Que s'ha fet el doble click i per tant obre les dades de la Persona
	public void dobleClick() {
		indexTw = twClients.getSelectionModel().getSelectedIndex();
		if(indexTw==indexTwGuardat) {
			//Aquí tenim la persona selccionada al fer doubleClick que es retorna amb RetornarPersonaTableView
			personaSeleccionada = retornarPersonaTableView();
			//Omplim els camps amb les dades de la persona Selccionada.
			omplirCampsPersonaPassada(personaSeleccionada);
			
		}else {
			indexTwGuardat=indexTw;
		}
	}
	
	//retorna la persona seleccionada amb doble click
	public Persona retornarPersonaTableView() {
		Persona pTornar = null;
		if(twClients.getSelectionModel().getSelectedItem() != null) {
			pTornar = twClients.getSelectionModel().getSelectedItem();
			log.info("Persona seleccioanda: " + pTornar.toString());
		}		
		return pTornar;
	}
	
	//Filtrar TableView
	public void filtrePersonesTWNomDNI(){
		if(busquedaActiva) {
			if(personaSeleccionada==null) {
				GestorVistes.emplenarTableView(psi.cercarPersonaDniNom(txtDniClient.getText().toUpperCase(), txtNomClient.getText().toUpperCase()),twClients);
			}
		}

	}
	
	//Metode per omplir els camps de text a partir de una persona
	private void omplirCampsPersonaPassada(Persona p) {
		txtIdClient.setText(String.valueOf(p.getIdPersona()));
		txtDniClient.setText(p.getDni());
		txtNomClient.setText(p.getNombre());
		txtEmailClient.setText(p.getEmail());
		txtDireccioClient.setText(p.getDireccio());
		txtPoblacioClient.setText(p.getPoblacio());
		txtTelefonClient.setText(p.getTlfFix());
		txtMobilClient.setText(p.getTlfMobil());
		btnNouPersona.setDisable(true);
	}
	
	/**
	 * Activar el Toggle pone en On el sistema de Busqueda del controlador
	 * i permite filtrar conetnidos del TableView con los Text Field
	 * 
	 */
	@FXML
	private void activarBuscar() {
		if(!busquedaActiva) {
			busquedaActiva=true;
		}else {
			busquedaActiva=false;
		}		
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {
		//--------Col ID Client------//
		colIdClient = new TableColumn<Persona, String>("ID");
		colIdClient.setCellValueFactory(new PropertyValueFactory<Persona, String>("idPersona"));
		//--------Col DNI Client------//
		colDniClient=new TableColumn<Persona, String>("DNI");
		colDniClient.setCellValueFactory(new PropertyValueFactory<Persona, String>("dni"));
		//--------Col NOM Client------//
		colNomClient=new TableColumn<Persona, String>("NOM");
		colNomClient.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombre"));
		//--------Col email Client------//
		colEmailClient=new TableColumn<Persona, String>("EMAIL");
		colEmailClient.setCellValueFactory(new PropertyValueFactory<Persona, String>("email"));
		//--------Col Direccio Client------//
		colDireccioClient=new TableColumn<Persona, String>("DIR.");
		colDireccioClient.setCellValueFactory(new PropertyValueFactory<Persona,String>("direccio"));
		//--------Col Població Client------//
		colPoblacioClient = new TableColumn<Persona, String>("POBLACIÓ");
		colPoblacioClient.setCellValueFactory(new PropertyValueFactory<>("poblacio"));
		//--------Col TELF Client------//
		colTelefonClient=new TableColumn<Persona, String>("TELF.");
		colTelefonClient.setCellValueFactory(new PropertyValueFactory<Persona, String>("tlfFix"));
		//--------Col Mobil Client------//
		colMobilClient=new TableColumn<Persona, String>("MOBIL");
		colMobilClient.setCellValueFactory(new PropertyValueFactory<>("tlfMobil"));
		
		twClients.getColumns().addAll(colIdClient,colDniClient,colNomClient,colEmailClient,colPoblacioClient,colDireccioClient,colTelefonClient);
		
		GestorVistes.emplenarTableView(psi.cercarTotesLesPersones(), twClients);
		
		
		
	}
}
