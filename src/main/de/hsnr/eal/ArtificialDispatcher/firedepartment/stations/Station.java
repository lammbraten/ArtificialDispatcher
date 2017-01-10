package de.hsnr.eal.ArtificialDispatcher.firedepartment.stations;

public class Station {
	private int id;
	private String name;
	private StationType type;
	private long osmNode;
	
	public Station(int id, String name, String type, long node){
		this.id = id;
		this.name = name;
		this.osmNode = node;
				
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StationType getType() {
		return type;
	}

	public void setType(StationType type) {
		this.type = type;
	}

	public long getOsmNode() {
		return osmNode;
	}

	public void setOsmNode(long osmNode) {
		this.osmNode = osmNode;
	}
	
}
