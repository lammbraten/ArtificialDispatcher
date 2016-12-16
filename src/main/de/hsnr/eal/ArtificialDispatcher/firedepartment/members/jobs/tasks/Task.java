package de.hsnr.eal.ArtificialDispatcher.firedepartment.members.jobs.tasks;

import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.FireFighter;

public interface Task {
	int getRequiredTime();
	int getYetSpendTime();
	int getNumberOfRequiredFireFighters();
	List<FireFighter> getAssignedFireFighters();
}
