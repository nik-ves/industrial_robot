package GA_2018200727;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ga_2018200727 {

	public static void main(String[] args) {
		System.out.println("Nikola Veselinovic, 2018200727");
		
		List<IndustrijskiRobot> lista = IndustrijskiRobot.load("ulaz.dat");
		
		double suma = 0.0;
		
		for (IndustrijskiRobot ir : lista) {
			suma += ir.getEfikasnost();
		}
		
		double srvr = suma / lista.size();
		
		for (int i=0; i<lista.size(); i++) {
			if (lista.get(i).getEfikasnost() * 1.25 < srvr ||
				lista.get(i).getEfikasnost() * 0.75 > srvr) {
				lista.remove(i);
				i--;
			}
		}
		
		try {
			PrintWriter pw = new PrintWriter("izlaz.txt");
			
			for (IndustrijskiRobot ir : lista) {
				pw.print(ir);
			}
			
			pw.flush();
			pw.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Nije moguce raditi sa izlaz.txt datotekom.");
		}
		
		
	}

}
