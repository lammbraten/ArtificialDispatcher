package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.jobs.positions;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;

public interface Position {
	public List<EquipmentItem> neededEquipment();
	public String getName();
}
