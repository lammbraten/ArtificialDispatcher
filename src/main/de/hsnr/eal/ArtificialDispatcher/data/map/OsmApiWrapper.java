package de.hsnr.eal.ArtificialDispatcher.data.map;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;
import de.hsnr.eal.ArtificialDispatcher.graph.weights.WeightFunction;
import de.westnordost.osmapi.OsmConnection;
import de.westnordost.osmapi.common.errors.OsmConnectionException;
import de.westnordost.osmapi.map.MapDataDao;
import de.westnordost.osmapi.map.data.Node;
import de.westnordost.osmapi.map.data.OsmNode;
import de.westnordost.osmapi.map.data.Relation;
import de.westnordost.osmapi.map.data.Way;
import de.westnordost.osmapi.map.handler.ListOsmElementHandler;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;

public class OsmApiWrapper extends AbstractProvider{

	private static final String SECURE_OSM_API_URL = "https://api.openstreetmap.org/api/0.6/";
	private static final String OSM_API_URL = "http://openstreetmap.org/api/0.6/";
	private static final String OSM_TEST_API_URL = "http://api06.dev.openstreetmap.org";
	private static final String XAPI = "http://informationfreeway.org/api/0.6/";
	private static final String OVERPASS_TURBO = "//overpass-api.de/api/";
	private static final String USER_AGENT = "SPinOSM";
	private static final int TIMEOUT = 10000; //10 secs
	private static final int MAX_ATTEMPTS = 5;
	private static final OAuthConsumer OSM_AUTH= null;
	
	private OsmConnection osm;
	private MapDataDao mddao;
	
	private OsmElementCache<Node> osmNodeListBuffer;
	private OsmElementCache<Way> osmWaysOfNodeBuffer;
	
	public OsmApiWrapper(WeightFunction weightFunction){
		OAuthConsumer auth = new DefaultOAuthConsumer("CuPCn3sRc8FDiepAoSkH4a9n7w2QuqVCykStfVPG", 
				"D1nX6BF1NMAZtIq8ouGJJ7zGtSaTRDTz8QfZl5mo");
		auth.setTokenWithSecret(
				"2C4LiOQBOn96kXHyal7uzMJiqpCsiyDBvb8pomyX",
				"1bFMIQpgmu5yjywt3kknopQpcRmwJ6snDDGF7kdr");
		this.osm = new OsmConnection(OSM_API_URL, USER_AGENT, auth);
		this.mddao = new MapDataDao(osm);

		osmNodeListBuffer = new OsmElementCache<Node>();
		osmWaysOfNodeBuffer = new OsmElementCache<Way>();
		
		this.weightFunction = weightFunction;
	}
	
	@Override
	public RouteableVertex getVertex(long id) {
		RouteableVertex returnValue;
		OsmNode osmNode = (OsmNode) this.getNode(id);
		if(osmNode == null){
			throw new IllegalArgumentException("Node not existing in OSM");
		}else{
			returnValue = buildNewStreetVertex(osmNode);
		}
		return returnValue;
	}

	@Override
	public Set<StreetEdge> getStreetEdgesForVertex(RouteableVertex sj) {
		Set<StreetEdge> returnValue;
		if(sj == null){
			throw new IllegalArgumentException("Node not existing in OSM");
		}else{
			returnValue = getRouteableEdgesForVertex(sj);
		}
		return returnValue;
	}

	@Override
	public List<Node> getWayNodesComplete(long id, List<Long> nids) {
		if(osmNodeListBuffer.contains(id))
			return getWayCompleteFromBuffer(id);
		return getWayCompleteFromServer(id, nids);
	}

	@Override
	public List<Way> getWaysForNode(long id){
		if(osmWaysOfNodeBuffer.contains(id))
			return getWaysOfNodeFromBuffer(id);
		return getWayForNodeFromServer(id);
	}

	public Node getNode(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getNode(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Node> getNodes(List<Long> nodeIds){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++){
			try{return inWayOrder(mddao.getNodes(nodeIds), nodeIds);}catch(OsmConnectionException e){}			
		}
		return null;
	}
	
	public Way getWay(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getWay(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Way> getWays(Collection<Long> wayIds){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getWays(wayIds);}catch(OsmConnectionException e){}
		return null;
	}
	
	public Relation getRelation(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelation(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelations(Collection<Long> relationIds){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelations(relationIds);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelationsForNode(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelationsForNode(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelationsForRelation(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelationsForRelation(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public List<Relation> getRelationsForWay(long id){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return mddao.getRelationsForWay(id);}catch(OsmConnectionException e){}
		return null;
	}
	
	public OsmConnection getConnection(){
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{return osm;}catch(OsmConnectionException e){}
		return null;
	}

	private List<Node> getWayCompleteFromBuffer(long id) {
		return osmNodeListBuffer.getElementList(id);
	}

	/**
	 * @param id
	 * @param nids 
	 * @return
	 */
	private List<Node> getWayCompleteFromServer(long id, List<Long> nids) {
		ListOsmElementHandler<Node>  mdh = new ListOsmElementHandler<Node>(Node.class);
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{
				mddao.getWayComplete(id, mdh);
				break;
			}catch(OsmConnectionException e){}
		osmNodeListBuffer.addElementList(id, inWayOrder(mdh.get(), nids));
		return inWayOrder(mdh.get(), nids);
	}
	
	/**
	 * @param id
	 * @return 
	 */
	private List<Way> getWaysOfNodeFromBuffer(long id) {
		return osmWaysOfNodeBuffer.getElementList(id);
	}

	/**
	 * @param id
	 * @return 
	 */
	private List<Way> getWayForNodeFromServer(long id) {
		for(int attempt = 0; attempt < MAX_ATTEMPTS; attempt++)
			try{
				List<Way> returnValue= mddao.getWaysForNode(id);
				osmWaysOfNodeBuffer.addElementList(id, returnValue);
				return returnValue;
			}catch(OsmConnectionException e){}
		return null;
	}
}
