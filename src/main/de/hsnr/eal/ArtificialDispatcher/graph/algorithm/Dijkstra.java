package de.hsnr.eal.ArtificialDispatcher.graph.algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;
import de.hsnr.eal.ArtificialDispatcher.graph.pattern.DistanceComparator;
import de.hsnr.eal.ArtificialDispatcher.graph.pattern.IdComparator;
import de.hsnr.eal.ArtificialDispatcher.graph.pattern.RouteDistanceComparator;

public class Dijkstra extends ObservableShortestPath {
	private static final long serialVersionUID = 8742913011912127748L;
	protected StreetGraph graph; 
	protected HashSet<RouteableVertex> visitedVertices;
	protected PriorityQueue<RouteableVertex> toVisitVertices;
	protected RouteableVertex startVertex;
	protected RouteableVertex endVertex;
	protected int direction;
	private TreeMap<RouteableVertex, RouteableVertex> shortestPathMap;
	private LinkedList<RouteableVertex> calculatetdPath;
	private int radius;	
	private HashSet<RouteableVertex> toFind;
	
	
	public Dijkstra(StreetGraph streetgraph) {
		this(streetgraph, StreetGraph.DEFAULT_DIRECTION);
	}
	
	public Dijkstra(StreetGraph streetgraph, int direction){
		this.graph = streetgraph;
		this.direction = direction;
		visitedVertices = new HashSet<RouteableVertex>();
		toVisitVertices = new PriorityQueue<RouteableVertex>(new DistanceComparator());		
		shortestPathMap = new TreeMap<RouteableVertex, RouteableVertex>(new IdComparator());
	}
	
	public Dijkstra(StreetGraph streetgraph, HashSet<RouteableVertex> visitedVertecies, PriorityQueue<RouteableVertex> toVisitVertecies) {
		this.graph = streetgraph;
		this.visitedVertices = visitedVertecies;
		this.toVisitVertices = toVisitVertecies;
	}

	@Override
	public List<RouteableVertex> getShortestPath(RouteableVertex start, RouteableVertex end) throws Exception {
		endVertex = end;		
		init(start);

		return iterateThrougGraph();
	}
	
	/**
	 * finds routes for given RoutableVertices in a defined radius.
	 * Dijkstra-variant.
	 * @param start Vertex to start with
	 * @param toFind Vertices to find routes for
	 * @param radius distance to search in
	 * @return An arraylist of found routes matchen to the given vertices.
	 * @throws Exception when there is no route to calc
	 */
	public ArrayList<Route> radiusSearch(RouteableVertex start, HashSet<RouteableVertex> toFind, int radius) throws Exception{
		this.toFind = toFind;
		this.radius = radius;
		reset();
		init(start);
		
		return searchRadius();
	}
	
	private ArrayList<Route> searchRadius() throws Exception{
		ArrayList<Route> foundRoutes = new ArrayList<Route>();
		RouteableVertex actVertex = null;
		double actRadius = 0;
		
		while(!toVisitVertices.isEmpty() ){
			if(actRadius > radius || toFind.isEmpty()){
				foundRoutes.sort(new RouteDistanceComparator());
				return foundRoutes;
			}
			actVertex = checkNextVertex();
			actRadius = actVertex.getDistance();
			if(toFind.contains(actVertex)){
				foundRoutes.add(buildNewRoute(actVertex));
				toFind.remove(actVertex);
			}
		}
		throw new Exception("No Radius found! ");
	}
	


	private Route buildNewRoute(RouteableVertex actVertex) {
		return new Route(buildShortestPathTo(actVertex));
	}

	/**
	 * @return
	 * @throws Exception 
	 */
	List<RouteableVertex> iterateThrougGraph() throws Exception {
		int i = 0;
		while(!toVisitVertices.isEmpty()){
			if(isEndVertexFound())
				return buildShortestPathTo(endVertex);
			checkNextVertex();
			i++;
		}
		throw new Exception("No Path found! " + i);
	}
	
	RouteableVertex checkNextVertex() {
		RouteableVertex u = toVisitVertices.poll();
		visitedVertices.add(u);
		setChanged();
		notifyObservers(u);		
		
		for(StreetEdge e : graph.edgesOf(u, direction)){
			RouteableVertex v = e.getOtherVertexThan(u);
			if(!visitedVertices.contains(v)){
				try {				
					if(toVisitVertices.contains(v)){					
						decraeseValueIfLower(u, v, e.getWeight());
					}else{
						insertNewValue(u, v, e.getWeight());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return u;
	}
	
	void decraeseValueIfLower(RouteableVertex u, RouteableVertex v, double weight) throws Exception {
		RouteableVertex alreadyFoundV = shortestPathMap.ceilingKey(v);
		if(!alreadyFoundV.equals(v) || alreadyFoundV == null || u == null)
			throw new Exception("Something went wrong");
		if(alreadyFoundV.getDistance() > (u.getDistance() + weight)){			
			alreadyFoundV.setDistance(u.getDistance() + weight);	
			if(shortestPathMap.put(alreadyFoundV, u) == null)
				throw new Exception("Something went wrong");
		}
	}

	void insertNewValue(RouteableVertex u, RouteableVertex v, double weight) throws Exception {
		v.setDistance(u.getDistance() + weight);						
		toVisitVertices.add(v);
		if(shortestPathMap.put(v, u) != null)
			throw new Exception("Something went wrong");
	}



	LinkedList<RouteableVertex> buildShortestPathTo(RouteableVertex endVertex2) {
		//writeToLogFile(shortestPathMap.descendingMap());
		RouteableVertex v = shortestPathMap.get(endVertex2);
		LinkedList<RouteableVertex> returnValue = new LinkedList<RouteableVertex>();	
		returnValue.add(endVertex2);
		returnValue.add(v);
		while(v.getId() != startVertex.getId()){
			v = shortestPathMap.get(v);
			if(v == null)
				break;
			returnValue.add(v);
		}
		calculatetdPath = returnValue;
		return returnValue;
	}

	void init(RouteableVertex start){
		startVertex = start;
		startVertex.setDistance(0);
		graph.edgesOf(startVertex, direction);
		toVisitVertices.add(graph.getVertex(startVertex.getId()));
	}
	
	private void reset() {
		visitedVertices = new HashSet<RouteableVertex>();
		toVisitVertices = new PriorityQueue<RouteableVertex>(new DistanceComparator());	
		
		this.shortestPathMap = new TreeMap<RouteableVertex, RouteableVertex>(new IdComparator());

	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
		
	}

	@Override
	public StreetGraph getGraph() {
		return graph;
	}
	
	@Override
	public List<RouteableVertex> getBorderVertices() {
		return  new ArrayList<RouteableVertex>(toVisitVertices);
	}

	@Override
	public List<RouteableVertex> getFinishedVertices() {
		return new ArrayList<RouteableVertex>(visitedVertices);
	}
	
	@Override
	public HashSet<RouteableVertex> getVisitedVertices() {
		return visitedVertices;
	}

	@Override
	public void setVisitedVertices(HashSet<RouteableVertex> visitedVertices) {
		this.visitedVertices = visitedVertices;
	}

	
	public RouteableVertex getStartVertex() {
		return startVertex;
	}

	public void setStartVertex(RouteableVertex startVertex) {
		this.startVertex = startVertex;
	}

	public RouteableVertex getEndVertex() {
		return endVertex;
	}

	public void setEndVertex(RouteableVertex endVertex) {
		this.endVertex = endVertex;
	}

	@Override
	public List<RouteableVertex> getCalculatedShortestPath() {
		return calculatetdPath;
	}

	private boolean isEndVertexFound() {
		return toVisitVertices.peek().getId() == endVertex.getId();
	}
}
