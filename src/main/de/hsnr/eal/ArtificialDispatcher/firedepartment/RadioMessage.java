package de.hsnr.eal.ArtificialDispatcher.firedepartment;

public class RadioMessage {
	
	private String source;
	private String destination;
	private String message;
	private String time;
	
	public RadioMessage(String src, String dst, String msg, String time){
		this.source = src;
		this.destination = dst;
		this.message = msg;
		this.time = time;
	}
	
	@Override
	public String toString(){
		return  "(" + time + ") " +  source + " >> " + destination + "; " + message;
	}

}
