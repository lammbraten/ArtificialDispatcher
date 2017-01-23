package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.Observable;

public class TickEngine extends Observable{
	long tick;
	public long TICK = 60l*1000l;

	public TickEngine(){
		this.tick = 1483513200000L;
	}
	
	public long getTick() {
		return tick;
	}

	public void setTick(long tick) {
		setChanged();
		this.tick = tick;
		notifyObservers();
	}
	
	public void tickPlusOne(){
		setChanged();		
		tick += TICK;
		notifyObservers(this);
	}
}
