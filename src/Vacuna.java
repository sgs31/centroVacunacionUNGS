
public abstract class Vacuna {

	private Fecha fechaIngreso;
	private int temperatura;
	private String nombreVacuna;

	public Vacuna(String n, int t, Fecha f) {
		this.nombreVacuna = n;
		this.temperatura = t;
		this.fechaIngreso = f;
	}

	public abstract RangoDeAplicacion getRangoDeAplicacion();

	public int getTemperaturaAlmacenamiento() {
		return this.temperatura;
	}

	public abstract boolean estaVencida();

	public Fecha getFechaIngreso() {
		return fechaIngreso;
	}

	public String getNombreVacuna() {
		return nombreVacuna;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fechaIngreso == null) ? 0 : fechaIngreso.hashCode());
		result = prime * result + ((nombreVacuna == null) ? 0 : nombreVacuna.hashCode());
		result = prime * result + temperatura;
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
		Vacuna other = (Vacuna) obj;
		if (fechaIngreso == null) {
			if (other.fechaIngreso != null)
				return false;
		} else if (!fechaIngreso.equals(other.fechaIngreso))
			return false;
		if (nombreVacuna == null) {
			if (other.nombreVacuna != null)
				return false;
		} else if (!nombreVacuna.equals(other.nombreVacuna))
			return false;
		if (temperatura != other.temperatura)
			return false;
		return true;
	}
}

