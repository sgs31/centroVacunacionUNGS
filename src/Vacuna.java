
public class Vacuna {

	RangoDeAplicacion rangoDeAplicacion;
	Fecha fechaIngreso;
	int temperatura;
	
	public Vacuna(RangoDeAplicacion r, int t, Fecha f) {
		this.rangoDeAplicacion = r;
		this.temperatura = t;
		this.fechaIngreso = f;
	}

	public RangoDeAplicacion getRangoDeAplicacion() {
		return this.rangoDeAplicacion;
	}

	public int getTemperaturaAlmacenamiento() {
		return this.temperatura;
	}
	
}
