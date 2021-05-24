
public class Persona {

	int dni;
	int edad;
	boolean trabajadorSalud;
	boolean enfermedadPreexistente;

	public Persona(int dni, int edad, boolean trabajadorSalud, boolean enfermedadPreexistente) {
		if(edad<18) {
			throw new RuntimeException("Las personas menores a 18 años no pueden ser registradas.");
		}
		this.dni = dni;
		this.edad = edad;
		this.trabajadorSalud = trabajadorSalud;
		this.enfermedadPreexistente = enfermedadPreexistente;
	}

	public OrdenDePrioridad obtenerOrdenDePrioridad() {
		if(trabajadorSalud) {
			return OrdenDePrioridad.PRIMERO;
		}
		if(edad>60) {
			return OrdenDePrioridad.SEGUNDO;
		}
		if(enfermedadPreexistente) {
			return OrdenDePrioridad.TERCERO;
		}
		return OrdenDePrioridad.CUARTO;
	}

}
