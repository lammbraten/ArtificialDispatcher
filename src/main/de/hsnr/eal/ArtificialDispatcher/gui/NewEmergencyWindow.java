package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

import de.hsnr.eal.ArtificialDispatcher.data.map.ConcreteGeoLocation;
import de.hsnr.eal.ArtificialDispatcher.data.map.GeoLocation;
import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.VehicleType;
import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.graph.StreetEdge;

public class NewEmergencyWindow extends JDialog {


	private static final long serialVersionUID = -3023598463523579982L;
	private final JPanel contentPanel = new JPanel();
	private MapLoader ml;
	Set<RouteableVertex> vertices;

	/**
	 * Create the dialog.
	 * @param emergencyTypes 
	 * @param ml 
	 */
	public NewEmergencyWindow(List<EmergencyType> emergencyTypes, MapLoader ml) {
		this.ml = ml;
		loadAllGeolocations();
		
		setTitle("Neuen Einsatz er\u00F6ffnen");
		setBounds(100, 100, 450, 134);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel emergencyTypeLbl = new JLabel("Einsatzart: ");
		emergencyTypeLbl.setBounds(10, 13, 92, 14);
		contentPanel.add(emergencyTypeLbl);

		DefaultComboBoxModel<EmergencyType> emergencyTypeModel = new DefaultComboBoxModel<EmergencyType>();
		
		for(EmergencyType emt : emergencyTypes)
			emergencyTypeModel.addElement(emt);
		
		JComboBox<EmergencyType> emergencyComboBox = new JComboBox<EmergencyType>();
		emergencyComboBox.setModel(emergencyTypeModel);
		emergencyComboBox.setBounds(112, 10, 312, 20);
		contentPanel.add(emergencyComboBox);

		JLabel eventPlaceLbl = new JLabel("Einsatzort:");
		eventPlaceLbl.setBounds(10, 38, 92, 14);
		contentPanel.add(eventPlaceLbl);

		
		JComboBox eventPlaceComboBox = new JComboBox();
		eventPlaceComboBox.setModel(new DefaultComboBoxModel(new String[] {"Reinarztstra\u00DFe", "K\u00F6lner Stra\u00DFe"}));
		eventPlaceComboBox.setBounds(112, 35, 312, 20);
		contentPanel.add(eventPlaceComboBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void loadAllGeolocations() {
		this.vertices = ml.getAllVertices();
		List<GeoLocation> geolocations = buildGeolocations();
		for(GeoLocation geol : geolocations)
			System.out.println(geol);
		
		
		//System.out.println(vertices);
	}

	private List<GeoLocation> buildGeolocations() {
		ArrayList<GeoLocation> geolocations = new ArrayList<GeoLocation>();
		for(RouteableVertex vertex : vertices){
			String streetname = ml.getStreetnameForVertex(vertex);
			geolocations.add(new ConcreteGeoLocation(vertex.getId(), streetname));
		}
		return geolocations;
	}
	
	
	
	
	
	
}
