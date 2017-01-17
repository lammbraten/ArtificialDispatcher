package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyTask;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.FireFighter;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.Squad;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;

public interface Vehicle {
	public Status getStatus();
	public void setStatus(Status status);
	public int getSpeed();
	//public int getSeats();
	//public Squad getCrew();
	//public void setCrew(Squad ff);
	public Station getHomeStation();
	public long getLocation();
	public void setLocation(long location);
	public List<EquipmentItem> getEquipment();
	public List<EquipmentItem> getUsedEquipment();
	public int getId();
	public VehicleType getType();
	public String getTypeTerm();
	public String getName();
	public int getCrewStrength();
	public Route getRoute();
	public void setRoute(Route route);
	public boolean hasRoute();
	public Emergency getEmergency();
	public void setEmergency(Emergency e);
	double getRemainingMeter();
	void setRemainingMeter(double remainingMeter);
	public boolean isAtTarget();
	boolean hasEmergency();
	boolean canDoTask(EmergencyTask task);
	public void alert();


	
}
