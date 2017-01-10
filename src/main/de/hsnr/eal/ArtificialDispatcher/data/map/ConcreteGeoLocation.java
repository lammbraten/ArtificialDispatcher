package de.hsnr.eal.ArtificialDispatcher.data.map;

public class ConcreteGeoLocation implements GeoLocation {
	private static final String DEFAULT_STREETNAME = "Unbekannter Straﬂename";
	private long osmNodeId;
	private String streetname;
	
	ConcreteGeoLocation(long osmNodeId){
		this(osmNodeId, DEFAULT_STREETNAME);		
	}

	public ConcreteGeoLocation(long osmNodeId, String streetname) {
		this.osmNodeId = osmNodeId;
		if(streetname == null || streetname != "")
			this.streetname = streetname;
		else
			this.streetname = DEFAULT_STREETNAME;
	}

	@Override
	public long getOsmNodeId() {
		return osmNodeId;
	}

	@Override
	public String getStreetname() {
		return streetname;
	}
	
	@Override
	public String toString(){
		return streetname + " (" + osmNodeId + ")";
	}

}
