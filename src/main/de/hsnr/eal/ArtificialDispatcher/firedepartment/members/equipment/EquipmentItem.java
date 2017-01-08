package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment;

public class EquipmentItem {
	private int id;
	private int typeId;
	private String name;
	private int setupTime;
	private int neededPeople;
	private boolean inUse;
	
	public EquipmentItem(int typeId, String name, int setupTime, int neededPeople){
		this.id = 0; //TODO: Generate Id for this
		this.typeId = typeId;
		this.name = name;
		this.setupTime = setupTime;
		this.neededPeople = neededPeople;
		this.inUse = false;
	}
	
	public int getTypeId(){
		return typeId;
	}
	
	public String getName(){
		return name;
	}
	/**
	 * de: R�stzeit
	 * @return time in minutes
	 */
	public int getSetupTime(){
		return setupTime;
	}
	
	/**
	 * People needed to use this Equipment
	 * @return number of People needed;
	 */
	int getNeededPeople(){
		return neededPeople;
	}
	
	public void inUse(boolean bool){
		this.inUse = bool;
	}
	
	public boolean isInUse(){
		return this.inUse;
	}
	
	@Override
	public String toString(){
		//return name + " needs " + setupTime+ " mins and " + neededPeople + " FireFighters to setup";
		return name;
	}
}
