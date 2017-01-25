package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.RadioMessage;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Status;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.VehicleType;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.Tuple;

public class VehicleHandler extends Observable {
	private static final String LST = "LST";
	ArrayList<Vehicle> vehicles;
	private TickEngine te;
	private RadioHandler rh;
	
	public VehicleHandler(ArrayList<Vehicle> vehicles, TickEngine te, RadioHandler rh) {
		this.vehicles = vehicles;
		this.te = te;
		this.rh = rh;
	}
	
	public void moveVehicles(){
		for(Vehicle v : vehicles)
			moveVehicle(v);
	}
	
	private void moveVehicle(Vehicle v) {
		if(needBackRoute(v))
			sendBackToStation(v);
		if(v.hasRoute()){
			if(v.getStatus().equals(Status.C))
				checkIfCanRespondYet(v);
			this.setChanged();
			if(v.getStatus().equals(Status.DREI) || v.getStatus().equals(Status.EINS)){	
				calcAndSetPosition(v);
				if(v.isAtTarget()){
					if(v.hasEmergency()){
						updateStatus(Status.VIER, v);
					}else{
						updateStatus(Status.ZWEI, v);
					}
				}
			}
			this.notifyObservers(v);
		}
	}

	private boolean needBackRoute(Vehicle v) {
		if(v.getStatus().equals(Status.E))
			return true;
		return false;
	}

	private void checkIfCanRespondYet(Vehicle v) {
		//System.out.println(v.getEmergency().getStartTime() + v.getHomeStation().getType().getResponseTime() + " " + te.tick );
		if(v.getEmergency().getStartTime() + v.getHomeStation().getType().getResponseTime() < te.tick || v.getPosition() != v.getHomeStation().getOsmNode())
			updateStatus(Status.DREI, v);
	}
	
	public void updateStatus(Status s, Vehicle v){
		v.setStatus(s);
		if(s.isSendFromVehicle()){
			rh.addMessage(new RadioMessage(v.getName(), LST, "Status " + s.toString() + " " + s.getLabel(), this.te.actTime())); 
		}else{
			rh.addMessage(new RadioMessage(LST, v.getName(), "Status " + s.toString() + " " + s.getLabel(), this.te.actTime())); 			
		}
	}

	private void calcAndSetPosition(Vehicle v) {
		int iNode = v.getRoute().getNodeIds().indexOf(v.getPosition());
		double distance = calcDrivableTimeDistance(v.getSpeed(), v.getRemainingMeter());
				
		Tuple<Long, Double> t = v.getRoute().findNearestNodeFor(iNode, distance);
		
		v.setLocation(t.t1);
		v.setRemainingMeter(t.t2);
	}

	private double calcDrivableTimeDistance(int speed, double remainingTimeDistance) {
		return  (speed ) + remainingTimeDistance;
	}

	public Set<Vehicle> getAvailableVehicles(){
		Set<Vehicle> freeVehicles = new HashSet<Vehicle>();
		for(Vehicle v : vehicles)
			if(Status.isAvailable(v.getStatus()))
				freeVehicles.add(v);
		
		return freeVehicles;
	}
	
	public Set<Vehicle> getVehiclenOf(List<VehicleType> vts){
		HashSet<Vehicle> vehicles = new HashSet<Vehicle>();
		for(Vehicle v : vehicles)
			if(vts.contains(v.getType()))
				vehicles.add(v);
		return vehicles;
	}
	
	public void alertVehicle(Vehicle v, Emergency e, EmergencyTask t, Route r){
		if(!v.isAvailable())
			throw new IllegalArgumentException("Vehicle not available!\n");
		if(e == null)
			throw new IllegalArgumentException("Emergency not found!\n");
		if(r == null)
			throw new IllegalArgumentException("No Route found!\n");
		else{
			this.setChanged();
			v.assignTo(e, t);
			v.setRoute(r);
			updateStatus(Status.C, v);	
			//v.setEmergency(e);
			//e.addAssignedVehicle(v, t);
			this.notifyObservers(v);
		}	
	}
	
	public Set<Vehicle> getVehiclesOnPosition(long osmNodeId){
		HashSet<Vehicle> vehiclesOnPosition = new HashSet<Vehicle>();
		
		for(Vehicle v : vehicles)
			if(v.getPosition() == osmNodeId)
				vehiclesOnPosition.add(v);
		return vehiclesOnPosition;
	}

	public void newTick() {
		moveVehicles();
	}

	private void sendBackToStation(Vehicle v) {
		this.updateStatus(Status.EINS, v);
		v.getEmergency().removeAssignedVehicle(v);
		v.setEmergency(null);

	}	
	
	
}
