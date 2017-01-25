package de.hsnr.eal.ArtificialDispatcher.emergency;

import java.util.ArrayList;
import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;

public class EmergencyTask {
	private int id;
	private String name;
	private List<EquipmentItem> neededEquipment;
	private int estimatedTime;
	private long startTimestamp;
	private boolean assigned;
	private boolean started;
	private boolean finished;	
	
	public EmergencyTask(int id, String name, List<EquipmentItem> neededEquipment,
			int estimatedTime) {
		super();
		this.id = id;
		this.name = name;
		this.neededEquipment = neededEquipment;
		this.estimatedTime = estimatedTime *60*1000;
		this.startTimestamp = -1;
		this.finished = false;
		this.started = false;
		this.setAssigned(false);
	}
	
	public EmergencyTask(EmergencyTask t) {
		super();
		this.id = t.id;
		this.name = t.name;
		
		this.neededEquipment = new ArrayList<EquipmentItem>();
		for(EquipmentItem e : t.neededEquipment)
			this.neededEquipment.add(new EquipmentItem(e.getTypeId(), e.getName(), e.getSetupTime(), e.getNeededPeople()));
		
		
		this.estimatedTime = t.estimatedTime;
		this.startTimestamp = -1;
		this.finished = false;
		this.started = false;
		this.setAssigned(false);
	}

	public void assignEquipment(EquipmentItem item){
		item.inUse(true);
	}
	
	public void releaseAllEquipment(){
		for(EquipmentItem ei: neededEquipment)
			ei.inUse(false);
	}
	
	public boolean canStart(){
		if(started == false && !hasFinished())
			return true;
		return false;
	}
	
	public void start(long tick){
		startTimestamp = tick;
		started = true;
	}

	private boolean isAllEquipmentAssigned(){
		for(EquipmentItem ei: neededEquipment)
			if(!ei.isInUse())	
				return false;
		return true;
				
	}
	
	@Override
	public String toString(){
		return this.name + ", Equipment: " + neededEquipment + "\n"; 
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
	
	public int getEstimatedTime() {
		return estimatedTime;
	}
	
	public long getStartTimestamp() {
		return startTimestamp;
	}
	
	public boolean hasFinished() {
		return finished;
	}
	
	private boolean isFinished(long actTimestamp) {
		if(getRestTime(actTimestamp) <= 0)
			finished = true;
		return finished;
	}

	public long getRestTime(long actTimestamp) throws IllegalStateException{
		if(startTimestamp == -1)
			throw new IllegalStateException("Task hasn't started yet.");
		return estimatedTime - (actTimestamp - startTimestamp);
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public boolean canFinish(long tick) {
		if(started == false || finished == true)
			return false;
		return isFinished(tick);
	}

	public void finish() {
		releaseAllEquipment();
		finished = true;
		
	}

	
	
	
}
