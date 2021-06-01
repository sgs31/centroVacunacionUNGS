import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class prueba {

	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map<Integer, LinkedList<String>> asd = new HashMap<Integer,LinkedList<String>>();
		
		asd.put(1, new LinkedList<String>());
		asd.get(1).add("Agustin");
		asd.get(1).add("Agustin2");
		asd.get(1).add("Agustin3");
		
		LinkedList<String> aux = asd.get(1);
		
		for(String a : aux) {
			System.out.println(a);
		}
		
		
		

 }
}
