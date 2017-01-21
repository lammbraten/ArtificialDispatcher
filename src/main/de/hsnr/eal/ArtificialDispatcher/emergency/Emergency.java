package de.hsnr.eal.ArtificialDispatcher.emergency;

import java.util.ArrayList;
import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.data.map.GeoLocation;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class Emergency implements Comparable {
    private static int counter = 0;
	private int nr;
	private EmergencyType et;
	private GeoLocation gl;
	private List<EmergencyTask> tasks;
	private List<Vehicle> assignedVehicles;
	
	public Emergency(EmergencyType et, GeoLocation gl) {
        counter++;
        nr = counter;
		this.et = et;
		this.gl = gl;
		this.setTasks(et.getTasks());
		this.assignedVehicles = new ArrayList<Vehicle>();
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
	
	public void addAssignedVehicle(Vehicle v, EmergencyTask t) {
		assignedVehicles.add(v);
		t.setAssigned(true);
	}
	
	
	public boolean isHelpful(Vehicle v){
		return false;
	}

	public List<EmergencyTask> getTasks() {
		return tasks;
	}

	public void setTasks(List<EmergencyTask> tasks) {
		this.tasks = tasks;
	}
	
	public List<EmergencyTask> getUnassingnedTasks() {
		List<EmergencyTask> unassignedTasks = new ArrayList<EmergencyTask>();
		
		for(EmergencyTask et : tasks)
			if(!et.isAssigned())
				unassignedTasks.add(et);
		
		return unassignedTasks;
	}


	public List<EmergencyTask> getAssignedTasks() {
		List<EmergencyTask> assignedTasks = new ArrayList<EmergencyTask>();
		
		for(EmergencyTask et : tasks)
			if(!et.isAssigned())
				assignedTasks.add(et);
		
		return assignedTasks;
	}

	public boolean hasUnassignedTasks() {
		if(this.getUnassingnedTasks().isEmpty())
			return false;
		return true;
	}
}
