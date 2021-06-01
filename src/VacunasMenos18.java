
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
		
		Fecha nuevaFecha = super.getFechaIngreso();
		if (super.getNombreVacuna().equals("Pfizer")) {
			for (int i = 0; i <= 30; i++) {
				nuevaFecha.avanzarUnDia();
			}
		} else {
			for (int i = 0; i <= 60; i++) {
				nuevaFecha.avanzarUnDia();
			}
		}

		return !nuevaFecha.anterior(Fecha.hoy());
	}

}
