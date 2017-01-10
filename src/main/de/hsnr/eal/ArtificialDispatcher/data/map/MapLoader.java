package de.hsnr.eal.ArtificialDispatcher.data.map;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.Dijkstra;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.ShortestPath;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.PythagoreanDistanceWeight;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.WeightFunction;

public class MapLoader {
	private static String DEFAULT_MAP_SOURCE = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Daten\\Roh\\Krefeld_Streetmap.bin";
	private static WeightFunction WEIGHTFUNCTION =  new PythagoreanDistanceWeight();
	
	private DataProvider dp;
	private StreetGraph sg;
	private ShortestPath sp;
	private static String graphFilePath;
	
	public MapLoader(){
		this(DEFAULT_MAP_SOURCE);
	}
	
	public MapLoader(String graphFilePath){
		this.graphFilePath = graphFilePath;
		System.out.println(new Date());
		dp = new DefaultDataProvider();
		sg = readSG();
		sp = new Dijkstra(sg);
		System.out.println(new Date());

	}

	public DataProvider getDataProvider() {
		return dp;
	}
	
	//TODO: Calc Path from to
	public String calcPath(long startId, long endId){
		RouteableVertex startVertex = sg.getVertex(startId);
		RouteableVertex endVertex = sg.getVertex(endId);
		List<RouteableVertex> rvlist;
		String route = "";		
		try {
			rvlist = sp.getShortestPath(startVertex, endVertex);
			for(RouteableVertex rv : rvlist)
				route += rv.toString() + "\n";			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return route;
	}

	public Set<RouteableVertex> getAllVertices() {
		
		return sg.getVertices();
	}
	
	//TODO: Umkreissuche 
	
	
	
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
}
