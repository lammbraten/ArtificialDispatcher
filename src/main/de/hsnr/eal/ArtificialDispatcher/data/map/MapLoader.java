package de.hsnr.eal.ArtificialDispatcher.data.map;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.Dijkstra;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.ShortestPath;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.PythagoreanDistanceWeight;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.PythagoreanTimeWeight;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.WeightFunction;
import de.westnordost.osmapi.map.data.LatLon;

public class MapLoader {
	private static String DEFAULT_MAP_SOURCE = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Daten\\Roh\\Krefeld_Streetmap.bin";
	private static WeightFunction WEIGHTFUNCTION =  new PythagoreanTimeWeight();
	
	private DataProvider dp;
	private StreetGraph sg;
	private static String graphFilePath;
	private Dijkstra dijkstra;
	
	public MapLoader(){
		this(DEFAULT_MAP_SOURCE);
	}
	
	public MapLoader(String graphFilePath){
		this.graphFilePath = graphFilePath;
		System.out.println(new Date());
		dp = new DefaultDataProvider();
		sg = readSG();
		dijkstra = new Dijkstra(sg, -1);
		System.out.println(new Date());

	}

	public DataProvider getDataProvider() {
		return dp;
	}
	

	public Route calcPath(long startId, long endId){
		RouteableVertex startVertex = sg.getVertex(startId);
		RouteableVertex endVertex = sg.getVertex(endId);
		List<RouteableVertex> rvlist;
		Route route = null;		
		try {
			route = dijkstra.getShortestPath(startVertex, endVertex);		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return route;
	}
	
	

	public Set<RouteableVertex> getAllVertices() {
		
		return sg.getVertices();
	}
	
	
	private static StreetGraph readSG() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(graphFilePath));
			StreetGraph sg  = (StreetGraph) ois.readObject();
			sg.setDataprovider(new DefaultDataProvider());
			ois.close();	
			return sg;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getStreetnameForVertex(RouteableVertex vertex) {
		TreeSet<String> streetnames = new TreeSet<String>();
		String streetname = "";
		for(StreetEdge edge : sg.edgesOf(vertex))
			if(edge.hasStreetname())
				streetnames.add(edge.getStreetname());
		
		for(String streetnamepart : streetnames){
			if(streetname == null || streetname.equals(""))
				streetname += streetnamepart;
			else
				streetname += " Ecke " + streetnamepart;
		}
		
		return streetname;
	}

	public LatLon getPositionOf(long osmNodeId) {
		return sg.getVertex(osmNodeId).getPosition();
	}

	public ArrayList<Route> calcRadiusSearch(long emergencyNodeId, ArrayList<Vehicle> vehicles) {
		HashSet<RouteableVertex> toFindVehicles = new HashSet<RouteableVertex>();
		RouteableVertex start = sg.getVertex(emergencyNodeId);
		for(Vehicle v : vehicles)
			toFindVehicles.add(sg.getVertex(v.getPosition()));
		
		try {
			return dijkstra.radiusSearch(start, toFindVehicles, 1000000);//TODO: Radius anpassen
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null; 
		
	}
}
