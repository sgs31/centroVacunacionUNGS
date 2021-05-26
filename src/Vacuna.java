
public abstract class Vacuna {

	RangoDeAplicacion rangoDeAplicacion;
	Fecha fechaIngreso;
	int temperatura;
	
	public Vacuna(int t, Fecha f) {
		//this.rangoDeAplicacion;
		this.temperatura = t;
		this.fechaIngreso = f;
	}
	
	public  RangoDeAplicacion getRangoDeAplicacion() {
		return this.rangoDeAplicacion;
	}

	public  int getTemperaturaAlmacenamiento() {
		return this.temperatura;
	}
	public abstract boolean estaVencida();
	
	public Fecha getFechaIngreso() {
		return fechaIngreso;
	}
}
