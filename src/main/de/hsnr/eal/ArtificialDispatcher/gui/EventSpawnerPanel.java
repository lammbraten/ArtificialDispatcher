package de.hsnr.eal.ArtificialDispatcher.gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EventSpawnerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public EventSpawnerPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton generateNewEventBtn = new JButton("Neuen Einsatz er\u00F6ffnen");
		generateNewEventBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NewEventWindow dialog = new NewEventWindow();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		add(generateNewEventBtn);
		
		JButton generateRandomEventButton = new JButton("Neuen zuf\u00E4lligen Einsatz erstellen");
		add(generateRandomEventButton);

	}
}
