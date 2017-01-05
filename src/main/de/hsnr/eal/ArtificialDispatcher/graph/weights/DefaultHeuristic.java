package de.hsnr.eal.ArtificialDispatcher.graph.weights;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;

public class DefaultHeuristic extends CrowFliesTimeHeuristic {

	private static final long serialVersionUID = 4484496247154784983L;
	private static int ESTIMATED_AVERAGE_SPEED = 1;
	private static float WEIGHTING =  1;
	
	public DefaultHeuristic(RouteableVertex endVertex) {
		super(endVertex, ESTIMATED_AVERAGE_SPEED, WEIGHTING);
	}
}
