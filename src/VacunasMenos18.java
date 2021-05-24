
public class VacunasMenos18 extends Vacuna {

	public VacunasMenos18(RangoDeAplicacion r, int t, Fecha f) {
		super(r, t, f);
		// TODO Auto-generated constructor stub
	}
	public boolean vencimiento(Fecha f) {
		return false;
	}
}
