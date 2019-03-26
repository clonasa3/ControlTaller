package servei;

import java.util.ArrayList;
import java.util.List;

import dao.VehicleDaoImpl;
import entity.Persona;
import entity.Vehicle;

public class VehicleServiceImpl implements VehicleService{
	private VehicleDaoImpl vDaoImpl = new VehicleDaoImpl(); 

	public VehicleServiceImpl() {

	}

	@Override
	public List<Vehicle> llistarVehicles() {

		return vDaoImpl.llistarVehicles();
	}

	@Override
	public Vehicle selectVehicle(int id) {

		return vDaoImpl.selectVehicle(id);
	}

	@Override
	public void eliminarVehicle(Vehicle vehicle) {

		vDaoImpl.eliminarVehicle(vehicle);
	}

	@Override
	public void guardarVehicle(Vehicle vehicle) {

		vDaoImpl.guardarVehicle(vehicle);
	}

	public void updateVehicle(Vehicle vehicle) {
		vDaoImpl.updateVehicle(vehicle);
	}

	@Override
	public List<Vehicle> llistarVehiclesModelAndMarcaAndId(String model, String marca, int id, String identificadorVehicle) {

		return vDaoImpl.llistarVehiclesModelAndMarcaAndId(model, marca, id, identificadorVehicle);
	}
	
	@Override
	public List<Vehicle> llistarVehiclesModelAndMarcaAndIdentificador(String model, String marca, String identificadorVehicle) {

		return vDaoImpl.llistarVehiclesModelAndMarcaAndIdentificador(model, marca, identificadorVehicle);
	}

}
