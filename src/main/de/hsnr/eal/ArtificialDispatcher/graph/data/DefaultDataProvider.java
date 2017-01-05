package de.hsnr.eal.ArtificialDispatcher.graph.data;

import java.util.HashSet;
import java.util.Set;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;

public class DefaultDataProvider implements DataProvider {

	@Override
	public RouteableVertex getVertex(long id) {
		return null;
	}

	@Override
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex sj) {
		return new HashSet<StreetEdge>();
	}
}
