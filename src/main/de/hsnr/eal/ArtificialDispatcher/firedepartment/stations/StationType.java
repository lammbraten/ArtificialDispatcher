package de.hsnr.eal.ArtificialDispatcher.firedepartment.stations;

public enum StationType {
	FF(3*60*1000),
	BF(1*60*1000);
	
	private int responseTime;
	StationType(int responseTime){
		this.responseTime = responseTime;
	}
	
	public int getResponseTime(){
		return this.responseTime;
	}
	
	@Override
	public String toString(){
		return this.name() + " Responeses within " + this.getResponseTime() + " mins";
	}
	
	
}
