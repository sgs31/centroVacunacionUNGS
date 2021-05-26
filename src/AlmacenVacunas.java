import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AlmacenVacunas {
	private Map<String, Integer> vacunasVencidas;
	private Map <String, LinkedList<Vacuna>> vacunas;
	private Map <String, LinkedList<Vacuna>> vacunasNoUsable;
	public AlmacenVacunas() {
		this.vacunasVencidas = new HashMap<String,Integer>();
		this.vacunas = new HashMap<String, LinkedList<Vacuna>>();
		vacunas.put("pfizer",new LinkedList<Vacuna>());
		vacunas.put("moderna",new LinkedList<Vacuna>());
		vacunas.put("sputnik",new LinkedList<Vacuna>());
		vacunas.put("sinopharm",new LinkedList<Vacuna>());
		vacunas.put("astrazeneca",new LinkedList<Vacuna>());
	}
	
	public void almacenarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		String nombVacuna = nombreVacuna.toLowerCase();
		if (vacunas.containsKey(nombVacuna)) {
			LinkedList<Vacuna> taux = vacunas.get(nombVacuna);
			if (cantidad > 0) {
				if (nombreVacuna == "pfizer") {
					agregarVacuna(new Pfizer(fechaIngreso), taux, cantidad);
				}
				if (nombreVacuna == "moderna") {
					agregarVacuna(new Moderna(fechaIngreso), taux, cantidad);
				}
				if (nombreVacuna == "sputnik") {
					agregarVacuna(new Sputnik(fechaIngreso), taux, cantidad);
				}
				if (nombreVacuna == "sinopharm") {
					agregarVacuna(new Sinopharm(fechaIngreso), taux, cantidad);
				}
				if (nombreVacuna == "astrazeneca") {
					agregarVacuna(new Astrazeneca(fechaIngreso), taux, cantidad);
				}
			} else {
				throw new RuntimeException("La cantidad de vacunas no puede ser negativa");
			}
		}else {
			throw new RuntimeException("La vacuna no se puede almacenar");
		}	
	}
	
	private void agregarVacuna(Vacuna vacuna, LinkedList<Vacuna> listaVacuna, int cantidad) {
		if(vacuna.estaVencida()==false) {
			for (int i = 0; i < cantidad; i++) {
					listaVacuna.add(vacuna);
			}
		}else {
			throw new RuntimeException("no se pueden agregar vacunas vencidas");
		}
    }
	
	public int getVacunasDisponibles() {
		return vacunas.size();
	}
	
	public int getVacunasDisponibles(String vacuna) {
		String nombvacuna = vacuna.toLowerCase();
		if(vacunas.containsKey(nombvacuna)) {
			return vacunas.get(nombvacuna).size();
		}else {
			throw new RuntimeException("No hay existencias de esa vacuna");
		}	
	}
	
	public void vacunasVencida(String nombreVacuna) {
		String nombVacuna= nombreVacuna.toLowerCase();
		if(vacunas.containsKey(nombVacuna)) {
			Integer aux= vacunasVencidas.get(nombVacuna) + 1;
			vacunasVencidas.replace(nombVacuna,vacunasVencidas.get(nombVacuna),aux);
			vacunas.get(nombVacuna).remove();
		}
	}
	
	public void asignarVacuna(String nombreVacuna) {
		String nombVacuna = nombreVacuna.toLowerCase();
			LinkedList<Vacuna> taux = vacunasNoUsable.get(nombVacuna);
                taux.add(vacunas.get(nombVacuna).removeFirst());
	}
	
	public Map<String, Integer> getVacunasVencidas() {
		return vacunasVencidas;
	}

}
