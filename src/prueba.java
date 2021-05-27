import java.util.LinkedList;

public class prueba {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Fecha agustin = new Fecha(31,3,1995);
		Fecha juani = new Fecha(6, 6, 1995);
		LinkedList<Integer> algo = new LinkedList<Integer>();
		
		int num = 2;
		Integer numEnInteger = (Integer)num;
		
		
		System.out.println(Fecha.diferenciaAnios(agustin, Fecha.hoy()));
	}

}
