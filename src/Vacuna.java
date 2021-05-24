
public class Vacuna {

	RangoDeAplicacion rangoDeAplicacion;
	Date fechaExpiracion;
	int temperatura;

	public Vacuna(RangoDeAplicacion r, int t) {
		this.rangoDeAplicacion = r;
		this.temperatura = t;
	}

	public RangoDeAplicacion getRangoDeAplicacion() {
		return this.rangoDeAplicacion;
	}

	public int getTemperaturaAlmacenamiento() {
		return this.temperatura;
	}
}
