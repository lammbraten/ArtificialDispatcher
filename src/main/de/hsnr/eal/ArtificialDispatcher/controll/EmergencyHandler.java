package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;

public class EmergencyHandler extends Observable{
	private Set<Emergency> emergencies;
	private VehicleHandler vh;

	public EmergencyHandler(VehicleHandler vh){
		this.vh = vh;
		
		this.emergencies = new TreeSet<Emergency>();
		
	}

	public void addEmergency(Emergency emergency) {
		this.setChanged();
		handleEmergency(emergency);
		this.notifyObservers(emergency);
	}
	
	public void handleEmergency(Emergency emergency) {
		emergencies.add(emergency);		

		// TODO Auto-generated method stub
		
		//get Gesammt�bersicht (Aufgabe, Eingesetzte Fahrzeuge, Dauer)
		//get Einsatzf�hige Fahrzeuge
		
		//if Emergency unbehandelt
		//handelEmergency
			//Umkreissuche
			//if Fahrzeug found
			//Pr�fe Fahrzeugstatus
			//if frei
				//alarmieren
			//else
				//pr�fen ob und wann frei oder ob anderes Fahrzeug schneller.
					//1. Nicht Frei / zu lange -> verwerfen
					//2. eher fertig bevor anderes Fahrzeug anr�cken kann -> warten
		
		
		
	}

	public Set<Emergency> getEmergencies(){
		return emergencies;
	}
	
	
	
	

}
