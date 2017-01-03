package de.hsnr.eal.ArtificialDispatcher.data.prolog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;

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
        /*
        System.out.println("all solutions of " + tStations);
        for(int i = 0; i < stations.length; i++)
        	System.out.println("Name = "  + stations[i].get("Name"));*/
        	
        return stations;
	}
	
	public ArrayList<Station> getStationObjects(){
		Map<String, Term>[] stations = getStations();
		ArrayList<Station> stationObjects = new ArrayList<Station>();
		

    	
    	
    	
        for(int i = 0; i < stations.length; i++){
    		int number =  Integer.parseInt(stations[i].get("Number").toString());
    		String name = stations[i].get("Name").toString();
    		String type = stations[i].get("Type").toString();
    		String node = stations[i].get("Node").toString();
    		stationObjects.add(new Station(number, name, type, node));
        }
        	
        return stationObjects;
		
	}
	
	//public get
}
