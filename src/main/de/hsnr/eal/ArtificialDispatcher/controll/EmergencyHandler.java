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
		emergencies.add(emergency);		
		this.notifyObservers(emergency);
	}
	
	public Set<Emergency> getEmergencies(){
		return emergencies;
	}
	
	
	
	

}
