package de.hsnr.ArtificialDispatcher.data.prolog;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import de.hsnr.eal.ArtificialDispatcher.data.prolog.PLDatabase;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;

public class TestPLDatabase {

	private static final String FILE_PATH = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Implementierung\\ArtificialDispatcher\\src\\main\\de\\hsnr\\eal\\ArtificialDispatcher\\data\\prolog\\vehicles.pl";

	@Test
	public void testGetStations() {
		PLDatabase pldb = new PLDatabase(FILE_PATH);
		 ArrayList<Station> stations = pldb.getStationObjects();
		 for(Station station : stations)
			 System.out.println(station);
	}

}
