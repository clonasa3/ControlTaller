package servei;

import java.time.LocalDate;
import java.util.List;

import dao.EntradaTallerDaoImpl;
import entity.EntradaTaller;
import entity.Vehicle;

public class EntradaTallerServiceImpl implements EntradaTallerService {
	
	private EntradaTallerDaoImpl etdi= new EntradaTallerDaoImpl();

	public EntradaTallerServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<EntradaTaller> llistarEntradesTaller() {
		// TODO Auto-generated method stub
		return etdi.llistarEntradesTaller();
	}
	
	@Override
	public List<EntradaTaller> llistaEntradesSegonsVehicle(Vehicle vehicle){
		return etdi.llistarEntradesVehcile(vehicle);
	}
	
	@Override
	public EntradaTaller selectEntradaTaller(int id) {
		// TODO Auto-generated method stub
		return etdi.selectEntradaTaller(id);
	}

	@Override
	public void eliminarEntrada(int id) {
		// TODO Auto-generated method stub
		etdi.eliminarEntrada(id);
		
	}

	@Override
	public void guardarEntrada(EntradaTaller entradaTaller) {
		// TODO Auto-generated method stub
		etdi.guardarEntrada(entradaTaller);
	}
	
	@Override
	public void actualitzarEntrada(EntradaTaller entrada) {
		etdi.updateEntradaTaller(entrada);
	}
	
	public List<EntradaTaller> llistarEntradesSegonsData(LocalDate data1, LocalDate data2){
		return etdi.llistarEntradesSegonsData(data1, data2);
	}

}
