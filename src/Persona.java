
public class Persona {

	private int dni;
	private Fecha edad;
	private boolean trabajadorSalud;
	private boolean enfermedadPreexistente;

	public Persona(int dni, Fecha edad, boolean trabajadorSalud, boolean enfermedadPreexistente) {
		this.dni = dni;
		this.edad = edad;
		this.trabajadorSalud = trabajadorSalud;
		this.enfermedadPreexistente = enfermedadPreexistente;
	}

	public OrdenDePrioridad obtenerOrdenDePrioridad() {
		if(trabajadorSalud) {
			return OrdenDePrioridad.PRIMERO;
		}
		if(Fecha.diferenciaAnios(edad, Fecha.hoy()) >=60) {
			return OrdenDePrioridad.SEGUNDO;
		}
		if(enfermedadPreexistente) {
			return OrdenDePrioridad.TERCERO;
		}
		return OrdenDePrioridad.CUARTO;
	}
	
	public int getDni() {
		return dni;
	}

}
