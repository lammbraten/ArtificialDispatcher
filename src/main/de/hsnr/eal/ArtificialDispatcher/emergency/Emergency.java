package de.hsnr.eal.ArtificialDispatcher.emergency;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.data.map.GeoLocation;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class Emergency implements Comparable {
    private static int counter = 0;
	private int nr;
	private EmergencyType et;
	private GeoLocation gl;
	private int id;
	private List<EmergencyTask> unassignedTasks;
	private List<EmergencyTask> assignedTasks;
	private List<Vehicle> assignedVehicles;
	
	public Emergency(EmergencyType et, GeoLocation gl) {
        counter++;
        nr = counter;
		this.et = et;
		this.gl = gl;
		this.setUnassingnedTasks(et.getTasks());
	}

	public String getName(){
		return this.et.getName();
		
	}

	public int getNr() {
		return nr;
	}
	
	

	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof Emergency){
			Emergency otherEmergency = (Emergency) arg0;
			return this.getNr() - otherEmergency.getNr();
		}	
		return 0;
	}
	
	@Override 
	public String toString(){
		return this.getName() + ": " + et.getTasks();
	}

	public GeoLocation getGeoLocation() {
		return gl;
	}

	public EmergencyType getEmergencyType() {
		return et;
	}

	public List<Vehicle> getAssignedVehicles() {
		return assignedVehicles;
	}

	public void setAssignedVehicles(List<Vehicle> assignedVehicles) {
		this.assignedVehicles = assignedVehicles;
	}
	
	public void addAssignedVehicle(Vehicle v) {
		assignedVehicles.add(v);
	}
	
	
	public boolean isHelpful(Vehicle v){
		return false;
	}

	public List<EmergencyTask> getUnassingnedTasks() {
		return unassignedTasks;
	}

	public void setUnassingnedTasks(List<EmergencyTask> toDoTasks) {
		this.unassignedTasks = toDoTasks;
	}

	public List<EmergencyTask> getAssignedTasks() {
		return assignedTasks;
	}

	public void setAssignedTasks(List<EmergencyTask> assignedTasks) {
		this.assignedTasks = assignedTasks;
	}


	
}
