
public class VacunasMenos18 extends Vacuna {

	public VacunasMenos18(String n, Fecha f) {
		super(n, -18, f);
	}

	public RangoDeAplicacion getRangoDeAplicacion() {
		if (super.getNombreVacuna().equals("pfizer")) {
			return RangoDeAplicacion.MAYOR_SESENTA;
		} else {
			return RangoDeAplicacion.TODO_PUBLICO;
		}
	}

	public boolean estaVencida() {
		Fecha nuevaFecha = super.getFechaIngreso();
		if (super.getNombreVacuna().equals("pfizer")) {
			for (int i = 0; i <= 30; i++) {
				nuevaFecha.avanzarUnDia();
			}
		} else {
			for (int i = 0; i <= 60; i++) {
				nuevaFecha.avanzarUnDia();
			}
		}

		return nuevaFecha.posterior(Fecha.hoy());
	}

}
