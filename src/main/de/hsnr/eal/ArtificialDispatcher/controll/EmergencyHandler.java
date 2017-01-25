package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Status;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;

public class EmergencyHandler extends Observable{
	private ArrayList<Emergency> emergencies;
	private VehicleHandler vh;
	private MapLoader ml;
	private TickEngine te;

	public EmergencyHandler(VehicleHandler vh, MapLoader ml, TickEngine te){
		this.vh = vh;
		this.ml = ml;		
		this.emergencies = new ArrayList<Emergency>();
		this.te = te;
	}

	public void addEmergency(Emergency emergency) throws Exception {
		this.setChanged();
		emergency.setStartTime(te.tick);
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

		//System.out.println(emergency);		
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

	public void newTick() {
		LinkedList<Emergency> toRemove = new LinkedList<Emergency>();
		for(Emergency e : this.emergencies){
			if(e.getUnfinishedTasks().isEmpty() && e.getAssignedVehicles().isEmpty())
				toRemove.add(e);
			else if(!e.getAssignedVehicles().isEmpty()){
				for(Vehicle v : e.getAssignedVehicles())
					if(v.isAtTarget())
						doTasks(v, e);
			}
		}

		
		deleteGarbae(toRemove);
			
		
	}

	private void deleteGarbae(LinkedList<Emergency> toRemove) {
		for(Emergency e : toRemove)
			emergencies.remove(e);
	}

	private void doTasks(Vehicle v, Emergency e) {
		if(v.allTasksFinished())
			sendBackToStation(v);
		else{
			for(EmergencyTask t : v.getAssignedTasks()){
				if(t.canStart()){
					t.start(te.tick);
					
				}
				if(t.canFinish(te.tick))
					t.finish();
			}
		}
		
	}


	

	private void sendBackToStation(Vehicle v) {
		v.setRoute(calcRouteToHomeStation(v));
		v.getRoute().invertRoute();
		v.getRoute().invertRouteWeights();
		vh.updateStatus(Status.E, v);
	}

	/**
	 * Needed to be a extra method, because vehicles can be alerted when they were in Status 1 somewhere on the road,
	 * so that a simple inverted Route wouldn't work.
	 * @param v
	 * @return
	 */
	private Route calcRouteToHomeStation(Vehicle v) {

		return ml.calcPath(v.getPosition(), v.getHomeStation().getOsmNode());
	}
	
	
	
	

}
