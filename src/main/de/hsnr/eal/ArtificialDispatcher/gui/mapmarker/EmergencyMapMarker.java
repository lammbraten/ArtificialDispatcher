package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import org.jxmapviewer.viewer.GeoPosition;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;

public class EmergencyMapMarker extends AbstractMapMarker{
	private Emergency emergency;

	public EmergencyMapMarker(Emergency emergency, GeoPosition coord) {
		super(coord);
		this.setEmergency(emergency);
		this.panel = new EmergencyPanelMapMarker(emergency.getEmergencyType());
		panel.moreBtn.addMouseListener(new VehicleMapMarkerMouseListener());

		
	}

	public Emergency getEmergency() {
		return emergency;
	}

	public void setEmergency(Emergency emergency) {
		this.emergency = emergency;
	}
	
	private class VehicleMapMarkerMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(panel.getMoreButton(), emergency);
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
