package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

import de.hsnr.eal.ArtificialDispatcher.data.prolog.PLDatabase;
import de.hsnr.eal.ArtificialDispatcher.events.Event;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.Station;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.gui.test.TestListCellRenderer.Item;
import de.hsnr.eal.ArtificialDispatcher.gui.test.TestListCellRenderer.ItemCellRenderer;

public class AppWindow {

	private static final String FILE_PATH = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Implementierung\\ArtificialDispatcher\\src\\main\\de\\hsnr\\eal\\ArtificialDispatcher\\data\\prolog\\vehicles.pl";
	private JFrame mainFrame;
	private PLDatabase pldb;
	private ArrayList<Vehicle> vehicles;
	private ArrayList<Station> stations;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AppWindow window = new AppWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AppWindow() {
		loadDb();
		initialize();
	}

	private void loadDb() {
		pldb = new PLDatabase(FILE_PATH);
		stations = pldb.getStationObjects();
		 vehicles = new ArrayList<Vehicle>();
		
		try {
			for(Station station : stations)
				vehicles.addAll(pldb.getVehiclesObjectsOfStation(station.getId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Artificial Dispatcher / K\u00FCnstliche Leitstelle\r\n");
		mainFrame.setBounds(100, 100, 1920, 950);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JList<Vehicle> vehicleList = new JList<Vehicle>();
		vehicleList.setModel(new AbstractListModel<Vehicle>() {
			private static final long serialVersionUID = 4873049741922975858L;
			ArrayList<Vehicle> values = vehicles;
			public int getSize() {
				return values.size();
			}
			public Vehicle getElementAt(int index) {
				return values.get(index);
			}
		});
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
		vehicleSplitPane.setPreferredSize(new Dimension(400,500));

		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(200, 250);
		vehicleListScrollPane.setMinimumSize(minimumSize);
		radioListScrollPane.setMinimumSize(minimumSize);
		mainFrame.getContentPane().setLayout(new BorderLayout(5, 5));
		
		
		mainFrame.getContentPane().add(vehicleSplitPane, BorderLayout.EAST);
		
		// Setup JXMapViewer
		final JXMapViewer mapViewer = new JXMapViewer();
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
		

        DefaultListModel model = new DefaultListModel();
        model.addElement(new Event("Wohnungsbrand"));
        model.addElement(new Event("Heckenbrand"));
        model.addElement(new Event("Mülleimer"));
        
        JList list = new JList(model);
        list.setCellRenderer(new EventPanelRenderer());
        
		/*EventPanelListElement[] values = new EventPanelListElement[] {new EventPanelListElement("Heckenbrand "), new EventPanelListElement("Mülleimer"), new EventPanelListElement("Wohnungsbrand"), new EventPanelListElement("Lagerhalle")};


		JScrollPane eventListScrollPane = new JScrollPane(new EventPanelListElement("Heckenbrand "));
		eventListScrollPane.add(new EventPanelListElement("Mülleimer"));
		*/
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BorderLayout(0, 0));
		//eventPanel.add(eventListScrollPane, BorderLayout.CENTER);
		eventPanel.add(list);
		JLabel eventLabel = new JLabel("Aktuelle Eins\u00E4tze");
		eventPanel.add(eventLabel, BorderLayout.NORTH);
		
		mainFrame.getContentPane().add(eventPanel, BorderLayout.WEST);
		

		
		JPanel viewSelectPanel = new JPanel();
		mainFrame.getContentPane().add(viewSelectPanel, BorderLayout.NORTH);
	}
	

}
