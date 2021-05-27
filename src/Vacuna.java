
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
}

