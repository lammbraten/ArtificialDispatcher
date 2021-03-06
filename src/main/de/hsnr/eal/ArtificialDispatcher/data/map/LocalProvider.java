package de.hsnr.eal.ArtificialDispatcher.data.map;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.WeightFunction;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Way;

public class LocalProvider extends AbstractProvider{

	private File xmlFile;
	private OsmXmlFilerHandler osmxmlelements;
	
	public LocalProvider(String filePath, WeightFunction weightFunction){
		this.xmlFile = new File(filePath);
		osmxmlelements = new OsmXmlFilerHandler(xmlFile);
		this.weightFunction = weightFunction;
	}
	
	@Override
	public RouteableVertex getVertex(long id) {
		OsmNode n = (OsmNode) osmxmlelements.getNode(id);
		return 	buildNewStreetVertex(n);
	}

	@Override
	public List<Way> getWaysForNode(long id) {
		return osmxmlelements.getWaysForNode(id);
	}

	@Override
	public List<Node> getWayNodesComplete(long id, List<Long> nids) {
		LinkedList<Node> nodes = new LinkedList<Node>();
		for(long nid : nids)
			nodes.add(osmxmlelements.getNode(nid));
		return nodes;
	}

	@Override
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex sv) {
		Set<StreetEdge> returnValue;
		if(sv == null){
			throw new IllegalArgumentException("Node not existing in OSM");
		}else{
			returnValue = getRouteableEdgesForVertex(sv);
		}
		return returnValue;
	}

}
