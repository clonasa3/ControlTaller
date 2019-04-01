package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import entity.Persona;
import entity.Vehicle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class GestorVistes {
	//LOGGER
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenHeight = screenSize.height;
	int screenWidth = screenSize.width;
	
	private static GestorVistes gv=null;
	
	public enum Vistes{
		CRUDClients,
		CRUDTaller,
		CRUDVehicles,
		IniciTaller
	}

	public GestorVistes() {
	}
	
	//Singleton gestorVistes
	public static GestorVistes getGestorVista() {
		if(gv==null) {
			gv = new GestorVistes();
		}
		return gv;
	}
	
	
	
	//Metode per fer de refresh
	public static void emplenarTableView(List llista, TableView tableView) {
		ObservableList observableList = FXCollections.observableArrayList(llista);
		tableView.setItems(observableList);
		
	}
	
	//Metode per canviar les vistes
	public void canviarVista(javafx.event.ActionEvent event, String vista) {
		Parent parent;
		
		try {
			parent = FXMLLoader.load(getClass().getResource("/vistes/"+vista+".fxml"));
			Scene scene = new Scene(parent);
	        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        log.info("VISTA: " + vista);
	        appStage.setScene(scene);	        
	        appStage.show();
	        if(vista != "IniciTaller") {
	        	appStage.setX(0);
	        	appStage.setY(0);
	        	appStage.setWidth(screenWidth);
	        	appStage.setHeight(screenHeight);
	        }else {
	        	appStage.setWidth(600.00D);
	        	appStage.setHeight(250.00D);
	        }

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	/**
	 * Passem a una vista nova enviant un controlador 
	 * @param event
	 * @param vista
	 * @param objectePassat
	 */
	//Metode per canviar les vistes si té personaSeleccioanda
	public void canviarVista(javafx.event.ActionEvent event, String vista, Object objectePassat) {
		Parent parent;
		FXMLLoader loader;
		try {
			loader = new FXMLLoader(getClass().getResource("/vistes/"+vista+".fxml"));
			parent = (Parent)loader.load();
			//canviar per switch
			if(objectePassat instanceof Persona) {
				VehiclesController  vehicleController = loader.getController();
				vehicleController.setPersonaPassada((Persona)objectePassat);
			}else if(objectePassat instanceof Vehicle) {
				//Passem un vehicle com a parametre en el gestor de vistes
				//Carreguem taller amb un vehicle passat.
				TallerController tallerController = loader.getController();
				tallerController.setVehiclePassat((Vehicle)objectePassat);
			}			
	        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			Scene scene = new Scene(parent);
	        appStage.setScene(scene);
	        appStage.show();
        	appStage.setWidth(screenWidth);
        	appStage.setHeight(screenHeight);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}


}
