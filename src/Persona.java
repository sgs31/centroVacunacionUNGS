
public class Persona {
	
	int dni;
	int edad;
	boolean trabajadorSalud;
	boolean enfermedadPreexistente;
	
	public Persona(int dni, int edad, boolean trabajadorSalud, boolean enfermedadPreexistente) {
		this.dni = dni;
		this.edad = edad;
		this.trabajadorSalud = trabajadorSalud;
		this.enfermedadPreexistente = enfermedadPreexistente;
	}

	public OrdenDePrioridad obtenerOrdenDePrioridad() {
		
	}

}
