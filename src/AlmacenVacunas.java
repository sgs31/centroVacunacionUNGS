import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AlmacenVacunas {

	private Map<String, Integer> vacunasVencidas;
	private Map<RangoDeAplicacion, LinkedList<Vacuna>> vacunas;
	private Map<Integer, Vacuna> vacunasReservadas;

	public AlmacenVacunas() {
		this.vacunasVencidas = new HashMap<String, Integer>();
		this.vacunas = new HashMap<RangoDeAplicacion, LinkedList<Vacuna>>();
		this.vacunasReservadas = new HashMap<Integer, Vacuna>();
		vacunas.put(RangoDeAplicacion.MAYOR_SESENTA, new LinkedList<Vacuna>());
		vacunas.put(RangoDeAplicacion.TODO_PUBLICO, new LinkedList<Vacuna>());
	}

	public void almacenarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		String nombVacuna = nombreVacuna.toLowerCase();
		if (vacunaValida(nombVacuna)) {
			if (cantidad > 0) {
				if (nombreVacuna.equals("pfizer") || nombreVacuna.equals("moderna")) {
					agregarVacuna(new VacunasMenos18(nombreVacuna, fechaIngreso), cantidad);
				} else {
					agregarVacuna(new VacunasA3Grados(nombreVacuna, fechaIngreso), cantidad);
				}
			} else {
				throw new RuntimeException("La cantidad de vacunas no puede ser negativa");
			}
		} else {
			throw new RuntimeException("La vacuna no se puede almacenar");
		}
	}

	private void agregarVacuna(Vacuna vacuna, int cantidad) {
		LinkedList<Vacuna> listaVacuna = vacunas.get(vacuna.getRangoDeAplicacion());
		if (!vacuna.estaVencida()) {
			for (int i = 0; i < cantidad; i++) {
				listaVacuna.add(vacuna);
			}
		} else {
			throw new RuntimeException("no se pueden agregar vacunas vencidas");
		}
	}

	public int getVacunasDisponibles() {
		return vacunas.size();
	}

	public int getVacunasDisponibles(String vacuna) {
		String nombvacuna = vacuna.toLowerCase();
		if (vacunas.containsKey(nombvacuna)) {
			return vacunas.get(nombvacuna).size();
		} else {
			throw new RuntimeException("No hay existencias de esa vacuna");
		}
	}

	public void vacunasVencida(String nombreVacuna) {
		String nombVacuna = nombreVacuna.toLowerCase();
		if (vacunas.containsKey(nombVacuna)) {
			Integer aux = vacunasVencidas.get(nombVacuna) + 1;
			vacunasVencidas.replace(nombVacuna, vacunasVencidas.get(nombVacuna), aux);
			vacunas.get(nombVacuna).remove();
		}
	}

	public void asignarVacuna(Integer dni, String vacuna) {
		String nombVacuna = vacuna.toLowerCase();
		vacunasReservadas.put(dni, vacunas.get(nombVacuna).removeFirst());
	}

	public void restaurarVacunas(LinkedList<Persona> personas) {
		for (Persona p : personas) {
			Vacuna vacunaReservada = vacunasReservadas.get(p.getDni());
			if (vacunaReservada.estaVencida()) {
				vacunasReservadas.remove(p.getDni());
			} else {
				vacunas.get(vacunaReservada.getRangoDeAplicacion()).add(vacunaReservada);
			}
		}
	}

	public Map<String, Integer> getVacunasVencidas() {
		return vacunasVencidas;
	}

	private boolean vacunaValida(String n) {
		boolean ret = false;
		if (n.equals("pfizer") || n.equals("moderna") || n.equals("sputnik") || n.equals("astrazeneca")
				|| n.equals("sinopharm")) {
			ret = true;
		}
		return ret;
	}

}
