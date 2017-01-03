package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.jobs.positions;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;

public class SquadLeader implements Position {

	@Override
	public String getName() {
		return "Gruppenführer";
	}

	@Override
	public List<EquipmentItem> neededEquipment() {
		// TODO Auto-generated method stub
		return null;
	}

}
