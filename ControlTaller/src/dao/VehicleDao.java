package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Persona;
import entity.Vehicle;

public interface VehicleDao {
	
	public List<Vehicle> llistarVehicles();
	public List<Vehicle> llistarVechilesPersona(Persona persona);
	public Vehicle selectVehicle(int id);
	public void eliminarVehicle(Vehicle vehicle);
	public void guardarVehicle(Vehicle vehicle);
	public void updateVehicle(Vehicle vehicle);
	

}
