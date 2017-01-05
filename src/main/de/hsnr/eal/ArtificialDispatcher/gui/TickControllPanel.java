package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.FlowLayout;
import java.text.DateFormat;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.DropMode;
import javax.swing.JFormattedTextField;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class TickControllPanel extends JPanel {
	private JTextField timeTxtField;

	/**
	 * Create the panel.
	 */
	public TickControllPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		timeTxtField = new JTextField();
		timeTxtField.setText("10:00");
		timeTxtField.setEditable(false);
		add(timeTxtField);
		timeTxtField.setColumns(5);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerDateModel(new Date(1483513200000L), null, null, Calendar.HOUR_OF_DAY));
		JSpinner.DateEditor de = new JSpinner.DateEditor(spinner, "HH:mm");
		de.getTextField().setEditable(false);
		spinner.setEditor(de);
		
		
		timeTxtField.setText(de.getFormat().format(spinner.getValue()));
		
		JButton tickButton = new JButton("+1 Minute");
		tickButton.setIcon(new ImageIcon(TickControllPanel.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		add(tickButton);
		
		
	}

}
