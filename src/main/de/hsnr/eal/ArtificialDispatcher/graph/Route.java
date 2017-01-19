package de.hsnr.eal.ArtificialDispatcher.graph;

import java.util.LinkedList;
import java.util.List;

public class Route {

	LinkedList<RouteableVertex> vertices;
	private long targetNodeId;
	
	public Route(LinkedList<RouteableVertex> vertices){
		this.vertices = vertices;		
		this.targetNodeId = vertices.getLast().getId();
	}
	
	public List<Long> getNodeIds() {
		LinkedList<Long> nodeIds = new LinkedList<Long>();
		for(RouteableVertex rv : vertices)
			nodeIds.add(rv.getId());
		return nodeIds;
	}
	
	public double getRouteDistance(){
		return vertices.getLast().getDistance();
	}

	/**
	 * Zum fahren
	 * @param distance
	 * @param iNode
	 * @return
	 */
	public Tuple<Long, Double> findNearestNodeFor(double distance, int iNode) {
		double actDistance = vertices.get(iNode).getDistance();
		double maxDistance = actDistance + distance;
		
		for(int i = iNode; i < vertices.size(); i++){
			if(vertices.get(i).getId() == targetNodeId)
				return new Tuple<Long, Double>(targetNodeId, 0.0);

			if(vertices.get(i).getDistance() > maxDistance)
				return new Tuple<Long, Double>(vertices.get(i-1).getId(), (maxDistance - vertices.get(i-1).getDistance()));
		}
		throw new IllegalArgumentException("No Node Found!");
	}

	public long getTargetNodeId() {
		return targetNodeId;
	}

	public void setTargetNodeId(long targetNodeId) {
		this.targetNodeId = targetNodeId;
	}

}
