package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

public class AbstractMapMarker extends DefaultWaypoint {
	
	protected AbstractJPanel panel;

	public AbstractMapMarker(GeoPosition coord) {
		super(coord);
	}

	public AbstractJPanel getPanel() {
		return panel;
	}
	
    class AbstractMapMarkerMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(panel.getMoreButton(), "You clicked on ");
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
