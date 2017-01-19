package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Status;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.VehicleType;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.Tuple;

public class VehicleHandler extends Observable {

	ArrayList<Vehicle> vehicles;
	
	public VehicleHandler(ArrayList<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	public void moveVehicles(){
		for(Vehicle v : vehicles)
			moveVehicle(v);
	}
	
	private void moveVehicle(Vehicle v) {
		this.setChanged();
		if(v.hasRoute())
			calcAndSetPosition(v);
		if(v.isAtTarget()){
			if(v.hasEmergency()){
				v.setStatus(Status.VIER);
			}else{
				v.setStatus(Status.ZWEI);
			}
		}
		this.notifyObservers(v);
	}

	private void calcAndSetPosition(Vehicle v) {
		int iNode = v.getRoute().getNodeIds().indexOf(v.getLocation());
		double distance = calcDrivableDistance(v.getSpeed(), v.getRemainingMeter());
				
		Tuple<Long, Double> t = v.getRoute().findNearestNodeFor(distance, iNode);
		
		v.setLocation(t.t1);
		v.setRemainingMeter(t.t2);
	}

	private double calcDrivableDistance(int speed, double remainingDistance) {
		// s = v · t + s0 
		return  ((speed * (1/60))*1000) + remainingDistance;
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
	
	public void alertVehicle(Vehicle v, Emergency e, Route r){
		if(!Status.isAvailable(v.getStatus()))
			throw new IllegalArgumentException("Vehicle not available!\n");
		if(e == null)
			throw new IllegalArgumentException("Emergency not found!\n");
		if(r == null)
			throw new IllegalArgumentException("No Route found!\n");
		else{
			this.setChanged();
			v.setRoute(r);
			v.setStatus(Status.DREI);
			v.setEmergency(e);
			e.addAssignedVehicle(v);
			this.notifyObservers(v);
		}	
	}
	
	
}
