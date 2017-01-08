package de.hsnr.eal.ArtificialDispatcher.gui;

import javax.swing.JPanel;

import de.hsnr.eal.ArtificialDispatcher.data.map.MapLoader;
import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;

public class EmergencySpawnerPanel extends JPanel {

	/**
	 * Create the panel.
	 * @param ml 
	 */
	public EmergencySpawnerPanel(List<EmergencyType> emergencyTypes, MapLoader ml) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton generateNewEventBtn = new JButton("Neuen Einsatz er\u00F6ffnen");
		generateNewEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewEmergencyWindow dialog = new NewEmergencyWindow(emergencyTypes, ml);
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		add(generateNewEventBtn);
		
		JButton generateRandomEventButton = new JButton("Neuen zuf\u00E4lligen Einsatz erstellen");
		add(generateRandomEventButton);

	}
}
