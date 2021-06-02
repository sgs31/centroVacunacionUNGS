
public class VacunasMenos18 extends Vacuna {

	public VacunasMenos18(String n, Fecha f) {
		super(n, -18, f);
	}

	public RangoDeAplicacion getRangoDeAplicacion() {
		if (super.getNombreVacuna().equals("Pfizer")) {
			return RangoDeAplicacion.MAYOR_SESENTA;
		} else {
			return RangoDeAplicacion.TODO_PUBLICO;
		}
	}

	public boolean estaVencida() {
		Fecha ingreso = super.getFechaIngreso();
		Fecha fechaDeCaducidad = new Fecha(ingreso.dia(), ingreso.mes(), ingreso.anio());
		if (super.getNombreVacuna().equals("Pfizer")) {
			for (int i = 0; i <= 30; i++) {
				fechaDeCaducidad.avanzarUnDia();
			}
		}
		if(super.getNombreVacuna().equals("Moderna")){
			for (int i = 0; i <= 60; i++) {
				fechaDeCaducidad.avanzarUnDia();
			}
		}
		boolean ESTA_VENCIDA = fechaDeCaducidad.anterior(Fecha.hoy());
		return ESTA_VENCIDA;
	}
}
