package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.util.ArrayList;
import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;

public class ConcreteVehicle implements Vehicle {
	private int id;
	private VehicleType type;
	private String name; //Funkrufname
	private Station homeStation;
	private int crewStrength;
	/**
	 * General equipment of the vehicle, like hoses, nozzle or ladders.
	 */
	private List<EquipmentItem> equipment; 	
	private int normSpeed, emergencySpeed;
	private int tankVolume;
	private long location;
	private Route route;
	private Status fmsStatus;
	private Emergency emergency;
	private List<EmergencyTask> assignedTasks;	
	
	private double remainingMeter;

	
	public ConcreteVehicle(int id, String typeTerm, String name, Station homeStation, int crewStrength,
			List<EquipmentItem> equipment, int emergencySpeed, int normSpeed, int tankVolume){
		this.id = id;
		this.type = VehicleType.parseType(typeTerm);
		this.name = name;
		this.homeStation = homeStation;
		this.crewStrength = crewStrength;
		this.equipment = equipment;
		this.fmsStatus = Status.ZWEI;
		
		this.location = homeStation.getOsmNode();
		this.emergency = null;
		this.assignedTasks = new ArrayList<EmergencyTask>();
		
		//Meter die beim letzten tick nicht gefahren werden konnten.
		this.remainingMeter = 0.0;
				
	}
	
	@Override
	public String toString(){
		return name + ": Besatzung: " + crewStrength + " Status: " + fmsStatus; //+ " Beladung: " + equipment;
	}
	
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public VehicleType getType(){
		return type;
	}
	
	@Override
	public String getTypeTerm(){
		return type.toString();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public Station getHomeStation() {
		return homeStation;
	}
	
	@Override
	public int getCrewStrength() {
		return crewStrength;
	}
	
	public int getTankVolume() {
		return tankVolume;
	}
	
	@Override
	public int getSpeed() {
		if(emergency != null)
			return emergencySpeed;
		return normSpeed;
	}

	@Override
	public long getPosition() {
		return location;
	}

	@Override
	public void setLocation(long location) {
		this.location = location;
	}
	
	@Override
	public Route getRoute() {
		return route;
	}

	@Override
	public void setRoute(Route route) {
		this.route = route;
	}
	
	@Override
	public boolean hasRoute(){
		if(this.route != null)
			return true;
		return false;
	}

	@Override
	public Status getStatus(){
		return fmsStatus;
	}

	@Override
	public void setStatus(Status status) {
		this.fmsStatus = status;
	}

	@Override
	public Emergency getEmergency() {
		return this.emergency;
	}

	@Override
	public void setEmergency(Emergency e) {
		this.emergency = e;
	}
	
	@Override
	public boolean hasEmergency() {
		if(this.emergency != null)
			return true;
		return false;
	}


	@Override
	public double getRemainingMeter() {
		return remainingMeter;
	}
	
	@Override
	public void setRemainingMeter(double remainingMeter) {
		this.remainingMeter = remainingMeter;
	}

	@Override
	public boolean isAtTarget() {
		if(!hasRoute())
			return false; //Ist nicht unterwegs
		if(route.getTargetNodeId() == location)
			return true;
		
		return false;
	}

	@Override
	public void alert() {
		//TODO (Route? Status? Geschwindigkeit? Wann ausrücken?)
		
	}

	@Override
	public int getRespondTime() {
		if(this.fmsStatus.equals(Status.EINS) || this.fmsStatus.equals(Status.DREI))
			return 0; //Kann direkt zum Einsatz fahren;
		if(this.fmsStatus.equals(Status.ZWEI))
			return this.homeStation.getType().getResponseTime();
		if(this.fmsStatus.equals(Status.VIER)) 
			return calcRemainingTimeAtEmergency();
		return 0;
	}

	private int calcRemainingTimeAtEmergency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean canDo(EmergencyTask t) {
		List<EquipmentItem> freeEquipment = equipment;
				
		for(EquipmentItem ei : t.getNeededEquipment()){
			if(freeEquipment.contains(ei)){
				freeEquipment.remove(ei);
			} else {
				return false;
			}
		}
		return true;
	}

	
	@Override
	public void assignTo(Emergency e, EmergencyTask t) {
		if(emergency == null)
			this.setEmergency(e);
		
		if(emergency.equals(e)){
			for(EquipmentItem  ei : t.getNeededEquipment())
				for(EquipmentItem ei2 : getUanassignedEquipment())
					if(ei2.equals(ei)){
						ei2.inUse(true);
						break;
					}
			assignedTasks.add(t);
			emergency.addAssignedVehicle(this, t);
		} else {
			throw new IllegalArgumentException("Already assigned to an Emergency");
		}
	}

	@Override
	public List<EmergencyTask> getAssignedTasks() {
		return assignedTasks;
	}


	@Override
	public List<EquipmentItem> getEquipment() {
		return equipment;
	}
	
	@Override
	public List<EquipmentItem> getAssignendEquipment() {
		List<EquipmentItem> assignedEquipment = new ArrayList<EquipmentItem>();
		for(EquipmentItem et : equipment)
			if(et.isInUse())
				assignedEquipment.add(et);
		return assignedEquipment;
	}

	@Override
	public List<EquipmentItem> getUanassignedEquipment() {
		List<EquipmentItem> unassignedEquipment = new ArrayList<EquipmentItem>();
		for(EquipmentItem et : equipment)
			if(!et.isInUse())
				unassignedEquipment.add(et);
		return unassignedEquipment;
	}
}
