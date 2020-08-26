package GA_2018200727;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IndustrijskiRobot {
	public enum VrstaPogona {
		MAGNETNI_LINEARNI_AKTUATOR,
		ELEKTROMOTORNI_POGON,
		HIDRAULICNI_POGON
	}
	
	private String proizvodjac, model;
	private long godina, stepeniSlobodeKretanja, maxBrojSati;
	private double potrosnja;
	private VrstaPogona vrstaPogona;
	
	public String getProizvodjac() {
		return proizvodjac;
	}

	public String getModel() {
		return model;
	}

	public long getGodina() {
		return godina;
	}

	public long getStepeniSlobodeKretanja() {
		return stepeniSlobodeKretanja;
	}

	public long getMaxBrojSati() {
		return maxBrojSati;
	}

	public double getPotrosnja() {
		return potrosnja;
	}

	public VrstaPogona getVrstaPogona() {
		return vrstaPogona;
	}

	
	public IndustrijskiRobot(String proizvodjac, String model, long godina, long stepeniSlobodeKretanja,
			long maxBrojSatiRada, double potrosnja, VrstaPogona vrstaPogona) {
		this.proizvodjac = proizvodjac;
		this.model = model;
		this.godina = godina;
		this.stepeniSlobodeKretanja = stepeniSlobodeKretanja;
		this.maxBrojSati = maxBrojSati;
		this.potrosnja = potrosnja;
		this.vrstaPogona = vrstaPogona;
	}
	
	public double getEfikasnost() {
		double L;
		
		if ( this.vrstaPogona == VrstaPogona.MAGNETNI_LINEARNI_AKTUATOR) {
			L = 2.0;
		} else if ( this.vrstaPogona == VrstaPogona.ELEKTROMOTORNI_POGON) {
			L = 1.0;
		} else {
			L = 0.33;
		}
		
		return this.potrosnja * Math.sqrt(this.maxBrojSati) / 
			 ( this.stepeniSlobodeKretanja - 2 ) * L;
		
	}
	
	@Override
	public String toString() {
		String zaPrikaz = "";
		
		zaPrikaz += String.format("%20s%20s%30s%9.2f W\n", "Naziv proizvodjaca:", this.proizvodjac, "Potrosnja energije:", this.potrosnja);
		
		zaPrikaz += String.format("%20s%20s%30s%6d sati\n", "Naziv modela:", this.model, "Maks. broj sati rada:", this.maxBrojSati);
		
		zaPrikaz += String.format("%20s%19d %30s %6d sslk\n", "Godina proizvodnje", this.godina, "Broj stepeni slobode kretanja:", this.stepeniSlobodeKretanja);
		
		String pogon = "";
		
		if ( this.vrstaPogona == VrstaPogona.MAGNETNI_LINEARNI_AKTUATOR) {
			pogon = "Magnetni linearni aktuator";
		} else if ( this.vrstaPogona == VrstaPogona.ELEKTROMOTORNI_POGON) {
			pogon = "Elektromotorni pogon";
		} else {
			pogon = "Hidraulicni pogon";
		}
		
		zaPrikaz += String.format("%7s%33s%30s%9.2f #\n", "Pogon:", pogon, "Efikasnost:", this.getEfikasnost());
		
		return zaPrikaz;
	}
	
	public static List<IndustrijskiRobot> load(String imeDatoteke) {
		List<IndustrijskiRobot> lista = new ArrayList<>();
		
		try {
			FileInputStream fis = new FileInputStream(imeDatoteke);
			Scanner s = new Scanner(fis);
			
			while (s.hasNext()) {
				double potrosnja = s.nextDouble();
				long sati = s.nextLong();
				long godina = s.nextLong();
				String proizvodjac = s.nextLine().trim();
				long sslk = s.nextLong();
				String pogonAkronm = s.next().trim();
				String model = s.nextLine().trim();
				
				VrstaPogona vp = null;
				
				if (pogonAkronm.equals("MLA")) {
					vp = VrstaPogona.MAGNETNI_LINEARNI_AKTUATOR;
				} else if (pogonAkronm.equals("EMP")) {
					vp = VrstaPogona.ELEKTROMOTORNI_POGON;
				} else {
					vp = VrstaPogona.HIDRAULICNI_POGON;
				}
				
				IndustrijskiRobot ir = new IndustrijskiRobot(proizvodjac, model, godina, sslk, sati, potrosnja, vp);
				
				lista.add(ir);
			}
			
			s.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Doslo je do greske prilikom rada sa datotekom.");
		}

		return lista;
	}
	

}
