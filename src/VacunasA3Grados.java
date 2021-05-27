
public class VacunasA3Grados extends Vacuna {

	public VacunasA3Grados(String n, Fecha f) {
		super(n, 3, f);
	}

	public RangoDeAplicacion getRangoDeAplicacion() {
		if (super.getNombreVacuna().equals("sputnik")) {
			return RangoDeAplicacion.MAYOR_SESENTA;
		} else {
			return RangoDeAplicacion.TODO_PUBLICO;
		}
	}

	public boolean estaVencida() {
		return false;
	}
}