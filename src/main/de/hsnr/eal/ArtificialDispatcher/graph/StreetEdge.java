package de.hsnr.eal.ArtificialDispatcher.graph;

public class StreetEdge extends DirectedEdge {
	private static final long serialVersionUID = -6282043762165569240L;
	
	private String streetname;

	public StreetEdge(RouteableVertex source, RouteableVertex target, double cost, String streetname) {
		super(source, target, cost);
		this.setStreetname(streetname);
	}

	public StreetEdge(RouteableVertex source, RouteableVertex target, double cost) {
		this(source, target, cost, null);
	}

	public String getStreetname() {
		return streetname;
	}

	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}

	public boolean hasStreetname() {
		if(streetname == null || streetname.equals(""))
			return false;
		return true;
	}
}
