package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

import org.jxmapviewer.viewer.GeoPosition;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class VehicleMapMarker extends AbstractMapMarker{
	private Vehicle vehicle;

	public VehicleMapMarker(Vehicle vehicle, GeoPosition coord) {
		super(coord);
		this.setVehicle(vehicle);
		this.panel = new VehiclePanelMapMarker(vehicle);
		panel.moreBtn.addMouseListener(new VehicleMapMarkerMouseListener());

		
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	private class VehicleMapMarkerMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(panel.getMoreButton(), vehicle);
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
