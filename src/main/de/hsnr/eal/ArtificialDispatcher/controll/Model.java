package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.data.prolog.PLDatabase;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class Model extends Observable implements Observer{
	
	private static final String FILE_PATH = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Implementierung\\ArtificialDispatcher\\src\\main\\de\\hsnr\\eal\\ArtificialDispatcher\\data\\prolog\\KnowledgeBase.pl";
	MapLoader ml; 
	ArrayList<Vehicle> vehicles;
	ArrayList<Station> stations;
	List<EmergencyType> emergencyTypes;
	TickEngine te;	
	RadioHandler rh;
	
	
	private VehicleHandler vh;
	private EmergencyHandler eh;
	private PLDatabase pldb;

	
	public Model(){
		loadGraph();
		loadDb();
		
		te = new TickEngine();
		te.addObserver(this);
		
		rh = new RadioHandler();
		rh.addObserver(this);
		
		vh = new VehicleHandler(this.vehicles, this.te, this.rh);
		vh.addObserver(this);
		
		eh = new EmergencyHandler(vh, ml, te);
		eh.addObserver(this);
		


		
	}

	private void loadGraph() {
		ml = new MapLoader();

	}
	
	private void loadDb() {
		pldb = new PLDatabase(FILE_PATH);
		stations = pldb.getStationObjects();
		vehicles = new ArrayList<Vehicle>();
		try {
			emergencyTypes = pldb.getEmergencyTypeObjects();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			for(Station station : stations)
				vehicles.addAll(pldb.getVehiclesObjectsOfStation(station.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		//System.out.println("update Model");
		
		this.setChanged();
		
		if(arg0 instanceof VehicleHandler)
			updateViewWithVehicle(arg1);
		if(arg0 instanceof EmergencyHandler)
			notifyObservers(arg1);
		if(arg0 instanceof TickEngine)
			updateModelWithTickEngine(arg1);
		if(arg0 instanceof RadioHandler){
			notifyObservers(arg1);
		}
	}
	
	
	private void updateModelWithTickEngine(Object arg1){

		TickEngine te = null;
		if(arg1 instanceof TickEngine)
			te = (TickEngine) arg1;
		notifyObservers(te);

		
		this.vh.newTick();
		this.eh.newTick();
	}


	private void updateViewWithVehicle(Object arg1) {
		//TODO: Vielleicht unnötigt das Object in ein Vehicle zu casten, wenn es danach wieder an eine Mtehode übergeben wird, welche ein object erwartet.
		Vehicle v = null;
		if(arg1 instanceof Vehicle)
			v = (Vehicle) arg1;
		this.notifyObservers(v);
		
	}

	public EmergencyHandler getEmergencyHandler() {
		return eh;
	}
}
