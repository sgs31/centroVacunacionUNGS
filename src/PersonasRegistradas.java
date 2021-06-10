import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PersonasRegistradas {

	private Map<Integer, String> personasVacunadas;
	private Map<OrdenDePrioridad, LinkedList<Persona>> personasRegistradas;

	public PersonasRegistradas() {

		this.personasVacunadas = new HashMap<Integer, String>();
		this.personasRegistradas = new HashMap<OrdenDePrioridad, LinkedList<Persona>>();
		personasRegistradas.put(OrdenDePrioridad.PRIMERO, new LinkedList<Persona>());
		personasRegistradas.put(OrdenDePrioridad.SEGUNDO, new LinkedList<Persona>());
		personasRegistradas.put(OrdenDePrioridad.TERCERO, new LinkedList<Persona>());
		personasRegistradas.put(OrdenDePrioridad.CUARTO, new LinkedList<Persona>());
	}

	public void registrarPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		if (Math.abs(Fecha.diferenciaAnios(nacimiento, Fecha.hoy())) >= 18) {
			Persona aux = new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
			personasRegistradas.get(aux.obtenerOrdenDePrioridad()).add(aux);
		} else {
			throw new RuntimeException("Las personas menores a 18 años no pueden ser registradas.");
		}

	}
	public Persona asignarTurno(OrdenDePrioridad o) {
		return personasRegistradas.get(o).removeFirst();
	}
	
	public List<Integer> getPersonasEnEspera() {
		LinkedList<Integer> taux = new LinkedList<Integer>();
		for (OrdenDePrioridad p : personasRegistradas.keySet()) {
			LinkedList<Persona> personas = personasRegistradas.get(p);
			for (Persona per : personas) {
				taux.add(per.getDni());
			}
		}
		return taux;
	}
	
	public boolean existePersonasEnEspera(OrdenDePrioridad o) {
		return personasRegistradas.get(o).size()>=1;
	}

	public void registrarPersonaVacunada(int dni, String vacuna) {
		personasVacunadas.put(dni, vacuna);
	}

	public Map<Integer, String> getPersonasVacunadas() {
		return personasVacunadas;
	}

}
