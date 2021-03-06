package de.hsnr.eal.ArtificialDispatcher.data.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.WeightFunction;
import de.spinosm.common.Common;
import de.spinosm.common.Vehicle;
import de.westnordost.osmapi.map.data.LatLon;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Way;

abstract class AbstractProvider implements DataProvider, OsmDataHandler {
	
	protected WeightFunction weightFunction;
	
	public WeightFunction getWeightFunction() {
		return weightFunction;
	}

	public void setWeightFunction(WeightFunction weightFunction) {
		this.weightFunction = weightFunction;
	}

	protected RouteableVertex buildNewStreetVertex(OsmNode osmNode) {
		return new StreetVertex(osmNode);
	}
	
	protected Set<StreetEdge> getRouteableEdgesForVertex(RouteableVertex rv) {
		Set<StreetEdge> waysFromNode = new HashSet<StreetEdge>();
		List<Way> ways = this.getWaysForNode(rv.getId());
		for(Way way : ways){
			try{
				waysFromNode.addAll(parseToStreetEdge(rv, way));
			}catch(Exception e){
				//System.out.println(e.getMessage());
			}
		}
		return waysFromNode;

	}

	protected boolean canBeRouteableVertex(Node node) {
		List<Way> waysOfNode = this.getWaysForNode(node.getId());
		if(waysOfNode.size() >= 2)
			return hasAnotherRoute(waysOfNode);
		return false;
	}

	protected List<StreetEdge> parseToStreetEdge(RouteableVertex rv, Way way) {
		List<StreetEdge> edges = new LinkedList<StreetEdge>();
		List<Long> nids = way.getNodeIds();
		List<Node> nodes =  this.getWayNodesComplete(way.getId(), nids);
		for(Node node : nodes){
			if(rv.getId() == node.getId()){
				if(isUseableDownTheRoad(way)){
					try {edges.add(shapeNewOutgoingEdgeDownTheRoad(way, rv, nodes, node));
					}catch (Exception e) {}
					try {edges.add(shapeNewIncomingEdgeDownTheRoad(way, rv, nodes, node));
					}catch (Exception e) {}
				}
				if(isUseableUpTheRoad(way)){
					try {edges.add(shapeNewOutgoingEdgeUpTheRoad(way, rv, nodes, node));
					}catch (Exception e) {}
					try {edges.add(shapeNewIncomingEdgeUpTheRoad(way, rv, nodes, node));	
					}catch (Exception e) {}
				}
			}
		}
		return edges;
	}

	/**
	 * Had to write this because OSM-Delivers for request for multiply nodes the nodes not ordered
	 * OSM does this even for GET way/<id\>/complete
	 * @param nodes - The unordered array
	 * @param nids - Nodes should be in this order
	 * @return the ordered List of Nodes
	 */
	protected List<Node> inWayOrder(List<Node> nodes, List<Long> nids) {
		ArrayList<Node> orderedList = new ArrayList<Node>();
		
		for(long nid : nids)
			for(Node node : nodes)
				if(node.getId() == nid)
					orderedList.add(node);				
		
		return orderedList;
	}

	protected double calcCost(Way way, LinkedList<Node> shapingNodes) {
		LinkedList<LatLon> nodes = new LinkedList<LatLon>();
		for(Node node : shapingNodes)
			nodes.add(node.getPosition());
		return weightFunction.calcCosts(nodes, way);
	}

	/**
	 * @param waysOfNode
	 */
	protected boolean hasAnotherRoute(List<Way> waysOfNode) {
		int routeableWaysCounter = 0;	
		for(Way way: waysOfNode){
			if(Common.wayIsUseable(way, Vehicle.FIRETRUCK))
				routeableWaysCounter++;
			if(routeableWaysCounter >= 2)
				return true;
		}
		return false;
	}

	private boolean isUseableUpTheRoad(Way way) {
		if(way.getTags().containsKey("oneway")){
			String val = way.getTags().get("oneway");
			if(val.equals("yes"))
				return true;
			else if(val.equals("1"))
				return true;
			else if(val.equals("true"))
				return true;
			else 
				return false;
		}
		return true;
	}

	private boolean isUseableDownTheRoad(Way way) {
		if(way.getTags().containsKey("oneway")){
			String val = way.getTags().get("oneway");
			if(val.equals("-1"))
				return true;
			else if(val.equals("reverse"))
				return true;
			else 
				return false;
		}
		return true;
	}

	private StreetEdge shapeNewOutgoingEdgeUpTheRoad(Way way, RouteableVertex sj, List<Node> nodes, Node node) throws Exception {
		LinkedList<Node> shapingNodes = new LinkedList<Node>();	
		shapingNodes.add(node);
		for(int i = nodes.indexOf(node)+1; i < nodes.size(); i++){
			shapingNodes.add(nodes.get(i));
			if(canBeRouteableVertex(nodes.get(i))){
				return new StreetEdge(sj, new StreetVertex((OsmNode) nodes.get(i)), calcCost(way, shapingNodes), getStreetname(way));
			}
		}
		throw new Exception("No junction found");
	}

	private StreetEdge shapeNewOutgoingEdgeDownTheRoad(Way way, RouteableVertex sj, List<Node> nodes, Node node) throws Exception {
		LinkedList<Node> shapingNodes = new LinkedList<Node>();	
		shapingNodes.add(node);
		for(int i = nodes.indexOf(node)-1; i >= 0; i--){
			shapingNodes.add(nodes.get(i));
			if(canBeRouteableVertex(nodes.get(i))){
				return new StreetEdge(sj, new StreetVertex((OsmNode) nodes.get(i)), calcCost(way, shapingNodes), getStreetname(way));
			}
		}
		throw new Exception("No junction found");
	}
	
	private StreetEdge shapeNewIncomingEdgeUpTheRoad(Way way, RouteableVertex endingNode, List<Node> nodes, Node node) throws Exception {
		LinkedList<Node> shapingNodes = new LinkedList<Node>();	
		shapingNodes.add(node);
		for(int i = nodes.indexOf(node)-1; i >= 0; i--){
			shapingNodes.add(nodes.get(i));
			if(canBeRouteableVertex(nodes.get(i))){
				return new StreetEdge(new StreetVertex((OsmNode) nodes.get(i)), endingNode, calcCost(way, shapingNodes), getStreetname(way));
			}
		}
		throw new Exception("No junction found");
	}

	private StreetEdge shapeNewIncomingEdgeDownTheRoad(Way way, RouteableVertex endingNode, List<Node> nodes, Node node) throws Exception {
		LinkedList<Node> shapingNodes = new LinkedList<Node>();	
		shapingNodes.add(node);
		for(int i = nodes.indexOf(node)+1; i < nodes.size(); i++){
			shapingNodes.add(nodes.get(i));
			if(canBeRouteableVertex(nodes.get(i))){
				return new StreetEdge(new StreetVertex((OsmNode) nodes.get(i)), endingNode, calcCost(way, shapingNodes), getStreetname(way));
			}
		}
		throw new Exception("No junction found");
	}
	
	private String getStreetname(Way way) {
		return 	way.getTags().get("name");
	}
}
