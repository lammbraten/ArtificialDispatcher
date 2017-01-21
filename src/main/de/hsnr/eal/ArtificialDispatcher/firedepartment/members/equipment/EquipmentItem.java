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
	
	public void inUse(boolean bool){
		this.inUse = bool;
	}
	
	public boolean isInUse(){
		return this.inUse;
	}
	
	 @Override
	public boolean equals(Object o){
		if(o == null)
			return false;
		if(o instanceof EquipmentItem){
			EquipmentItem et = (EquipmentItem) o;
			if(et.getTypeId() == this.getTypeId())
				return true;
		}
		return false;
	}
	
	@Override
	public String toString(){
		//return name + " needs " + setupTime+ " mins and " + neededPeople + " FireFighters to setup";
		return name;
	}
}
