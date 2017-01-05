package de.hsnr.eal.ArtificialDispatcher.graph.weights;

import java.io.Serializable;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;

public interface Heuristic extends Serializable {
	public double heuristicForVertex(RouteableVertex v);
	public void setReferenceVertex(RouteableVertex v);
}
