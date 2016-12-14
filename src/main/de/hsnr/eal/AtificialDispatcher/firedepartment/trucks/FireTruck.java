package de.hsnr.eal.AtificialDispatcher.firedepartment.trucks;

import de.hsnr.eal.AtificialDispatcher.firedepartment.members.Squad;
import de.hsnr.eal.AtificialDispatcher.firedepartment.members.FireFighter;

public interface FireTruck {
	public int getSpeed();
	public int getSeats();
	public Squad getCrew();
	public void setCrew(Squad ff);
	public int getHomeStation();
	
}
