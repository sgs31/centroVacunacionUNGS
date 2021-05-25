import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class AlmacenVacunas {
	
	Map<String, Integer> vacunasVencidas;
	Map <String, LinkedList<Vacuna>> vacunas;
	public AlmacenVacunas() {
		this.vacunasVencidas = new HashMap<String,Integer>();
		this.vacunas = new HashMap<String, LinkedList<Vacuna>>();
		vacunas.put("pfizer",new LinkedList<Vacuna>());
		vacunas.put("moderna",new LinkedList<Vacuna>());
		vacunas.put("sputnik",new LinkedList<Vacuna>());
		vacunas.put("sinopharm",new LinkedList<Vacuna>());
		vacunas.put("astrazeneca",new LinkedList<Vacuna>());
	}
	
	
	public void almacenarVacunas(String nombreVacuna, int cantidad, Fecha fechaIngreso) {
		String nombVacuna = nombreVacuna.toLowerCase();
		LinkedList<Vacuna> taux = vacunas.get(nombVacuna);
		if (vacunas.containsKey(nombVacuna)) {
			if (cantidad > 0) {
				if (nombreVacuna == "pfizer" || nombreVacuna == "moderna") {
					Vacuna aux = new Vacuna(-18, fechaIngreso);
					taux.add(aux);
				} else {
					Vacuna aux = new Vacuna(3, fechaIngreso);
					taux.add(aux);
				}
			} else {
				throw new RuntimeException("La cantidad de vacunas no puede ser negativa");
			}
		}else {
			throw new RuntimeException("La vacuna no se puede almacenar");
		}	
	}

	public int getVacunasDisponibles() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public int getVacunasDisponibles(String vacuna) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, Integer> getVacunasVencidas() {
		// TODO Auto-generated method stub
		return null;
	}

}
