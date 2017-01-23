package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;

public class EmergencyHandler extends Observable{
	private ArrayList<Emergency> emergencies;
	private VehicleHandler vh;
	private MapLoader ml;

	public EmergencyHandler(VehicleHandler vh, MapLoader ml){
		this.vh = vh;
		this.ml = ml;		
		this.emergencies = new ArrayList<Emergency>();
		
	}

	public void addEmergency(Emergency emergency) throws Exception {
		this.setChanged();
		handleEmergency(emergency);
		this.notifyObservers(emergency);
	}
	
	public void handleEmergency(Emergency emergency) throws Exception {
		emergencies.add(emergency);		

		ArrayList<Route> routes = ml.calcRadiusSearch(emergency.getGeoLocation().getOsmNodeId(), vh.vehicles);

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
		

		if(emergency.hasUnassignedTasks()) //Kein geeignetes Fahrzeug zur Verfügung zur Zeit
			throw new Exception("Kein oder nicht genügend geeignete Fahrzeuge verfügbar"); //TODO Warteliste

		System.out.println(emergency);		
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
