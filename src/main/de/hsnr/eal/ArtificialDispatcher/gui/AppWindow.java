package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
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

public class AppWindow {

	private JFrame mainFrame;

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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setTitle("Artificial Dispatcher / K\u00FCnstliche Leitstelle\r\n");
		mainFrame.setBounds(100, 100, 1920, 950);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JList vehicleList = new JList();
		vehicleList.setModel(new AbstractListModel() {
			String[] values = new String[] {"HLF", "DLK", "LF"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
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
		

		
		

		EventPanelListElement[] values = new EventPanelListElement[] {new EventPanelListElement("Heckenbrand "), new EventPanelListElement("Mülleimer"), new EventPanelListElement("Wohnungsbrand"), new EventPanelListElement("Lagerhalle")};


		JScrollPane eventListScrollPane = new JScrollPane(new EventPanelListElement("Heckenbrand "));
		eventListScrollPane.add(new EventPanelListElement("Mülleimer"));
		
		JPanel eventPanel = new JPanel();
		eventPanel.setLayout(new BorderLayout(0, 0));
		eventPanel.add(eventListScrollPane, BorderLayout.CENTER);
		JLabel eventLabel = new JLabel("Aktuelle Eins\u00E4tze");
		eventPanel.add(eventLabel, BorderLayout.NORTH);
		
		mainFrame.getContentPane().add(eventPanel, BorderLayout.WEST);
		

		
		JPanel viewSelectPanel = new JPanel();
		mainFrame.getContentPane().add(viewSelectPanel, BorderLayout.NORTH);
	}

}
