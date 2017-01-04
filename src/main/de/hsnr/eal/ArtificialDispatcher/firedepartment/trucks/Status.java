package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

public enum Status {
	EINS(1, "Frei auf Funk"), 
	ZWEI(2, "Einsatzbereit auf Wache"), 
	DREI(3, "Einsatz übernommen / auf Anfahrt"),
	VIER(4, "Ankunft am Einsatzort"),
	FUENF(5, "Sprechwunsch"),
	SECHS(6, "nicht einsatzbereit"),
	SIEBEN(7, "Patient aufgenommen"),
	ACHT(8, "am Transportziel"),	
	NEUN(9, "Notarzt aufgenommen"),
	NULL(0, "NOTFALL!");

	private int n;
	private String label;
	Status(int n, String label){
		this.n = n;
		this.label = label;
	}
	
	@Override
	public String toString(){
		return this.n +"";
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public int getNumber(){
		return n;
	}
	
}
