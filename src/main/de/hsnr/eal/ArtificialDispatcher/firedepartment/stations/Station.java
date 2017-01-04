package de.hsnr.eal.ArtificialDispatcher.firedepartment.stations;

public class Station {
	private int id;
	private String name;
	private StationType type;
	private long location;
	
	public Station(int id, String name, String type, long node){
		this.id = id;
		this.name = name;
		this.location = node;
				
		if(type.equals(StationType.BF.name()))
			this.type = StationType.BF;
		else if(type.equals(StationType.FF.name()))
			this.type = StationType.FF;

	}
	
	@Override
	public String toString(){
		return id + ", " + name + ", " + type ;
	}
	
	public int getId(){
		return id;
	}
}