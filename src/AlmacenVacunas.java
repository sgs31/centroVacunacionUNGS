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
		
		if (vacunaValida(vacuna)) {
			if (vacuna.equals("Pfizer") || vacuna.equals("Sputnik")) {
				return vacunas.get(RangoDeAplicacion.MAYOR_SESENTA).size();
			} else {
				return vacunas.get(RangoDeAplicacion.TODO_PUBLICO).size();
			}
		} else {
			throw new RuntimeException("No hay esa vacuna.");
		}
	}

	// cambiar nombre metodo ---!
	private void agregarVacunaVencida(String nombreVacuna) {
		
		if (vacunas.containsKey(nombreVacuna)) {
			Integer aux = vacunasVencidas.get(nombreVacuna) + 1;
			vacunasVencidas.replace(nombreVacuna, vacunasVencidas.get(nombreVacuna), aux);
			vacunas.get(nombreVacuna).remove();
		}
	}
	
	public void verificarVacunasVencidas() {
		LinkedList <Vacuna> vacunas60 = vacunas.get(RangoDeAplicacion.MAYOR_SESENTA);
		LinkedList <Vacuna> vacunasTodos = vacunas.get(RangoDeAplicacion.TODO_PUBLICO);
		//Set<RangoDeAplicacion> r = vacunas.keySet();
		Iterator<Vacuna> a = vacunas60.iterator();
		Iterator<Vacuna> b = vacunasTodos.iterator();
		while(a.hasNext()) {
			if(a.next().estaVencida()) {
				if(a.next().getNombreVacuna()==null) {
					vacunasVencidas.put(a.next().getNombreVacuna(),1);
				}
				else {
					agregarVacunaVencida(a.next().getNombreVacuna());
				}
			}

		}	
		while(b.hasNext()) {
				if(b.next().estaVencida()) {
					if(b.next().getNombreVacuna()==null) {
						vacunasVencidas.put(b.next().getNombreVacuna(),1);
					}
					else {
						agregarVacunaVencida(b.next().getNombreVacuna());
					}
				}
		}		
			//RangoDeAplicacion rangoDeAplicacion = (RangoDeAplicacion) b.next();
			/*for(Vacuna v : vacunas.get(b.next())) {
				if(v.estaVencida()) {
					if(vacunasVencidas.get(v.getNombreVacuna())==null) {
						vacunasVencidas.put(v.getNombreVacuna(), 1);
					}else {
						agregarVacunaVencida(v.getNombreVacuna());
					}
				}
			}*/
	}

	public void asignarVacuna(Integer dni, RangoDeAplicacion r) {
		vacunasReservadas.put(dni, vacunas.get(r).removeFirst());
	}

	public void restaurarVacunas(LinkedList<Persona> personas) {
		for (Persona p : personas) {
			Vacuna vacunaReservada = vacunasReservadas.get(p.getDni());
			if (vacunaReservada.estaVencida()) {
				agregarVacunaVencida(vacunaReservada.getNombreVacuna());
				removerVacunaReservada(p.getDni());
			} else {
				vacunas.get(vacunaReservada.getRangoDeAplicacion()).add(vacunaReservada);
			}
		}
	}

	public String getVacunaReservada(int dni) {
		if(vacunasReservadas.containsKey(dni)) {
			return vacunasReservadas.get(dni).getNombreVacuna();
		}
		else {
			return "";
		}
	}

	public void removerVacunaReservada(int dni) {
		vacunasReservadas.remove(dni);
	}

	public Map<String, Integer> getVacunasVencidas() {
		return vacunasVencidas;
	}

	// metodo que por ahi sirve
//	public String getVacuna(OrdenDePrioridad o) {
//		LinkedList<Vacuna> todoPublico = vacunas.get(RangoDeAplicacion.TODO_PUBLICO);
//		LinkedList<Vacuna> mayores60 = vacunas.get(RangoDeAplicacion.MAYOR_SESENTA);
//		String vacuna;
//		if (o == OrdenDePrioridad.SEGUNDO) {
//			vacuna = mayores60.getFirst().getNombreVacuna();
//		} else {
//			vacuna = todoPublico.getFirst().getNombreVacuna();
//		}
//		return vacuna;
//	}

	// viejo
	private boolean vacunaValida(String n) {
		boolean ret = false;
		if (n.equals("Pfizer") || n.equals("Moderna") || n.equals("Sputnik") || n.equals("AstraZeneca")|| n.equals("Sinopharm")) {
			ret = true;
		}
		return ret;
	}

	public boolean existeVacunaConRangoDeAplicacion(RangoDeAplicacion r) {
		return vacunas.get(r).size()>=1;
	}

}
