package de.hsnr.eal.ArtificialDispatcher.emergency;

public class Emergency {
	private String name;
	private int id;
	
	public Emergency(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
		
	}

	public int getNr() {
		return 10;
	}
}
