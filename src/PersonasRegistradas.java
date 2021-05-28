import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PersonasRegistradas {
	
	//private Map<Integer, Persona> personasRegistradas;
	private Map<Integer, String> personasVacunadas;
	private Map<OrdenDePrioridad, LinkedList <Persona>> personasRegistradas; 

	public PersonasRegistradas() {
		//this.personasRegistradas = new HashMap<Integer, Persona>();
		this.personasVacunadas = new HashMap<Integer, String>();
		this.personasRegistradas = new HashMap<OrdenDePrioridad, LinkedList<Persona>>();
		personasRegistradas.put(OrdenDePrioridad.PRIMERO, new LinkedList<Persona>());
		personasRegistradas.put(OrdenDePrioridad.SEGUNDO, new LinkedList<Persona>());
		personasRegistradas.put(OrdenDePrioridad.TERCERO, new LinkedList<Persona>());
		personasRegistradas.put(OrdenDePrioridad.CUARTO, new LinkedList<Persona>());
	}

	public void registrarPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		if (Fecha.diferenciaAnios(nacimiento, Fecha.hoy()) >= 18) {
			Persona aux = new Persona (dni,nacimiento,tienePadecimientos,esEmpleadoSalud);
			OrdenDePrioridad prioridad = aux.obtenerOrdenDePrioridad();
			personasRegistradas.get(prioridad).add(aux);
			//personasRegistradas.put(dni, new Persona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud));
		} else {
			throw new RuntimeException("Las personas menores a 18 años no pueden ser registradas.");
		}

	}
	public LinkedList<Integer> getPersonasEnEspera() {
		LinkedList <Integer> taux = new LinkedList<Integer>();
		//List<Integer> personasEnEspera = (List<Integer>) personasRegistradas.keySet();
		for (OrdenDePrioridad p: personasRegistradas.keySet() ) {
			for(int i=0; i<personasRegistradas.get(p).size();i++) {
				taux.add(personasRegistradas.get(p).get(0).getDni());
			}
			
		}
		return /*personasEnEspera;*/taux;
	}
	
	public Map<Integer, String> getPersonasVacunadas() {
		return personasVacunadas;
	}

}
