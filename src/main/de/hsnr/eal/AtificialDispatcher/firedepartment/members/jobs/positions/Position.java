package de.hsnr.eal.AtificialDispatcher.firedepartment.members.jobs.positions;

import java.util.List;

import de.hsnr.eal.AtificialDispatcher.firedepartment.members.equipment.Equipment;

public interface Position {
	public List<Equipment> neededEquipment();
	public String getName();
}
