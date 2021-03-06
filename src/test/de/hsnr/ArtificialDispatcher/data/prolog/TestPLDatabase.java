package de.hsnr.ArtificialDispatcher.data.prolog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import de.hsnr.eal.ArtificialDispatcher.data.prolog.PLDatabase;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class TestPLDatabase {

	private static final String FILE_PATH = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Implementierung\\ArtificialDispatcher\\src\\main\\de\\hsnr\\eal\\ArtificialDispatcher\\data\\prolog\\vehicles.pl";

	@Test
	public void testGetStations() {
		PLDatabase pldb = new PLDatabase(FILE_PATH);
		ArrayList<Station> stations = pldb.getStationObjects();
		for(Station station : stations)
			System.out.println(station);
	}
	
	@Test
	public void testGetVehiclesOfStation() {
		PLDatabase pldb = new PLDatabase(FILE_PATH);
		ArrayList<Vehicle> vehicles;
		try {
			vehicles = pldb.getVehiclesObjectsOfStation(3);
			for(Vehicle vehicle : vehicles)
				System.out.println(vehicle);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void testGetEmergenyTypeObjects() {
		PLDatabase pldb = new PLDatabase(FILE_PATH);
		List<EmergencyType> emergencyTypes;
		try {
			emergencyTypes = pldb.getEmergencyTypeObjects();
			for(EmergencyType emergencyType : emergencyTypes)
				System.out.println(emergencyType);	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
