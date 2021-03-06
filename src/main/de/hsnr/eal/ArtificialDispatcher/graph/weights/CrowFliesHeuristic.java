package de.hsnr.eal.ArtificialDispatcher.graph.weights;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.spinosm.common.Common;

public class CrowFliesHeuristic implements Heuristic{

	private static final long serialVersionUID = -2029496330097990129L;
	protected RouteableVertex reference;
	protected float weight;
	
	public CrowFliesHeuristic(RouteableVertex endVertex, float weight) {
		this.reference = endVertex;
		this.weight = weight;
	}

	@Override
	public double heuristicForVertex(RouteableVertex v) {
		return Common.asTheCrowFlies(reference.getPosition(), v.getPosition()) *weight;
	}

	@Override
	public void setReferenceVertex(RouteableVertex v) {
		this.reference = v;
	}
	
}
