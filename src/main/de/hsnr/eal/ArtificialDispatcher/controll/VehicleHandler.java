package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Status;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.VehicleType;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.Tuple;

public class VehicleHandler extends Observable {
	ArrayList<Vehicle> vehicles;
	private TickEngine te;
	
	public VehicleHandler(ArrayList<Vehicle> vehicles, TickEngine te) {
		this.vehicles = vehicles;
		this.te = te;
	}
	
	public void moveVehicles(){
		for(Vehicle v : vehicles)
			moveVehicle(v);
	}
	
	private void moveVehicle(Vehicle v) {
		if(v.hasRoute()){
			if(v.getStatus().equals(Status.C))
				checkIfCanRespondYet(v);
			this.setChanged();
			if(v.getStatus().equals(Status.DREI) || v.getStatus().equals(Status.EINS)){	
				calcAndSetPosition(v);
				if(v.isAtTarget()){
					if(v.hasEmergency()){
						v.setStatus(Status.VIER);
					}else{
						v.setStatus(Status.ZWEI);
					}
				}
			}
			this.notifyObservers(v);
		}
	}

	private void checkIfCanRespondYet(Vehicle v) {
		System.out.println(v.getEmergency().getStartTime() + v.getHomeStation().getType().getResponseTime() + " " + te.tick );
		if(v.getEmergency().getStartTime() + v.getHomeStation().getType().getResponseTime() < te.tick )
			v.setStatus(Status.DREI);
	}

	private void calcAndSetPosition(Vehicle v) {
		int iNode = v.getRoute().getNodeIds().indexOf(v.getPosition());
		double distance = calcDrivableTimeDistance(v.getSpeed(), v.getRemainingMeter());
				
		Tuple<Long, Double> t = v.getRoute().findNearestNodeFor(distance, iNode);
		
		v.setLocation(t.t1);
		v.setRemainingMeter(t.t2);
	}

	private double calcDrivableTimeDistance(int speed, double remainingTimeDistance) {
		System.out.println((speed ) + remainingTimeDistance);
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
			v.setStatus(Status.C);
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

}
