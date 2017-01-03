package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.util.ArrayList;
import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;

public class ConcreteVehicle implements Vehicle {
	private int id;
	private VehicleType type;
	private String name; //Funkrufname
	private int homeStationId;
	private int crewStrength;
	private List<EquipmentItem> equipment; 	
	private int normSpeed, emergencySpeed;
	private int tankVolume;
	private long location;
	private Route route;
	
	public ConcreteVehicle(int id, String typeTerm, String name, int homeStationId, int crewStrength,
			List<String> equipmentTerm, int emergencySpeed, int normSpeed, int tankVolume){
		this.id = id;
		this.type = VehicleType.parseType(typeTerm);
		this.name = name;
		this.homeStationId = homeStationId;
		this.crewStrength = crewStrength;
		this.equipment = generateEquipment(equipmentTerm);
		
	}
	
	private List<EquipmentItem> generateEquipment(List<String> equipmentTerm) {
		ArrayList<EquipmentItem> equipment = new ArrayList<EquipmentItem>();
		
		for(String item : equipmentTerm){
			
			
			//equipment.add(new EquipmentItem(crewStrength, item, crewStrength, crewStrength))
		}
		return null;
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
	public int getHomeStationId() {
		return homeStationId;
	}
	
	@Override
	public int getCrewStrength() {
		return crewStrength;
	}
	
	@Override
	public List<EquipmentItem> getEquipment() {
		return equipment;
	}
	
	public int getTankVolume() {
		return tankVolume;
	}
	
	@Override
	public int getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLocation() {
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
	
}
