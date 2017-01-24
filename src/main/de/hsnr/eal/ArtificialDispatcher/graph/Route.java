package de.hsnr.eal.ArtificialDispatcher.graph;

import java.util.LinkedList;
import java.util.List;

public class Route {

	LinkedList<RouteableVertex> vertices;
	private long targetNodeId;
	private long startNodeId;
	
	public Route(LinkedList<RouteableVertex> vertices){
		this(vertices, 1);
	}
	
	public Route(LinkedList<RouteableVertex> vertices, int direction){
		this.vertices = new LinkedList<RouteableVertex>();
		for(RouteableVertex rv : vertices)
			this.vertices.add(new StreetVertex(rv));
		this.vertices = vertices;		
		this.targetNodeId = vertices.getLast().getId();
		this.startNodeId = vertices.getFirst().getId();
		if(direction > 0)
			invertRouteWeights();
	}
	
	public List<Long> getNodeIds() {
		LinkedList<Long> nodeIds = new LinkedList<Long>();
		for(RouteableVertex rv : vertices)
			nodeIds.add(rv.getId());
		return nodeIds;
	}
	
	public double getRouteDistance(){
		double firstDistance = vertices.getFirst().getDistance();
		double lastDistance = vertices.getLast().getDistance();
		if(firstDistance > lastDistance)
			return firstDistance;
		return lastDistance;
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
	
	public long getSartNodeId() {
		return startNodeId;
	}

	public void setStartNodeId(long startNodeId) {
		this.startNodeId = startNodeId;
	}
	
	public void invertRoute(){
		LinkedList<RouteableVertex> invertedRoute = new LinkedList<RouteableVertex>();

		invertRouteWeights();
		for(RouteableVertex rv: vertices)
			invertedRoute.addFirst(rv);
		
		
		this.vertices = invertedRoute;
		this.setStartNodeId(vertices.getFirst().getId());
		this.setTargetNodeId(vertices.getLast().getId());
	
	}

	public void invertRouteWeights() {
		double maxDistance = getRouteDistance();
		for(RouteableVertex rv: vertices){
			System.out.println(rv + " mDistance: " + maxDistance + "- actDistancd: " + rv.getDistance() + "new Distance: " + (maxDistance - rv.getDistance()));
			rv.setDistance(maxDistance - rv.getDistance());
		}

	}

}
