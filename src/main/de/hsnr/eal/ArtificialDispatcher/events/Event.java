package de.hsnr.eal.ArtificialDispatcher.events;

public class Event {
	private String name;
	private int id;
	
	public Event(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
		
	}

	public int getNr() {
		return 10;
	}
}
