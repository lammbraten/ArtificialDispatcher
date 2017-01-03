package de.hsnr.eal.ArtificialDispatcher.data.prolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class PLDatabase {
	
	
	private String filepath;

	public PLDatabase(String path){
		//System.out.println(System.getProperties());
		this.filepath = path;
		Query data = new Query( "consult",new Term[] {new Atom(filepath)});	
		System.out.println( "consult " + (data.hasMoreElements() ? "succeeded" : "failed"));
	}

	String getVehicleTypeForEquipment(){
       // String tVehicleForEuipment = "child_of(joe, ralf)";
       // Query qVehicleForEuipment = new Query(tVehicleForEuipment);
        
        return "";
	}
	
	private Map<String, Term>[] getStations(){
		String tStations = "station(Number, Name, Type, Node)";
        Query qStations = new Query(tStations);
        
        Map<String, Term>[] stations = qStations.allSolutions();
       	
        return stations;
	}
	
	public ArrayList<Station> getStationObjects(){
		Map<String, Term>[] stations = getStations();
		ArrayList<Station> stationObjects = new ArrayList<Station>();

        for(int i = 0; i < stations.length; i++){
    		int number =  Integer.parseInt(stations[i].get("Number").toString());
    		String name = stations[i].get("Name").toString().replace("'", "");
    		String type = stations[i].get("Type").toString().replace("'", "");
    		String node = stations[i].get("Node").toString();
    		stationObjects.add(new Station(number, name, type, node));
        }
        	
        return stationObjects;
	}
	
	private Map<String, Term>[] getVehiclesOfStation(int stationId){
		String tVehicles = "vehicle(Number, Type, Name, "+ stationId +")";
        Query qVehicles = new Query(tVehicles);
        
        Map<String, Term>[] vehicles = qVehicles.allSolutions();
       	
        return vehicles;
	}
	
	
	public ArrayList<Vehicle> getVehiclesObjectsOfStation(int stationId) throws Exception{
		Map<String, Term>[] vehicles = getVehiclesOfStation(stationId);
		ArrayList<Vehicle> vehicleObjects = new ArrayList<Vehicle>();
		
        for(int i = 0; i < vehicles.length; i++){
        	Map<String, Term> vehicleMap = vehicles[i];
    		constructVehicle(stationId, vehicleMap);
        }		
		
		return vehicleObjects;
		
	}
	
	private Map<String, Term> getEquipment(int id){
		String tEquipment = "equipment(" + id + ", Name, SetupTime, NeededPeople)";
        Query qEquipmemt = new Query(tEquipment);
        
        return qEquipmemt.getSolution();
	}
	
	private Term getVehicleType(String typeTerm) throws Exception{
		String tVehicle = "vehicleType(" + typeTerm + ", Equipment, Tank, EmergencySpeed, NormSpeed)";
        Query qVehicle = new Query(tVehicle);
        
        if(!qVehicle.hasSolution())
        	throw new Exception("no vehicleType found. Prolog Error 10");
        return qVehicle.getSolution().get(typeTerm);
	}
	
	

	private void constructVehicle(int stationId, Map<String, Term> vehicleMap) throws Exception {
		int id =  Integer.parseInt(vehicleMap.get("Number").toString());
		String name = vehicleMap.get("Name").toString().replace("'", "");
		String typeTerm = vehicleMap.get("Type").toString().replace("'", "");
		int station = stationId;
		
		
		Term vehicle = getVehicleType(typeTerm);

		System.out.println(vehicle.toString());
		
		
	}
}
