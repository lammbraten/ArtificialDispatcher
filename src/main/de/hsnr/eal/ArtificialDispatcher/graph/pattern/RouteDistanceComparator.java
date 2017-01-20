package de.hsnr.eal.ArtificialDispatcher.graph.pattern;

import java.util.Comparator;

import de.hsnr.eal.ArtificialDispatcher.graph.Route;


public class RouteDistanceComparator implements Comparator<Route>  {

	@Override
	public int compare(Route o1, Route o2) {
		if (o1.getRouteTimeDistance() == o2.getRouteTimeDistance() )
			return 0;
		if (o1.getRouteTimeDistance() < o2.getRouteTimeDistance())
			return -1;
		else
			return 1;
	}

}
