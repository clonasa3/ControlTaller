package servei;

import java.util.ArrayList;
import java.util.List;

import dao.VehicleDaoImpl;
import entity.Persona;
import entity.Vehicle;

public class VehicleServiceImpl implements VehicleService{
	private VehicleDaoImpl vDaoImpl = new VehicleDaoImpl(); 

	public VehicleServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Vehicle> llistarVehicles() {
		// TODO Auto-generated method stub
		return vDaoImpl.llistarVehicles();
	}

	@Override
	public Vehicle selectVehicle(int id) {
		// TODO Auto-generated method stub
		return vDaoImpl.selectVehicle(id);
	}

	@Override
	public void eliminarVehicle(Vehicle vehicle) {
		// TODO Auto-generated method stub
		vDaoImpl.eliminarVehicle(vehicle);
	}

	@Override
	public void guardarVehicle(Vehicle vehicle) {
		// TODO Auto-generated method stub
		vDaoImpl.guardarVehicle(vehicle);
	}

	public void updateVehicle(Vehicle vehicle) {
		vDaoImpl.updateVehicle(vehicle);
	}

}
