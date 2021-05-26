import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CentroVacunacion {

	String nombreCentro;
	int capacidadVacunacionDiaria;
	AlmacenVacunas almacenVacunas;
	PersonasRegistradas personasRegistradas;
	Map<Fecha, LinkedList<Persona>> calendarioVacunacion;

	public CentroVacunacion(String nombreCentro, int capacidadVacunacionDiaria) {
		if(capacidadVacunacionDiaria < 0) {
			throw new RuntimeException("La capacidad diaria de vacunacion debe ser mayor a 0.");
		}
		this.nombreCentro = nombreCentro;
		this.capacidadVacunacionDiaria = capacidadVacunacionDiaria;
		this.almacenVacunas = new AlmacenVacunas();
		this.personasRegistradas = new PersonasRegistradas();
	}

	/**
	 * Solo se pueden ingresar los tipos de vacunas planteados en la 1ra parte. Si
	 * el nombre de la vacuna no coincidiera con los especificados se debe generar
	 * una excepción. También se genera excepción si la cantidad es negativa. La
	 * cantidad se debe sumar al stock existente, tomando en cuenta las vacunas ya
	 * utilizadas.
	 */
	public void ingresarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		almacenVacunas.almacenarVacunas(nombreVacuna, cantidad, fechaIngreso);
		
	}

	/**
	 * total de vacunas disponibles no vencidas sin distinción por tipo.
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
	 * inscripta o es menor de 18 años, se debe generar una excepción. Si la persona
	 * ya fue vacunada, también debe generar una excepción.
	 */
	public void inscribirPersona(int dni, Fecha nacimiento, boolean tienePadecimientos, boolean esEmpleadoSalud) {
		personasRegistradas.registrarPersona(dni, nacimiento, tienePadecimientos, esEmpleadoSalud);
	}

	/**
	 * Devuelve una lista con los DNI de todos los inscriptos que no se vacunaron y
	 * que no tienen turno asignado. Si no quedan inscriptos sin vacunas debe
	 * devolver una lista vacía.
	 */
	public List<Integer> listaDeEspera() {
		return personasRegistradas.getPersonasEnEspera();
	}

	/**
	 * Primero se verifica si hay turnos vencidos. En caso de haber turnos vencidos,
	 * la persona que no asistió al turno debe ser borrada del sistema y la vacuna
	 * reservada debe volver a estar disponible.
	 *
	 * Segundo, se deben verificar si hay vacunas vencidas y quitarlas del sistema.
	 *
	 * Por último, se procede a asignar los turnos a partir de la fecha inicial
	 * recibida según lo especificado en la 1ra parte. Cada vez que se registra un
	 * nuevo turno, la vacuna destinada a esa persona dejará de estar disponible.
	 * Dado que estará reservada para ser aplicada el día del turno.
	 *
	 *
	 */
	public void generarTurnos(Fecha fechaInicial) {
			
	}

	/**
	 * Devuelve una lista con los dni de las personas que tienen turno asignado para
	 * la fecha pasada por parámetro. Si no hay turnos asignados para ese día, se
	 * debe devolver una lista vacía. La cantidad de turnos no puede exceder la
	 * capacidad por día de la ungs.
	 */
	public List<Integer> turnosConFecha(Fecha fecha) {
		List<Integer> personas = new LinkedList<>();
		if(!calendarioVacunacion.containsKey(fecha)) {
			return personas;
		}
		for(Persona persona : calendarioVacunacion.get(fecha)) {
			personas.add(persona.getDni());
		}
		return personas;
	}

	/**
	 * Dado el DNI de la persona y la fecha de vacunación se valida que esté
	 * inscripto y que tenga turno para ese dia. - Si tiene turno y está inscripto
	 * se debe registrar la persona como vacunada y la vacuna se quita del depósito.
	 * - Si no está inscripto o no tiene turno ese día, se genera una Excepcion.
	 */
	public void vacunarInscripto(int dni, Fecha fechaVacunacion) {
		
	}

	/**
	 * Devuelve un Diccionario donde - la clave es el dni de las personas vacunadas
	 * - Y, el valor es el nombre de la vacuna aplicada.
	 */
	public Map<String, Integer> reporteVacunacion() {
		return almacenVacunas.getVacunasVencidas();

	}

	/**
	 * Devuelve en O(1) un Diccionario: - clave: nombre de la vacuna - valor:
	 * cantidad de vacunas vencidas conocidas hasta el momento.
	 */
	public Map<String, Integer> reporteVacunasVencidas() {
		return almacenVacunas.getVacunasVencidas();
	}

}
