package de.hsnr.eal.AtificialDispatcher.firedepartment.members.jobs.positions;

import java.util.List;

import de.hsnr.eal.AtificialDispatcher.firedepartment.members.equipment.Equipment;

public class General implements Position {

	@Override
	public String getName() {
		return "Grundausbildung";
	}

	@Override
	public List<Equipment> neededEquipment() {
		// TODO Auto-generated method stub
		return null;
	}

}
