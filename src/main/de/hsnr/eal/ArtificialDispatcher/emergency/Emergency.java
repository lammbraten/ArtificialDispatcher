package de.hsnr.eal.ArtificialDispatcher.emergency;

import de.hsnr.eal.ArtificialDispatcher.data.map.GeoLocation;

public class Emergency implements Comparable {
    private static int counter = 0;
	private int nr;
	private EmergencyType et;
	private int id;
	private GeoLocation gl;
	
	public Emergency(EmergencyType et, GeoLocation gl) {
        counter++;
        nr = counter;
		this.et = et;
		this.gl = gl;
	}

	public String getName(){
		return this.et.getName();
		
	}

	public int getNr() {
		return nr;
	}

	@Override
	public int compareTo(Object arg0) {
		if(arg0 instanceof Emergency){
			Emergency otherEmergency = (Emergency) arg0;
			return this.getNr() - otherEmergency.getNr();
		}	
		return 0;
	}
}
