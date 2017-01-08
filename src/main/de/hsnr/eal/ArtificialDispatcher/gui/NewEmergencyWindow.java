package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.VehicleType;

public class NewEmergencyWindow extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public NewEmergencyWindow() {
		setTitle("Neuen Einsatz er\u00F6ffnen");
		setBounds(100, 100, 300, 134);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel eventTypeLbl = new JLabel("Einsatzart: ");
			eventTypeLbl.setBounds(10, 13, 92, 14);
			contentPanel.add(eventTypeLbl);
		}
		{
			JComboBox eventComboBox = new JComboBox();
			eventComboBox.setModel(new DefaultComboBoxModel(new String[] {"Wohnungsbrand", "Kellerbrand"}));
			eventComboBox.setBounds(112, 10, 162, 20);
			contentPanel.add(eventComboBox);
		}
		{
			JLabel eventPlaceLbl = new JLabel("Einsatzort:");
			eventPlaceLbl.setBounds(10, 38, 92, 14);
			contentPanel.add(eventPlaceLbl);
		}
		
		JComboBox eventPlaceComboBox = new JComboBox();
		eventPlaceComboBox.setModel(new DefaultComboBoxModel(new String[] {"Reinarztstra\u00DFe", "K\u00F6lner Stra\u00DFe"}));
		eventPlaceComboBox.setBounds(112, 35, 162, 20);
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
}
