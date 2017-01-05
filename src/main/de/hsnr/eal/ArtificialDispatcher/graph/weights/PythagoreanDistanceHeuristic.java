package de.hsnr.eal.ArtificialDispatcher.graph.weights;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.spinosm.common.Common;

public class PythagoreanDistanceHeuristic implements Heuristic {

	private static final long serialVersionUID = -1695693672868668134L;
	protected RouteableVertex reference;
	protected float weight;

	public PythagoreanDistanceHeuristic(RouteableVertex endVertex, float weight){
		this.reference = endVertex;
		this.weight = weight;	
	}
	
	@Override
	public double heuristicForVertex(RouteableVertex v) {
		return Common.PythagoreanDistance(reference.getPosition(), v.getPosition()) *weight;
	}

	@Override
	public void setReferenceVertex(RouteableVertex v) {
		this.reference = v;
	}

}
