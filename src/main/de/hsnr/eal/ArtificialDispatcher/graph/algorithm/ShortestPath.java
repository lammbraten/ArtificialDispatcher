package de.hsnr.eal.ArtificialDispatcher.graph.algorithm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hsnr.eal.ArtificialDispatcher.graph.Route;
import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetGraph;

public interface ShortestPath extends Serializable {
	public Route getShortestPath(RouteableVertex start, RouteableVertex end) throws Exception;
	public StreetGraph getGraph();
	public void setGraph(StreetGraph g);
	public List<RouteableVertex> getBorderVertices();
	public List<RouteableVertex> getFinishedVertices();
	public Set<RouteableVertex> getVisitedVertices();
	void setVisitedVertices(HashSet<RouteableVertex> visitedVertecies);
	public List<RouteableVertex> getCalculatedShortestPath();
	
}
