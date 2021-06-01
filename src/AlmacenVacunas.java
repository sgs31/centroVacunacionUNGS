import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

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

		if (vacunaValida(nombreVacuna)) {
			if (cantidad >= 1) {
				if (nombreVacuna.equals("Pfizer") || nombreVacuna.equals("Moderna")) {
					agregarVacuna(new VacunasMenos18(nombreVacuna, fechaIngreso), cantidad);
				} else {
					agregarVacuna(new VacunasA3Grados(nombreVacuna, fechaIngreso), cantidad);
				}
			} else {
				throw new RuntimeException("La cantidad de vacunas para almacenar debe ser mayor o igual a 1.");
			}
		} else {
			throw new RuntimeException("La vacuna con esa marca no se puede almacenar.");
		}
	}
	
	private void agregarVacuna(Vacuna vacuna, int cantidad) {
		LinkedList<Vacuna> listaVacuna = vacunas.get(vacuna.getRangoDeAplicacion());
		for (int i = 0; i < cantidad; i++) {
			listaVacuna.add(vacuna);
		}
	}
	
	public int getVacunasDisponibles() {
		int total = 0;
		Set<RangoDeAplicacion> r = vacunas.keySet();
		for (RangoDeAplicacion rangoApp : r) {
			total += vacunas.get(rangoApp).size();
		}
		return total;
	}
	
	public int getVacunasDisponibles(String vacuna) {
		int total = 0;
		if (vacunaValida(vacuna)) {
			if (vacuna.equals("Pfizer") || vacuna.equals("Sputnik")) {
				for(Vacuna vac : vacunas.get(RangoDeAplicacion.MAYOR_SESENTA)) {
					if(vac.getNombreVacuna().equals(vacuna)) {
						total+=1;
					}
				}
			} else {
				for(Vacuna vac : vacunas.get(RangoDeAplicacion.TODO_PUBLICO)) {
					if(vac.getNombreVacuna().equals(vacuna)) {
						total+=1;
					}
				}
			}
		return total;
		} else {
			throw new RuntimeException("No hay esa vacuna.");
		}
	}

	
	private void agregarVacunaVencida(Vacuna vac) {
		String nVac = vac.getNombreVacuna();
		if (vacunasVencidas.get(nVac) == null) {
			vacunasVencidas.put(nVac, 1);
		} else {
			Integer aux = vacunasVencidas.get(nVac) + 1;
			vacunasVencidas.replace(nVac, vacunasVencidas.get(nVac), aux);
			vacunas.get(vac.getRangoDeAplicacion()).remove(vac);
		}
	}

	public void verificarVacunasVencidas() {
		Set<RangoDeAplicacion> r = vacunas.keySet();
		for (RangoDeAplicacion rangoApp : r) {
			LinkedList<Vacuna> vacunasConRangoApp = vacunas.get(rangoApp);
			for(Vacuna vac : vacunasConRangoApp ) {
				if(vac.estaVencida()) {
					agregarVacunaVencida(vac);
				}
			}
		}	
	}

	public void asignarVacuna(Integer dni, RangoDeAplicacion r) {
		vacunasReservadas.put(dni, vacunas.get(r).removeFirst());
	}

	public void restaurarVacunas(LinkedList<Persona> personas) {
		for (Persona p : personas) {
			Vacuna vacunaReservada = vacunasReservadas.get(p.getDni());
			if (vacunaReservada.estaVencida()) {
				agregarVacunaVencida(vacunaReservada);
				removerVacunaReservada(p.getDni());
			} else {
				vacunas.get(vacunaReservada.getRangoDeAplicacion()).add(vacunaReservada);
			}
		}
	}

	public String getVacunaReservada(int dni) {
		if (vacunasReservadas.containsKey(dni)) {
			return vacunasReservadas.get(dni).getNombreVacuna();
		} else {
			return "";
		}
	}

	public void removerVacunaReservada(int dni) {
		vacunasReservadas.remove(dni);
	}

	public Map<String, Integer> getVacunasVencidas() {
		return vacunasVencidas;
	}

	private boolean vacunaValida(String n) {
		boolean ret = false;
		if (n.equals("Pfizer") || n.equals("Moderna") || n.equals("Sputnik") || n.equals("AstraZeneca")
				|| n.equals("Sinopharm")) {
			ret = true;
		}
		return ret;
	}

	public boolean existeVacunaConRangoDeAplicacion(RangoDeAplicacion r) {
		return vacunas.get(r).size() >= 1;
	}

}
