package controller;

import controller.GestorVistes.Vistes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class IniciTallerController {
	@FXML
	private Button btnIniciClient;
	
	@FXML
	private Button btnIniciVehicles;
	
	@FXML
	private Button btnIniciTaller;
	
	GestorVistes gv = GestorVistes.getGestorVista();
	
	public void obrirVistaClients(javafx.event.ActionEvent event) {
		gv.canviarVista(event, Vistes.CRUDClients.toString());
        
	}
	
	public void obrirVistaVehicles(javafx.event.ActionEvent event) {
		gv.canviarVista(event, Vistes.CRUDVehicles.toString());		
        
	}
	
	public void obrirVistaTaller(javafx.event.ActionEvent event) {
		gv.canviarVista(event, Vistes.CRUDTaller.toString());
	}
	

}
