package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.TreeSet;

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

import de.hsnr.eal.ArtificialDispatcher.controll.EmergencyHandler;
import de.hsnr.eal.ArtificialDispatcher.data.map.ConcreteGeoLocation;
import de.hsnr.eal.ArtificialDispatcher.data.map.GeoLocation;
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
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.CardLayout;
import net.miginfocom.swing.MigLayout;
import javax.swing.JToolBar;

public class AppWindow extends Observable implements Observer{

	private JFrame mainFrame;

	private MapLoader ml;


	private MapMarkerPainter mapMarkerPainter;
	private HashSet<AbstractMapMarker> fireDepartmentComponents;
	private JXMapViewer mapViewer;

	private ArrayList<Vehicle> vehicles;
	private ArrayList<Station> stations;
	private Set<Emergency> emergencies;
	private List<EmergencyType> emergencyTypes;

	private JPanel vehicleListPanel;
	private JSplitPane vehicleSplitPane;

	private JPanel radioListPanel;

	//private JList<Emergency> emergencyList;

	private EmergencyHandler eh;

	private Container eventPanel;

	private Toolkit t;



	/**
	 * Create the application.
	 * @param ml 
	 */
	public AppWindow(MapLoader ml, EmergencyHandler eh, ArrayList<Vehicle> vehicles, ArrayList<Station> stations, List<EmergencyType> emergencyTypes) {
		this.ml = ml;
		this.eh = eh;
		this.vehicles = vehicles;
		this.stations = stations;
		this.emergencyTypes = emergencyTypes;
		this.emergencies = new TreeSet<Emergency>();
		
		
		initialize();
	}
	
	public void renderVehicleList(){
        DefaultListModel<Vehicle> vehicleModel = new DefaultListModel<Vehicle>();
        for(Vehicle vehicle : vehicles)
        	vehicleModel.addElement(vehicle);
        
        JList<Vehicle> vehicleList = new JList<Vehicle>(vehicleModel);
        vehicleList.setCellRenderer(new VehiclePanelRenderer());
        
		JScrollPane vehicleListScrollPane = new JScrollPane(vehicleList);
		vehicleListScrollPane.setToolTipText("Hallo\r\n");
		
		vehicleListPanel = new JPanel();
		vehicleListPanel.setLayout(new BorderLayout(0, 0));
		JLabel vehicleListLabel = new JLabel("Fahrzeuge");
		vehicleListPanel.add(vehicleListLabel, BorderLayout.NORTH);
		vehicleListPanel.add(vehicleListScrollPane, BorderLayout.CENTER);
		
		Dimension minimumSize = new Dimension(200, 250);
		vehicleListScrollPane.setMinimumSize(minimumSize);
	}
	
	public void renderEmergencyList(){
		//mainFrame.getContentPane().remove(eventPanel);
		DefaultListModel<Emergency> emergencyModel = new DefaultListModel<Emergency>();
        
		this.emergencies = eh.getEmergencies();
		
		if(emergencies != null){
			for(Emergency e : emergencies)
				emergencyModel.addElement(e);
		}
		

	//	this.emergencies.add(new Emergency(new EmergencyType("HI","LOL", null), new ConcreteGeoLocation(1l,"1")));
	//	this.emergencies.add(new Emergency(new EmergencyType("HsI","L234OL", null), new ConcreteGeoLocation(1l,"1")));
	//	this.emergencies.add(new Emergency(new EmergencyType("H32eI","LO32L", null), new ConcreteGeoLocation(1l,"1")));

		JList<Emergency> emergencyList = new JList<Emergency>(emergencyModel);
        emergencyList.setCellRenderer(new EmergencyPanelRenderer());

        emergencyList.updateUI();
        
        emergencyList.validate();
        emergencyList.repaint();

        eventPanel.remove(emergencyList);
		eventPanel.add(emergencyList);
		
		eventPanel.validate();
		eventPanel.repaint();
		


		mainFrame.getContentPane().add(eventPanel, BorderLayout.WEST);
		//mainFrame.getContentPane().validate();
		//mainFrame.getContentPane().repaint();

		mainFrame.validate();
		mainFrame.repaint();
		

		

	}
	
	public void renderMap() {
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
	}

	public void renderRadioMessages(){
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
		
		JScrollPane radioListScrollPane = new JScrollPane(radioList);	

		radioListPanel = new JPanel();
		radioListPanel.setLayout(new BorderLayout(0, 0));
		JLabel radioListLabel = new JLabel("Funk");
		radioListPanel.add(radioListLabel, BorderLayout.NORTH);		
		radioListPanel.add(radioListScrollPane, BorderLayout.CENTER);
		
		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(200, 250);
		radioListScrollPane.setMinimumSize(minimumSize);	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		t = Toolkit.getDefaultToolkit();
		
		Dimension d = t.getScreenSize();
		
		mainFrame = new JFrame();
		mainFrame.setTitle("Artificial Dispatcher / K\u00FCnstliche Leitstelle\r\n");
		mainFrame.setBounds(0, 0, (int) d.getWidth(), (int)(d.getHeight()-10));
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new BorderLayout(5, 5));	
		
		initMap();
		initSplitPane();
        initEmergencyPanel();
		initToolbar();

		mainFrame.setVisible(true);

	}

	private void initToolbar() {
		JPanel viewSelectPanel = new JPanel();
		mainFrame.getContentPane().add(viewSelectPanel, BorderLayout.NORTH);
		viewSelectPanel.setMinimumSize(new Dimension(1900, 100));
		viewSelectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JToolBar emergencySpawnerToolBar = new JToolBar();
		viewSelectPanel.add(emergencySpawnerToolBar);
		
		EmergencySpawnerPanel evsPanel = new EmergencySpawnerPanel(emergencyTypes, ml, eh);
		emergencySpawnerToolBar.add(evsPanel);
		
		JToolBar tickControlToolBar = new JToolBar();
		viewSelectPanel.add(tickControlToolBar);
		
		TickControllPanel tcPanel = new TickControllPanel();
		tickControlToolBar.add(tcPanel);
	}

	private void initEmergencyPanel() {
		eventPanel = new JPanel();		
		mainFrame.getContentPane().add(eventPanel, BorderLayout.WEST);

		renderEmergencyList();
		

		eventPanel.setLayout(new BorderLayout(0, 0));
		JLabel eventLabel = new JLabel("Aktuelle Eins\u00E4tze");
		eventPanel.add(eventLabel, BorderLayout.NORTH);
		
	}

	private void initSplitPane() {
		renderVehicleList();
		renderRadioMessages();
		
		vehicleSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vehicleListPanel, radioListPanel);
		vehicleSplitPane.setOneTouchExpandable(true);
		vehicleSplitPane.setDividerLocation(450);
		vehicleSplitPane.setDividerSize(10);
		vehicleSplitPane.setPreferredSize(new Dimension(270, 500));

		mainFrame.getContentPane().add(vehicleSplitPane, BorderLayout.EAST);
	}

	private void initMap(){
		// Setup JXMapViewer
		mapViewer = new JXMapViewer();
		mapViewer.setTileFactory(new DefaultTileFactory(new OSMTileFactoryInfo()));
		
	
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
		
		renderMap();
	}

	private void addMapMarkerToMap(HashSet<AbstractMapMarker> marker) {
        fireDepartmentComponents.addAll(marker);
		for (AbstractMapMarker m : marker) {
            mapViewer.add(m.getPanel());
        }
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	

}
