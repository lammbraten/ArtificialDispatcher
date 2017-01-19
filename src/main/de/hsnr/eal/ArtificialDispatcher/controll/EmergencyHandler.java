package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.Dijkstra;

public class EmergencyHandler extends Observable{
	private Set<Emergency> emergencies;
	private VehicleHandler vh;
	private MapLoader ml;

	public EmergencyHandler(VehicleHandler vh, MapLoader ml){
		this.vh = vh;
		this.ml = ml;		
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
		
		
		List<Route> routes = ml.calcRadiusSearch(emergency.getGeoLocation().getOsmNodeId(), vh.vehicles);
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
	
	
	
	
	

}
