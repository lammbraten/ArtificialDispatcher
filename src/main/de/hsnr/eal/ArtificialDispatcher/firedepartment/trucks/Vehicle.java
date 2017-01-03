package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.FireFighter;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.Squad;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;

public interface Vehicle {
	public int getSpeed();
	//public int getSeats();
	//public Squad getCrew();
	//public void setCrew(Squad ff);
	public int getHomeStationId();
	public long getLocation();
	public void setLocation(long location);
	public List<EquipmentItem> getEquipment();
	public int getId();
	public VehicleType getType();
	public String getTypeTerm();
	public String getName();
	public int getCrewStrength();
	public Route getRoute();
	public void setRoute(Route route);

	
}
