import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;

public class CentroVacunacion {

	private String nombreCentro;
	private int capacidadVacunacionDiaria;
	private AlmacenVacunas almacenVacunas;
	private PersonasRegistradas personasRegistradas;
	private Map<Fecha, LinkedList<Persona>> calendarioVacunacion;

	public CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		if (capacidadVacunacionDiaria < 0) {
			throw new RuntimeException("La capacidad diaria de vacunacion debe ser mayor a 0.");
		}
		this.nombreCentro = nombreCentro;
		this.capacidadVacunacionDiaria = capacidadVacunacionDiaria;
		this.almacenVacunas = new AlmacenVacunas();
		this.personasRegistradas = new PersonasRegistradas();
		this.calendarioVacunacion = new HashMap<Fecha, LinkedList<Persona>>();
	}

	/**
	 * Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte. Si
	 * el nombre de la vacuna no coincidiera con los especificados se debe generar
	 * una excepci�n. Tambi�n se genera excepci�n si la cantidad es negativa. La
	 * cantidad se debe sumar al stock existente, tomando en cuenta las vacunas ya
	 * utilizadas.
	 */
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		almacenVacunas.almacenarVacunas(nombreVacuna, cantidad, fechaIngreso);
	}

	/**
	 * total de vacunas disponibles no vencidas sin distinci�n por tipo.
	 */
	public int vacunasDisponibles() {
		return almacenVacunas.getVacunasDisponibles();
	}

	/**
	 * total de vacunas disponibles no vencidas que coincida con el nombre de vacuna
	 * especificado.
	 */
	public int vacunasDisponibles(String nombreVacuna) {
		return almacenVacunas.getVacunasDisponibles(nombreVacuna);
	}
	/**
	 * Se inscribe una persona en lista de espera. Si la persona ya se encuentra
	 * inscripta o es menor de 18 a�os, se debe generar una excepci�n. Si la persona
	 * ya fue vacunada, tambi�n debe generar una excepci�n.
	 */
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		personasRegistradas.registrarPersona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
	}

	/**
	 * Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron y
	 * que no tienen turno asignado. Si no quedan inscriptos sin vacunas debe
	 * devolver una lista vac�a.
	 */
	public List<Integer> listaDeEspera() {
		return personasRegistradas.getPersonasEnEspera();
	}

	/**
	 * Primero se verifica si hay turnos vencidos. En caso de haber turnos vencidos,
	 * la persona que no asisti� al turno debe ser borrada del sistema y la vacuna
	 * reservada debe volver a estar disponible. => SE A�ADE QUE LA VACUNA NO DEBE ESTAR VENCIDA TAMPOCO/
	 *
	 * Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	 *
	 * Por ultimo, se procede a asignar los turnos a partir de la fecha inicial
	 * recibida seg�n lo especificado en la 1ra parte. Cada vez que se registra un
	 * nuevo turno, la vacuna destinada a esa persona dejar� de estar disponible.
	 * Dado que estar� reservada para ser aplicada el d�a del turno.
	 */
	public void generarTurnos(Fecha fechaInicial) {

		eliminarTurnosVencidos();
		almacenVacunas.eliminarVacunasVencidas();

		Fecha fechaAux = new Fecha(fechaInicial.dia(), fechaInicial.mes(), fechaInicial.anio());

		if (fechaAux.anterior(Fecha.hoy())) {
			throw new RuntimeException("La fecha que ingreso es invalida.");
		}
		if (calendarioVacunacion.get(fechaAux) == null) {
			calendarioVacunacion.put(new Fecha(fechaAux.dia(), fechaAux.mes(), fechaAux.anio()), new LinkedList<Persona>());
		}
		while (puedaAsignarTurno(OrdenDePrioridad.PRIMERO)) {
			procesarFecha(fechaAux, OrdenDePrioridad.PRIMERO);
		}
		while (puedaAsignarTurno(OrdenDePrioridad.SEGUNDO)) {
			procesarFecha(fechaAux, OrdenDePrioridad.SEGUNDO);
		}
		while (puedaAsignarTurno(OrdenDePrioridad.TERCERO)) {
			procesarFecha(fechaAux, OrdenDePrioridad.TERCERO);

		}
		while (puedaAsignarTurno(OrdenDePrioridad.CUARTO)) {
			procesarFecha(fechaAux, OrdenDePrioridad.CUARTO);
		}
	}

	private void procesarFecha(Fecha fechaAux, OrdenDePrioridad prioridad) {
		final boolean capacidadDiariaNoCompleta = calendarioVacunacion.get(fechaAux).size() < capacidadVacunacionDiaria;
		if (capacidadDiariaNoCompleta) {
			LinkedList<Persona> inscriptosRef = calendarioVacunacion.get(fechaAux);
			Persona personaConTurnoAsignado = personasRegistradas.asignarTurno(prioridad);
			RangoDeAplicacion rangoDeAplicacion = personaConTurnoAsignado.obtenerRangoDeAplicacion();
			almacenVacunas.asignarVacuna(personaConTurnoAsignado.getDni(), rangoDeAplicacion);
			inscriptosRef.add(personaConTurnoAsignado);
		} else {
			fechaAux.avanzarUnDia();
			calendarioVacunacion.put(new Fecha(fechaAux.dia(), fechaAux.mes(), fechaAux.anio()), new LinkedList<Persona>());
		}
	}

	private boolean puedaAsignarTurno(OrdenDePrioridad o) {

		if (o == OrdenDePrioridad.PRIMERO) {
			return personasRegistradas.existePersonasEnEspera(o)
					&& almacenVacunas.existeVacunaConRangoDeAplicacion(RangoDeAplicacion.TODO_PUBLICO);
		}
		if (o == OrdenDePrioridad.SEGUNDO) {
			return personasRegistradas.existePersonasEnEspera(o)
					&& almacenVacunas.existeVacunaConRangoDeAplicacion(RangoDeAplicacion.MAYOR_SESENTA);
		}
		if (o == OrdenDePrioridad.TERCERO) {
			return personasRegistradas.existePersonasEnEspera(o)
					&& almacenVacunas.existeVacunaConRangoDeAplicacion(RangoDeAplicacion.TODO_PUBLICO);
		}
		if (o == OrdenDePrioridad.CUARTO) {
			return personasRegistradas.existePersonasEnEspera(o)
					&& almacenVacunas.existeVacunaConRangoDeAplicacion(RangoDeAplicacion.TODO_PUBLICO);
		}
		return false;
	}

	private void eliminarTurnosVencidos() {
		Set<Fecha> fechasVacunacion = calendarioVacunacion.keySet();
		LinkedList<Fecha> listadoFechasVencidas = new LinkedList<>();

		for (Fecha fecha : fechasVacunacion) {
			final boolean ES_TURNO_VENCIDO = fecha.anterior(Fecha.hoy());
			if (ES_TURNO_VENCIDO) {
				LinkedList<Persona> personasConTurnoVencido = calendarioVacunacion.get(fecha);
				almacenVacunas.restaurarVacunas(personasConTurnoVencido);
				listadoFechasVencidas.add(fecha);
			}
		}
		listadoFechasVencidas.forEach(fecha->{
			calendarioVacunacion.remove(fecha);
		});
	}

	/**
	 * Devuelve una lista con los dni de las personas que tienen turno asignado para
	 * la fecha pasada por par�metro. Si no hay turnos asignados para ese d�a, se
	 * debe devolver una lista vac�a. La cantidad de turnos no puede exceder la
	 * capacidad por d�a de la ungs.
	 */
	
	public List<Integer> turnosConFecha(Fecha fecha) {
		List<Integer> dniPersonas = new LinkedList<Integer>();
		if(calendarioVacunacion.get(fecha)==null) {
			return dniPersonas;
		}
		for (Persona persona : calendarioVacunacion.get(fecha)) {
			dniPersonas.add((Integer)persona.getDni());
		}
		return dniPersonas;
	}

	/**
	 * Dado el DNI de la persona y la fecha de vacunaci�n se valida que est�
	 * inscripto y que tenga turno para ese dia. - Si tiene turno y est� inscripto
	 * se debe registrar la persona como vacunada y la vacuna se quita del dep�sito.
	 * - Si no est� inscripto o no tiene turno ese d�a, se genera una Excepcion.
	 */
	
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		if (estaInscriptoConTurno(dni, fechaVacunacion)) {
			personasRegistradas.registrarPersonaVacunada(dni, almacenVacunas.getVacunaReservada(dni));
			almacenVacunas.removerVacunaReservada(dni);
			removerInscripto(dni, fechaVacunacion);
		} else {
			throw new RuntimeException(
					"No existe turnos con esa fecha de vacunacion o no existe turno que corresponda con ese dni.");
		}
	}
	
	public boolean estaInscriptoConTurno(int dni, Fecha fechaVacunacion) {
		boolean inscripto = false;
		if (calendarioVacunacion.get(fechaVacunacion) != null) {
			LinkedList<Persona> aux = calendarioVacunacion.get(fechaVacunacion);
			for (Persona p : aux) {
				inscripto = inscripto || p.getDni() == dni;
			}
		}
		return inscripto;
	}

	private void removerInscripto(int dni, Fecha fecha) {
		LinkedList<Persona> personas = calendarioVacunacion.get(fecha);
		Iterator<Persona> p = personas.iterator();
		while(p.hasNext()) {
			if (p.next().getDni() == dni) {
				p.remove();
			}
		}
	}
	/**
	 * Devuelve un Diccionario donde - la clave es el dni de las personas vacunadas
	 * - Y, el valor es el nombre de la vacuna aplicada.
	 */
	public Map<Integer, String> reporteVacunacion() {
		return personasRegistradas.getPersonasVacunadas();
	}

	/**
	 * Devuelve en O(1) un Diccionario: - clave: nombre de la vacuna - valor:
	 * cantidad de vacunas vencidas conocidas hasta el momento.
	 */
	public Map<String, Integer> reporteVacunasVencidas() {
		return almacenVacunas.getVacunasVencidas();
	}
	
	public String toString() {
		return nombreCentro;
	}

}
