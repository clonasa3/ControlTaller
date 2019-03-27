package dao;

import java.time.LocalDate;
import java.util.List;

import entity.EntradaTaller;
import entity.Vehicle;

public interface EntradaTallerDao {
	
	public List<EntradaTaller> llistarEntradesTaller();
	public List<EntradaTaller> llistarEntradesVehcile(Vehicle vehicle);
	public List<EntradaTaller> llistarEntradesSegonsData(LocalDate data1, LocalDate data2);
	public EntradaTaller selectEntradaTaller(int id);
	public void updateEntradaTaller(EntradaTaller entrada);
	public void eliminarEntrada(int id);
	public void guardarEntrada(EntradaTaller entradaTaller);

}
