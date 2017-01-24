package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.awt.Color;

public enum Status {
	EINS('1', "Frei auf Funk", Color.YELLOW, true), 
	ZWEI('2', "Einsatzbereit auf Wache", Color.GREEN, true), 
	DREI('3', "Einsatz übernommen / auf Anfahrt", Color.ORANGE, true),
	VIER('4', "Ankunft am Einsatzort", Color.RED, true),
	FUENF('5', "Sprechwunsch", Color.MAGENTA, true),
	SECHS('6', "nicht einsatzbereit", Color.GRAY, true),
	SIEBEN('7', "Patient aufgenommen", Color.CYAN, true),
	ACHT('8', "am Transportziel", Color.CYAN, true),	
	NEUN('9', "Notarzt aufgenommen", Color.CYAN, true),
	NULL('0', "NOTFALL!", Color.BLACK, true),
	C('C', "Melden zur Einsatzübernahme", Color.ORANGE, false),
	E('E', "Einrücken / Einsatzauftrag abbrechen", Color.RED, false),
	J('J', "Sprechaufforderung", Color.YELLOW, false);
	

	private char s;
	private String label;
	private Color color;
	private boolean sendFromVehicle;
	Status(char n, String label, Color color, boolean sendFromVehicle){
		this.s = n;
		this.label = label;
		this.color = color;
		this.setSendFromVehicle(sendFromVehicle);
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

	public boolean isSendFromVehicle() {
		return sendFromVehicle;
	}

	public void setSendFromVehicle(boolean sendFromVehicle) {
		this.sendFromVehicle = sendFromVehicle;
	}
	
}
