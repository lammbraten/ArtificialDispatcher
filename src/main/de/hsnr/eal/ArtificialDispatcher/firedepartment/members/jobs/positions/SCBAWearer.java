package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.jobs.positions;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.Equipment;

public class SCBAWearer implements Position{

	@Override
	public String getName() {
		return "Atemschutzgeräteträger";
	}

	@Override
	public List<Equipment> neededEquipment() {
		// TODO Auto-generated method stub
		return null;
	}

}
