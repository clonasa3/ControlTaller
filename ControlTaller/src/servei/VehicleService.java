package servei;

import java.util.List;

import entity.Persona;
import entity.Vehicle;

public interface VehicleService {
	
	public List<Vehicle> llistarVehicles();
	public Vehicle selectVehicle(int id);
	public List<Vehicle> llistarVehiclesModelAndMarcaAndId(String model, String marca, int id, String identificadorVehicle);
	public List<Vehicle> llistarVehiclesModelAndMarcaAndIdentificador(String model, String marca, String identificadorVehicle);
	public void eliminarVehicle(Vehicle vehicle);
	public void guardarVehicle(Vehicle vehicle);
	public void updateVehicle(Vehicle vehicle);

}
