package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListCellRenderer;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.MouseInputListener;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.data.prolog.PLDatabase;
import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.StationType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.graph.RouteableVertex;
import de.hsnr.eal.ArtificialDispatcher.gui.mapmarker.AbstractMapMarker;
import de.hsnr.eal.ArtificialDispatcher.gui.mapmarker.MapMarkerPainter;
import de.hsnr.eal.ArtificialDispatcher.gui.mapmarker.StationMapMarker;
import de.hsnr.eal.ArtificialDispatcher.gui.mapmarker.VehicleMapMarker;
import de.hsnr.eal.ArtificialDispatcher.gui.test.TestListCellRenderer.Item;
import de.hsnr.eal.ArtificialDispatcher.gui.test.TestListCellRenderer.ItemCellRenderer;
import de.westnordost.osmapi.map.data.LatLon;

import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JToolBar;

public class AppWindow {

	private JFrame mainFrame;

	private MapLoader ml;


	private MapMarkerPainter mapMarkerPainter;
	private HashSet<AbstractMapMarker> fireDepartmentComponents;
	private JXMapViewer mapViewer;

	
	
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Station> stations;
	private List<EmergencyType> emergencyTypes;


	/**
	 * Create the application.
	 * @param ml 
	 */
	public AppWindow(MapLoader ml, ArrayList<Vehicle> vehicles, ArrayList<Station> stations, List<EmergencyType> emergencyTypes) {
		this.ml = ml;
		this.vehicles = vehicles;
		this.stations = stations;
		this.emergencyTypes = emergencyTypes;
		
		initialize();
		mainFrame.setVisible(true);

	}


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Artificial Dispatcher / K\u00FCnstliche Leitstelle\r\n");
		mainFrame.setBounds(100, 100, 1920, 950);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// Setup JXMapViewer
		mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));

		
		
        DefaultListModel<Vehicle> vehicleModel = new DefaultListModel<Vehicle>();
        for(Vehicle vehicle : vehicles)
        	vehicleModel.addElement(vehicle);
        
        JList<Vehicle> vehicleList = new JList<Vehicle>(vehicleModel);
        vehicleList.setCellRenderer(new VehiclePanelRenderer());
        
		JList radioList = new JList();
		radioList.setModel(new AbstractListModel() {
			String[] values = new String[] {"Kommen ", "H\u00F6rt", "Verstande", "Moin Uwe"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		
		JScrollPane vehicleListScrollPane = new JScrollPane(vehicleList);
		vehicleListScrollPane.setToolTipText("Hallo\r\n");
		JScrollPane radioListScrollPane = new JScrollPane(radioList);	
		
		
		JPanel vehicleListPanel = new JPanel();
		vehicleListPanel.setLayout(new BorderLayout(0, 0));
		JLabel vehicleListLabel = new JLabel("Fahrzeuge");
		vehicleListPanel.add(vehicleListLabel, BorderLayout.NORTH);
		vehicleListPanel.add(vehicleListScrollPane, BorderLayout.CENTER);
		
		
		JPanel radioListPanel = new JPanel();
		radioListPanel.setLayout(new BorderLayout(0, 0));
		JLabel radioListLabel = new JLabel("Funk");
		radioListPanel.add(radioListLabel, BorderLayout.NORTH);		
		radioListPanel.add(radioListScrollPane, BorderLayout.CENTER);
		
		JSplitPane vehicleSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vehicleListPanel, radioListPanel);
		
		
		vehicleSplitPane.setOneTouchExpandable(true);
		vehicleSplitPane.setDividerLocation(450);
		vehicleSplitPane.setDividerSize(10);
		vehicleSplitPane.setPreferredSize(new Dimension(270, 500));

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(200, 250);
		vehicleListScrollPane.setMinimumSize(minimumSize);
		radioListScrollPane.setMinimumSize(minimumSize);
		mainFrame.getContentPane().setLayout(new BorderLayout(5, 5));
		
		
		mainFrame.getContentPane().add(vehicleSplitPane, BorderLayout.EAST);
		


		GeoPosition krefeld = new GeoPosition(51.33, 6.58);

		// Set the focus
		mapViewer.setZoom(7);
		mapViewer.setAddressLocation(krefeld);

		// Add interactions
		MouseInputListener mia = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mia);
		mapViewer.addMouseMotionListener(mia);

		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCursor(mapViewer));
			
		mainFrame.getContentPane().add(mapViewer, BorderLayout.CENTER);
		
		
		
		//MapMarker------------------------------
		HashSet<AbstractMapMarker> stationMarker = new HashSet<AbstractMapMarker>();

		for(Station station : stations){
			LatLon position = ml.getPositionOf(station.getOsmNode());
			stationMarker.add(new StationMapMarker(station, new GeoPosition(position.getLatitude(), position.getLongitude())));
		}

		HashSet<AbstractMapMarker> vehicleMarker = new HashSet<AbstractMapMarker>();

		for(Vehicle vehicle : vehicles){
			LatLon position = ml.getPositionOf(vehicle.getLocation());
			vehicleMarker.add(new VehicleMapMarker(vehicle, new GeoPosition(position.getLatitude(), position.getLongitude())));
		}


        // Set the overlay painter
		mapMarkerPainter = new MapMarkerPainter();
		fireDepartmentComponents = new HashSet<AbstractMapMarker>();
        
        addMapMarkerToMap(vehicleMarker);
        addMapMarkerToMap(stationMarker);
		
        mapMarkerPainter.setWaypoints(fireDepartmentComponents);
        mapViewer.setOverlayPainter(mapMarkerPainter);
        


		//MapMarker--------------------------------Ende
		
		

        DefaultListModel<Emergency> eventModel = new DefaultListModel<Emergency>();
        eventModel.addElement(new Emergency("Wohnungsbrand"));
        eventModel.addElement(new Emergency("Heckenbrand"));
        eventModel.addElement(new Emergency("Mülleimer"));
        
        JList<Emergency> list = new JList<Emergency>(eventModel);
        list.setCellRenderer(new EmergencyPanelRenderer());
        

		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BorderLayout(0, 0));
		eventPanel.add(list);
		JLabel eventLabel = new JLabel("Aktuelle Eins\u00E4tze");
		eventPanel.add(eventLabel, BorderLayout.NORTH);
		
		mainFrame.getContentPane().add(eventPanel, BorderLayout.WEST);

		
		JPanel viewSelectPanel = new JPanel();
		mainFrame.getContentPane().add(viewSelectPanel, BorderLayout.NORTH);
		viewSelectPanel.setMinimumSize(new Dimension(1900, 100));
		viewSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JToolBar eventSpawnerToolBar = new JToolBar();
		viewSelectPanel.add(eventSpawnerToolBar);
		
		EmergencySpawnerPanel evsPanel = new EmergencySpawnerPanel(emergencyTypes, ml);
		eventSpawnerToolBar.add(evsPanel);
		
		JToolBar tickControlToolBar = new JToolBar();
		viewSelectPanel.add(tickControlToolBar);
		
		TickControllPanel tcPanel = new TickControllPanel();
		tickControlToolBar.add(tcPanel);

		

	}

	private void addMapMarkerToMap(HashSet<AbstractMapMarker> marker) {
        fireDepartmentComponents.addAll(marker);
		for (AbstractMapMarker m : marker) {
            mapViewer.add(m.getPanel());
        }
	}
	

}
