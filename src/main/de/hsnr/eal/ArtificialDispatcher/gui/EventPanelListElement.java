package de.hsnr.eal.ArtificialDispatcher.gui;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class EventPanelListElement extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1999690039963563181L;

	/**
	 * Create the panel.
	 */
	public EventPanelListElement(String name) {
		setLayout(null);
		
		JLabel einsatzName = new JLabel(name);
		einsatzName.setBounds(10, 11, 217, 14);
		add(einsatzName);
		
		JLabel vehicles = new JLabel("Zugeordnete Fahrzeuge");
		vehicles.setBounds(10, 31, 46, 14);
		add(vehicles);

	}
}
