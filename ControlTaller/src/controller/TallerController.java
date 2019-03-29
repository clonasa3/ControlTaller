package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.action.internal.EntityIdentityInsertAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import controller.GestorVistes.Vistes;
import entity.EntradaTaller;
import entity.Vehicle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import servei.EntradaTallerServiceImpl;

public class TallerController {
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private Vehicle vehiclePassat;
	
	@FXML
	private TextField txtIdVehicle;
	@FXML
	private TextField txtIncidenciaId;
	@FXML
	private TextArea txtIncidenciaActuacio;
	@FXML
	private TextArea txtIncidenciaAveria;
	
	@FXML
	private Button btnIncidenciaNou;
	@FXML
	private Button btnIncidenciaModificar;
	@FXML
	private Button btnIncidenciaEliminar;
	@FXML
	private Button btnHome;
	@FXML
	private Button btnAfegirImatge;
	@FXML
	private Button btnEliminarImatge;
	@FXML
	private Button btnDates;
	@FXML
	private DatePicker dpEntrada;
	@FXML
	private DatePicker dpSortida;
	@FXML
	private ImageView imgIncidenciaImatge;
	
	private boolean filtresActivats = false;
	
	@FXML
	TableView<EntradaTaller> twEntradesTaller;
	
	TableColumn<EntradaTaller, String> colIdEntrada;
	TableColumn<EntradaTaller, String> colIdVehicle;
	TableColumn<EntradaTaller, String> colNomClient;
	TableColumn<EntradaTaller, LocalDate> colEntrada;
	TableColumn<EntradaTaller, LocalDate> colSortida;
	TableColumn<EntradaTaller, String> colObseravioAveria;
	TableColumn<EntradaTaller, String> colActuacioAveria;
	
	File imatge;
	
	private String extensioImg;
	
	//Entrada Taller seleccionada del tw
	private EntradaTaller entradaSeleccionada;
	
	private GestorVistes gv = GestorVistes.getGestorVista();
	
	private EntradaTallerServiceImpl serveiTaller = new EntradaTallerServiceImpl();
	
	//Memoria Index Clicat TW
	int indexAnteriorSeleccionat = -99;
	

	
	public TallerController() {
	}
	
	public void tornarHome(javafx.event.ActionEvent event) {
		gv.canviarVista(event, Vistes.IniciTaller.toString());
	}
	
	public void setVehiclePassat(Vehicle vehicle) {
		vehiclePassat = vehicle;
		log.info("VEHICLE QUE REBEM AL TALLER: "+vehiclePassat.toString());
		txtIdVehicle.setText(String.valueOf(vehiclePassat.getCodiVehicle()));
		GestorVistes.emplenarTableView(vehicle.getEntradesVehicleTaller(), twEntradesTaller);		
	}
	
	@FXML
	public void guardarIncidencia() {
		EntradaTaller entradaTaller = new EntradaTaller();
		entradaTaller.setVehicleTaller(vehiclePassat);
		entradaTaller.setEntrada(dpEntrada.getValue());
		entradaTaller.setSortida(dpSortida.getValue());
		entradaTaller.setActuacioEntradaTaller(txtIncidenciaActuacio.getText().toUpperCase());
		entradaTaller.setObservacioEntradaTaller(txtIncidenciaAveria.getText().toUpperCase());
		if(imgIncidenciaImatge.getImage()!=null) {
			entradaTaller.setImatgeIncidencia(convertirImatgeByetArray(imgIncidenciaImatge.getImage()));			
		}
		serveiTaller.guardarEntrada(entradaTaller);
		log.info(entradaTaller.toString());
		GestorVistes.emplenarTableView(serveiTaller.llistaEntradesSegonsVehicle(vehiclePassat), twEntradesTaller);
	}
	
	public byte[] convertirImatgeByetArray(Image imatgeVista) {
		Image imgVista=imgIncidenciaImatge.getImage();
		BufferedImage image = SwingFXUtils.fromFXImage(imgVista, null);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] data = null;
		try {
			//Podem comprobar si es equals a jpg,png,jpeg
			if(extensioImg!=null) {
				ImageIO.write(image, extensioImg, baos);
				data = baos.toByteArray();
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	@FXML
	public void modificarIncidencia() {
		//Modifiquem els valors del item carregat que és el selccionat
		entradaSeleccionada.setEntrada(dpEntrada.getValue());
		entradaSeleccionada.setSortida(dpSortida.getValue());
		entradaSeleccionada.setObservacioEntradaTaller(txtIncidenciaAveria.getText().toUpperCase());
		entradaSeleccionada.setActuacioEntradaTaller(txtIncidenciaActuacio.getText().toUpperCase());
		if(imgIncidenciaImatge.getImage()!=null) {
			entradaSeleccionada.setImatgeIncidencia(convertirImatgeByetArray(imgIncidenciaImatge.getImage()));			
		}
		serveiTaller.actualitzarEntrada(entradaSeleccionada);
		GestorVistes.emplenarTableView(serveiTaller.llistaEntradesSegonsVehicle(vehiclePassat), twEntradesTaller);
	}
	
	@FXML
	public void eliminarEntrada() {
		serveiTaller.eliminarEntrada(entradaSeleccionada.getId());
		GestorVistes.emplenarTableView(serveiTaller.llistaEntradesSegonsVehicle(vehiclePassat), twEntradesTaller);
		netejarCamps();
		
	}
	
	private void netejarCamps() {
		txtIncidenciaId.setText("");
		dpEntrada.setValue(null);
		dpSortida.setValue(null);
		txtIncidenciaAveria.setText("");
		txtIncidenciaActuacio.setText("");
		imgIncidenciaImatge.setImage(null);
	}
	
	
	
	
	@FXML
	private void dobleClick() {
		int indexActual = twEntradesTaller.getSelectionModel().getSelectedIndex();
		
		if(indexActual==indexAnteriorSeleccionat) {
			entradaSeleccionada = twEntradesTaller.getSelectionModel().getSelectedItem();
			carregarDades(entradaSeleccionada);
		}else {
			indexAnteriorSeleccionat = indexActual;
		}
	}
	
	@FXML
	public void cercarImatge(MouseEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selcciona imatge");
		fileChooser.getExtensionFilters().addAll(
			new ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg")
				);
		imatge = fileChooser.showOpenDialog(btnAfegirImatge.getScene().getWindow());
		if(imatge!=null) {
			//agafem la ruta del file per generar una Imatge nova
			Image imatgeFinal = new Image("file:"+imatge.getAbsolutePath());
			guardarExtensio(imatge);
			//Posem la imatge al imgView
			imgIncidenciaImatge.setImage(imatgeFinal);
			
		}
		
	}
	
	//Guarda a la variabel l'extensio de l'iamtge carregada al imgView
	public void guardarExtensio(File file) {
		//agafem el Path de l'imatge
		extensioImg=imatge.getAbsolutePath();
		//Ens quedem només amb l'extensió
		extensioImg=extensioImg.substring(extensioImg.indexOf(".")+1);
	}
	
	private void carregarDades(EntradaTaller entrada) {
		txtIncidenciaId.setText(String.valueOf(entrada.getId()));
		dpEntrada.setValue(entrada.getEntrada());
		dpSortida.setValue(entrada.getSortida());
		txtIncidenciaAveria.setText(entrada.getObservacioEntradaTaller());
		txtIncidenciaActuacio.setText(entrada.getActuacioEntradaTaller());
		if(entrada.getImatgeIncidencia()==null || entrada.getImatgeIncidencia().equals("")) {
			imgIncidenciaImatge.setImage(null);
		}
		//CARREGUEM LA IMATGE DEL BYTE ARRAY A IMAGE I D'AQUÍ AL IMAGE VIEW
		try {
			BufferedImage bufferImage = ImageIO.read(new ByteArrayInputStream(entrada.getImatgeIncidencia()));
			Image image = SwingFXUtils.toFXImage(bufferImage, null);
			imgIncidenciaImatge.setImage(image);
			
		} catch (IOException e) {
			log.error("No hi ha imatge assignada a aquesta entrada");
		}
	}
	@FXML
	public void filtrarDates() {

		GestorVistes.emplenarTableView(serveiTaller.llistarEntradesSegonsData(dpEntrada.getValue(), dpSortida.getValue()), twEntradesTaller);
		
		
	}
	
	@FXML
	public void activarFiltres() {
		if(filtresActivats) {
			btnDates.setDisable(true);			
			filtresActivats=false;
		}else {
			btnDates.setDisable(false);
			filtresActivats=true;
			
		}
	}
	
	//CARREGAR INCIDENCIES DE UN VEHCILE AL TABLEVIEW
	
	@FXML
	public void initialize() {
		colIdEntrada = new TableColumn<>("ID");
		colIdVehicle = new TableColumn<>("VEHICLE");
		colNomClient = new TableColumn<EntradaTaller, String>("Nom Client");
		colEntrada = new TableColumn<>("Entrada");
		colSortida = new TableColumn<>("Sortida");
		colObseravioAveria = new TableColumn<>("INCIDÈNCIA");
		colActuacioAveria = new TableColumn<>("ACTUACIONS");
		
		colIdEntrada.setCellValueFactory(new PropertyValueFactory<>("id"));

		//In propertyValueFactory you can specify a getter (don't put full name with get only the word before get)
		//to retrive the value we want to show.
		colIdVehicle.setCellValueFactory(new PropertyValueFactory<>("idVehicle"));
		colNomClient.setCellValueFactory(new PropertyValueFactory<>("nomClient"));


		colEntrada.setCellValueFactory(new PropertyValueFactory<>("entrada"));
		colSortida.setCellValueFactory(new PropertyValueFactory<>("sortida"));
		colObseravioAveria.setCellValueFactory(new PropertyValueFactory<EntradaTaller, String>("observacioEntradaTaller"));
		colActuacioAveria.setCellValueFactory(new PropertyValueFactory<EntradaTaller, String>("actuacioEntradaTaller"));
		
		twEntradesTaller.getColumns().addAll(colIdEntrada,colIdVehicle,colNomClient,colEntrada,colSortida,colObseravioAveria,colActuacioAveria);
		
		GestorVistes.emplenarTableView(serveiTaller.llistarEntradesTaller(), twEntradesTaller);
		btnDates.setDisable(true);
	}

}
