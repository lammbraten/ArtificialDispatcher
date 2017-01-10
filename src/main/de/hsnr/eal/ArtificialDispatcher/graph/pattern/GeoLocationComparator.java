package de.hsnr.eal.ArtificialDispatcher.graph.pattern;

import java.util.Comparator;

import de.hsnr.eal.ArtificialDispatcher.data.map.GeoLocation;

public class GeoLocationComparator implements Comparator<GeoLocation> {

	@Override
	public int compare(GeoLocation o1, GeoLocation o2) {
		if (o1.getStreetname() == o2.getStreetname())
			return 0;
		if (o1.getStreetname() == null)
			return -1;
		if (o2.getStreetname() == null)
			return 1;
		return o1.getStreetname().compareTo(o2.getStreetname());
	}

}
