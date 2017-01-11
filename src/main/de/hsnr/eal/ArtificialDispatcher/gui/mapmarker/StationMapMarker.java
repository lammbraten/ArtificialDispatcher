package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jxmapviewer.viewer.GeoPosition;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.StationType;

public class StationMapMarker extends AbstractMapMarker{
	private Station station;
	
	public StationMapMarker(Station station, GeoPosition coord) {
		super(coord);
		this.setPosition(coord);
		this.station = station;
		this.panel = new StationPanelMapMarker(station.getName(), station.getType()); 
		panel.moreBtn.addMouseListener(new StationMapMarkerMouseListener());
		
	}
	
    public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	private class StationMapMarkerMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(panel.getMoreButton(), station);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

}
