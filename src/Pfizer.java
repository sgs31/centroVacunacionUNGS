
public class Pfizer extends VacunasMenos18 {
	public Pfizer(Fecha f) {
		super(RangoDeAplicacion.MAYOR_SESENTA, -18, f);
	}
	@Override
	public boolean estaVencida() {
        Fecha nuevaFecha = super.getFechaIngreso();
        for (int i = 0; i <= 30; i++) {
            nuevaFecha.avanzarUnDia();
        }
        return nuevaFecha.posterior(Fecha.hoy());
    }
}
