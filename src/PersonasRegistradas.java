import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PersonasRegistradas {

	private Map<Integer, Persona> personasRegistradas;
	private Map<Integer, String> personasVacunadas;

	public PersonasRegistradas() {
		this.personasRegistradas = new HashMap<Integer, Persona>();
		this.personasVacunadas = new HashMap<Integer, String>();
	}

	public void registrarPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		if (Fecha.diferenciaAnios(nacimiento, Fecha.hoy()) >= 18) {
			personasRegistradas.put(dni, new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud));
		} else {
			throw new RuntimeException("Las personas menores a 18 años no pueden ser registradas.");
		}

	}

	public List<Integer> getPersonasEnEspera() {
		List<Integer> personasEnEspera = (List<Integer>) personasRegistradas.keySet();
		return personasEnEspera;
	}

	public Map<Integer, String> getPersonasVacunadas() {
		return personasVacunadas;
	}

}
