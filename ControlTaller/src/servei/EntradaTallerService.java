package servei;

import java.time.LocalDate;
import java.util.List;

import entity.EntradaTaller;
import entity.Vehicle;

public interface EntradaTallerService {

	public List<EntradaTaller> llistarEntradesTaller();
	public List<EntradaTaller> llistaEntradesSegonsVehicle(Vehicle vehicle);
	public List<EntradaTaller> llistarEntradesSegonsData(LocalDate data1, LocalDate data2);
	public EntradaTaller selectEntradaTaller(int id);
	public void eliminarEntrada(int id);
	public void guardarEntrada(EntradaTaller entradaTaller);
	public void actualitzarEntrada(EntradaTaller entradaTaller);
}
