package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.jobs.positions;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.Equipment;

public interface Position {
	public List<Equipment> neededEquipment();
	public String getName();
}
