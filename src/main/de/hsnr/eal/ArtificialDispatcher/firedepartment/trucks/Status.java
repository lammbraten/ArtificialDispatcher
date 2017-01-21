package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.awt.Color;

public enum Status {
	EINS('1', "Frei auf Funk", Color.YELLOW), 
	ZWEI('2', "Einsatzbereit auf Wache", Color.GREEN), 
	DREI('3', "Einsatz übernommen / auf Anfahrt", Color.ORANGE),
	VIER('4', "Ankunft am Einsatzort", Color.RED),
	FUENF('5', "Sprechwunsch", Color.MAGENTA),
	SECHS('6', "nicht einsatzbereit", Color.GRAY),
	SIEBEN('7', "Patient aufgenommen", Color.CYAN),
	ACHT('8', "am Transportziel", Color.CYAN),	
	NEUN('9', "Notarzt aufgenommen", Color.CYAN),
	NULL('0', "NOTFALL!", Color.BLACK),
	C('C', "Melden zur Einsatzübernahme", Color.ORANGE),
	E('E', "Einrücken / Einsatzauftrag abbrechen", Color.RED),
	J('J', "Sprechaufforderung", Color.YELLOW);
	

	private char s;
	private String label;
	private Color color;
	Status(char n, String label, Color color){
		this.s = n;
		this.label = label;
		this.color = color;
	}
	
	@Override
	public String toString(){
		return this.s +"";
	}
	
	public String getLabel(){
		return this.label;
	}
	
	public int getNumber(){
		return s;
	}

	public Color getColor() {
		return color;
	}
	
	public static boolean isAvailable(Status s){
		if(s == Status.EINS || s == Status.ZWEI || s == Status.E)
			return true;
		return false;
	}
	
}
