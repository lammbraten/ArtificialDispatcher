package de.hsnr.eal.ArtificialDispatcher.graph.data;

import java.util.Set;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;

public interface DataProvider {
	public RouteableVertex getVertex(long id);
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex startVertex);
}
