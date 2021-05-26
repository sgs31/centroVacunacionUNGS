
public class Moderna extends VacunasMenos18 {
	public Moderna(Fecha f) {
		super(RangoDeAplicacion.TODO_PUBLICO, -18,f);
	}
	
	@Override
	public boolean estaVencida() {
        Fecha nuevaFecha = super.getFechaIngreso();
        for (int i = 0; i <= 60; i++) {
            nuevaFecha.avanzarUnDia();
        }
        return nuevaFecha.posterior(Fecha.hoy());
    }
}
