package de.hsnr.eal.ArtificialDispatcher.data.map;

import java.util.Date;
import java.util.List;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.Dijkstra;
import de.hsnr.eal.ArtificialDispatcher.graph.algorithm.ShortestPath;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.PythagoreanDistanceWeight;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.WeightFunction;

public class MapLoader {
	private static String DEFAULT_MAP_SOURCE = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Daten\\Roh\\Krefeld_Streetmap.osm";
	private static WeightFunction WEIGHTFUNCTION =  new PythagoreanDistanceWeight();
	
	private DataProvider dp;
	private StreetGraph sg;
	private ShortestPath sp;
	
	public MapLoader(){
		this(DEFAULT_MAP_SOURCE);
	}
	
	public MapLoader(String osmFilePath){
		System.out.println(new Date());
		dp = new LocalProvider(osmFilePath, WEIGHTFUNCTION);
		sg = new StreetGraph(dp);
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
	
	//TODO: Umkreissuche 
}
