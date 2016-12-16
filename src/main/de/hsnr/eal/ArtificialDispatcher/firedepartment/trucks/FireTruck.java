package de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.FireFighter;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.Squad;

public interface FireTruck {
	public int getSpeed();
	public int getSeats();
	public Squad getCrew();
	public void setCrew(Squad ff);
	public int getHomeStation();
	
}
