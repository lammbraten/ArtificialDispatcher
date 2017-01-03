package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment;

public class EquipmentItem {
	private int typeId;
	private String name;
	private int setupTime;
	private int neededPeople;
	EquipmentItem(int typeId, String name, int setupTime, int neededPeople){
		this.typeId = typeId;
		this.name = name;
		this.setupTime = setupTime;
		this.neededPeople = neededPeople;
	}
	
	public int getTypeId(){
		return typeId;
	}
	
	public String getName(){
		return name;
	}
	/**
	 * de: Rüstzeit
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
	
	@Override
	public String toString(){
		return name + " needs " + setupTime+ " mins and " + neededPeople + " FireFighters to setup";
	}
}
