package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.FireFighter;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.Squad;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.Equipment;

public interface Vehicle {
	public int getSpeed();
	//public int getSeats();
	//public Squad getCrew();
	//public void setCrew(Squad ff);
	public int getHomeStation();
	public long getLocation();
	public long setLocation();
	public List<Equipment> getEquipment();
	
}
