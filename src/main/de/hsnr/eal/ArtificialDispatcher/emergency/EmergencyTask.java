package de.hsnr.eal.ArtificialDispatcher.emergency;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;

public class EmergencyTask {
	private int id;
	private String name;
	private List<EquipmentItem> neededEquipment;
	private List<EquipmentItem> altNeededEquipment;
	private List<EquipmentItem> assignedEquipment;
	private int estimatedTime;
	private int startTimestamp;
	private boolean started;
	private boolean finished;	
	
	public EmergencyTask(int id, String name, List<EquipmentItem> neededEquipment,
			List<EquipmentItem> altNeededEquipment, int estimatedTime) {
		super();
		this.id = id;
		this.name = name;
		this.neededEquipment = neededEquipment;
		this.altNeededEquipment = altNeededEquipment;
		this.estimatedTime = estimatedTime;
		this.startTimestamp = -1;
		this.finished = false;
		this.started = false;
	}
	
	public void assignEquipment(EquipmentItem item){
		item.inUse(true);
	}
	
	public void releaseAllEquipment(){
		
	}
	
	public boolean canStart(){
		
		return false;
	}
	
	public void start(int actTimestamp){
		//TODO: Setup-time nicht vergessen
		
	}

	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<EquipmentItem> getNeededEquipment() {
		return neededEquipment;
	}
	public List<EquipmentItem> getAltNeededEquipment() {
		return altNeededEquipment;
	}
	public int getEstimatedTime() {
		return estimatedTime;
	}
	public int getStartTimestamp() {
		return startTimestamp;
	}
	public boolean isFinished() {
		return finished;
	}
	public boolean isFinished(int actTimestamp) {
		if(getRestTime(actTimestamp) >= 0)
			finished = true;
		return finished;
	}

	public int getRestTime(int actTimestamp) throws IllegalStateException{
		if(startTimestamp == -1)
			throw new IllegalStateException("Task hasn't yet.");
		return estimatedTime - (actTimestamp - startTimestamp);
	}

	
	
	
}
