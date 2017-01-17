package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

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
		
		List<EmergencyTask> todoTasks = emergency.getEmergencyType().getTasks();
		//get Gesammtübersicht (Aufgabe, Eingesetzte Fahrzeuge, Dauer)
		//get Einsatzfähige Fahrzeuge
		
		//if Emergency unbehandelt
		//handelEmergency
			//Umkreissuche
			//if Fahrzeug found
			//for each vehicle:
				//if vehicle.canDo(emergency) && emergency.isHelpful(vehicle)
					//Prüfe Fahrzeugstatus
					//if frei
						//alarmieren (assign())
					//else
						//prüfen ob und wann frei oder ob anderes Fahrzeug schneller.
							//1. Nicht Frei / zu lange -> verwerfen
							//2. eher fertig bevor anderes Fahrzeug anrücken kann -> warten
		
		for(Vehicle v : emergency.getAssignedVehicles())
			v.alert();
		
	}

	public Set<Emergency> getEmergencies(){
		return emergencies;
	}
	

	
	private void assign(Vehicle v, Emergency e){
		e.addAssignedVehicle(v);
		v.setEmergency(e);

	}
	
	
	
	
	
	

}
