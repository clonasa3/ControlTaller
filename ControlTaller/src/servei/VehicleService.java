package servei;

import java.util.List;

import entity.Persona;
import entity.Vehicle;

public interface VehicleService {
	
	public List<Vehicle> llistarVehicles();
	public Vehicle selectVehicle(int id);
	public void eliminarVehicle(Vehicle vehicle);
	public void guardarVehicle(Vehicle vehicle);
	public void updateVehicle(Vehicle vehicle);

}
