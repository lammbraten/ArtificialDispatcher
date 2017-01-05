package de.hsnr.eal.ArtificialDispatcher.graph.algorithm;

import java.util.HashSet;
import java.util.Observable;
import java.util.TreeSet;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;
import de.hsnr.eal.ArtificialDispatcher.graph.pattern.IdComparator;

public class BreadthFirstSearch extends Observable {

	private long maxVertices;
	private StreetGraph graph;
	private TreeSet<RouteableVertex> toVisitVertices;
	private HashSet<RouteableVertex>  vistitedVertices;

	
	public BreadthFirstSearch(StreetGraph g, long id, long maxIterations){
		this.graph = g;
		setMaxVertices(maxIterations);
		vistitedVertices = new HashSet<RouteableVertex>();
		toVisitVertices = new TreeSet<RouteableVertex>(new IdComparator());		
		RouteableVertex s = g.getVertex(id);
		mark(s);
	}
	
	public void searchBraedthFirst(){
		long iterations = 0;
		while(!toVisitVertices.isEmpty() && maxVertices > iterations){
			RouteableVertex u = toVisitVertices.pollFirst();
			
			setChanged();
			notifyObservers(u);		
			
			vistitedVertices.add(u);			
			mark(u);
			iterations++;
			}
	}
	
	private void mark(RouteableVertex s){
		for(StreetEdge e : graph.edgesOf(s, 1)){
			RouteableVertex v = e.getOtherVertexThan(s);
			if(!vistitedVertices.contains(v)){
				toVisitVertices.add(v);
			}
		}
	}
	
	private void setMaxVertices(long maxDepth) {
		if(maxDepth < 0)
			this.maxVertices = Long.MAX_VALUE;
		else
			this.maxVertices = maxDepth;
	}
}

