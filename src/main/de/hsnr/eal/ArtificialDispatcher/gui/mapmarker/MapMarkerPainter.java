package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.Graphics2D;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.WaypointPainter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class MapMarkerPainter extends WaypointPainter<AbstractMapMarker> {
	
	@Override
	protected void doPaint(Graphics2D g, JXMapViewer jxMapViewer, int width, int height) {
		for (AbstractMapMarker swingWaypoint : getWaypoints()) {
			Point2D point = jxMapViewer.getTileFactory().geoToPixel(
			        swingWaypoint.getPosition(), jxMapViewer.getZoom());
			Rectangle rectangle = jxMapViewer.getViewportBounds();
			int panelX = (int)(point.getX() - rectangle.getX());
			int panelY = (int)(point.getY() - rectangle.getY());
			JPanel panel = swingWaypoint.getPanel();
			panel.setLocation(panelX - panel.getWidth() / 2, panelY - panel.getHeight() / 2);
		}
	}

}
