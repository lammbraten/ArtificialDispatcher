package de.hsnr.eal.ArtificialDispatcher.graph.algorithm;

import java.util.List;
import java.util.PriorityQueue;

import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;
import de.hsnr.eal.ArtificialDispatcher.graph.pattern.DistanceHeuristicComparator;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.DefaultHeuristic;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.Heuristic;

public class AStar extends Dijkstra {
	private static final long serialVersionUID = -8285289477827305700L;
	private Heuristic heuristic;	
	
	public AStar(StreetGraph streetgraph){
		this(streetgraph, null);
	}
	
	public AStar(StreetGraph streetgraph, Heuristic heuristic){
		super(streetgraph);
		this.heuristic = heuristic;	
		toVisitVertices = new PriorityQueue<RouteableVertex>(new DistanceHeuristicComparator());
	}
	
	@Override
	public Route getShortestPath(RouteableVertex start, RouteableVertex end) throws Exception {
		endVertex = end;		
		this.init(start);

		return new Route(iterateThrougGraph());
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
						v.setHeuristic(heuristicForVertex(v));
						insertNewValue(u, v, e.getWeight());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		return u;
	}

	void init(RouteableVertex start){
		if(heuristic == null)
			heuristic = new DefaultHeuristic(endVertex);	
		else
			heuristic.setReferenceVertex(endVertex);
		startVertex = start;
		startVertex.setDistance(0);
		startVertex.setHeuristic(heuristicForVertex(startVertex));
		graph.edgesOf(startVertex, StreetGraph.DEFAULT_DIRECTION);
		toVisitVertices.add(graph.getVertex(startVertex.getId()));

	}


	@Override
	public StreetGraph getGraph() {
		return this.graph;
	}

	@Override
	public void setGraph(StreetGraph g) {
		this.graph = g;
	}

	private double heuristicForVertex(RouteableVertex startVertex) {			
		return this.heuristic.heuristicForVertex(startVertex);
	}
}
