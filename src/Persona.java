
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dni;
		result = prime * result + ((edad == null) ? 0 : edad.hashCode());
		result = prime * result + (enfermedadPreexistente ? 1231 : 1237);
		result = prime * result + (trabajadorSalud ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Persona other = (Persona) obj;
		if (dni != other.dni)
			return false;
		if (edad == null) {
			if (other.edad != null)
				return false;
		} else if (!edad.equals(other.edad))
			return false;
		if (enfermedadPreexistente != other.enfermedadPreexistente)
			return false;
		if (trabajadorSalud != other.trabajadorSalud)
			return false;
		return true;
	}

}
