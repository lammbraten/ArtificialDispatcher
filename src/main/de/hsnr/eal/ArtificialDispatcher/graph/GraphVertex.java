package de.hsnr.eal.ArtificialDispatcher.graph;

import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.OsmLatLon;



public abstract class GraphVertex implements RouteableVertex {

	public static double DISTANCE_INIT_VALUE = Double.MAX_VALUE;
	private static final long serialVersionUID = 1307414385237284029L;
	private long id;
	private double distance;
	private double heuristic;
	private OsmLatLon position; //needed for Heuristic
	
	public GraphVertex(long id, LatLon latLon, double distance){
		this.setId(id);
		this.setDistance(distance);
		this.position = new OsmLatLon(latLon.getLatitude(), latLon.getLongitude());
	}
	
	public GraphVertex(long id, LatLon latLon){
		this.setId(id);
		this.setDistance(DISTANCE_INIT_VALUE);
		this.setHeuristic(DISTANCE_INIT_VALUE);
		if(latLon != null)
			this.position = new OsmLatLon(latLon.getLatitude(), latLon.getLongitude());
	}
	
	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	@Override
	public OsmLatLon getPosition() {
		return position;
	}

	public void setPosition(LatLon latLon) {
		this.position = new OsmLatLon(latLon.getLatitude(), latLon.getLongitude());
	}
	
	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public void setDistance(double distance) {
		if(distance < 0)
			throw new IllegalArgumentException("Distance must be positive!");
		this.distance = distance;
	}

	@Override
	public double getHeuristic() {
		return heuristic;
	}

	@Override
	public void setHeuristic(double heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	public boolean hasSameId(RouteableVertex n) {
		return (this.getId() == n.getId());
	}

	@Override
	public boolean equals(Object other) {
		if(other == null)
			return false;
		if(this == other)
			return true;
		if(other instanceof GraphVertex){
			GraphVertex otherGraphNode = (GraphVertex) other;
			if(this.id == otherGraphNode.id)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "" + this.getId();
	}
	
	@Override
	public int compareTo(RouteableVertex other) {
		if(other == null )
			return 0;
		if(this.equals(other))
			return 0;
		if(this.getDistance() > other.getDistance()) 
			return 1;
		if(this.getDistance() < other.getDistance()) 
			return -1;
		return 0;
	}
	
	public void reset(){
		this.setDistance(DISTANCE_INIT_VALUE);
		this.setHeuristic(DISTANCE_INIT_VALUE);
	}
}
