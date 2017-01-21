package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.Dijkstra;

public class EmergencyHandler extends Observable{
	private ArrayList<Emergency> emergencies;
	private VehicleHandler vh;
	private MapLoader ml;

	public EmergencyHandler(VehicleHandler vh, MapLoader ml){
		this.vh = vh;
		this.ml = ml;		
		this.emergencies = new ArrayList<Emergency>();
		
	}

	public void addEmergency(Emergency emergency) {
		this.setChanged();
		handleEmergency(emergency);
		this.notifyObservers(emergency);
	}
	
	public void handleEmergency(Emergency emergency) {
		emergencies.add(emergency);		

		List<EmergencyTask> todoTasks = emergency.getEmergencyType().getTasks();
		//get Gesammtübersicht (Aufgabe, Eingesetzte Fahrzeuge, Dauer)
		//get Einsatzfähige Fahrzeuge
		
		
		ArrayList<Route> routes = ml.calcRadiusSearch(emergency.getGeoLocation().getOsmNodeId(), vh.vehicles);
		
		//Map<Vehicle, double> vehicleArrivalTime can be at emergency in "double" minutes
		TreeMap<Vehicle, Double> arrival = new TreeMap<Vehicle, Double>();
		//for each vehicle
			//Find route in routes. (Abhängig vom Standort)
			//vehicleArrivalTime.
		//arrival.p		
		Routes:
		for(Route r : routes){
			for(Vehicle v : vh.getVehiclesOnPosition(r.getSartNodeId())){
				if(v.isAvailable()){
					alertVehicleIfHelpful(emergency, v, r);	
					if(!emergency.hasUnassignedTasks()) //All Tasks assigned -> emergency can be done.
						break Routes;	//Quick and dirty break;
				}
			}
		}

		

		System.out.println(emergency);
		
		//if Emergency unbehandelt
		//handelEmergency
			//Umkreissuche
			//if Fahrzeug found
			//for each vehicle:
				//if vehicle.canDo(emergency) && emergency.isHelpful(vehicle)
					//Prüfe Fahrzeugstatus
					//if frei
						//alarmieren (assign())
						//if emergency.canbedone()
							//break
					//else
						//prüfen ob und wann frei oder ob anderes Fahrzeug schneller.
							//1. Nicht Frei / zu lange -> verwerfen
							//2. eher fertig bevor anderes Fahrzeug anrücken kann -> warten


		
	}

	private void alertVehicleIfHelpful(Emergency emergency, Vehicle v, Route route) {
		EmergencyTask task = canDo(v, emergency);
		if(task != null){
			//v.assignTo(emergency, task);
			vh.alertVehicle(v, emergency, task, route);
		}
	}

	private EmergencyTask canDo(Vehicle v, Emergency emergency) {
		for(EmergencyTask t : emergency.getUnassingnedTasks())
			if(v.canDo(t))
				return t;
		return null;
	}

	public List<Emergency> getEmergencies(){
		return emergencies;
	}	
	
	
	
	
	

}
