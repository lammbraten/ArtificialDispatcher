package de.hsnr.eal.ArtificialDispatcher.emergency;

import java.util.ArrayList;
import java.util.List;

public class EmergencyType {
	private String codeword;
	private String name;
	private List<EmergencyTask> tasks;
	
	public EmergencyType(String codeword, String name, List<EmergencyTask> tasks) {
		super();
		this.codeword = codeword;
		this.name = name;
		this.tasks = tasks;
	}
	
	public EmergencyType(EmergencyType et) {
		super();
		this.codeword = et.codeword;
		this.name = et.name;
		
		this.tasks = new ArrayList<EmergencyTask>();
		for(EmergencyTask t : et.tasks)
			this.tasks.add(new EmergencyTask(t));
	}

	public String getCodeword() {
		return codeword;
	}

	public String getName() {
		return name;
	}

	public List<EmergencyTask> getTasks() {
		return tasks;
	}
	
	@Override 
	public String toString(){
		return this.codeword + " -  " + this.name; 
	}

	
}
