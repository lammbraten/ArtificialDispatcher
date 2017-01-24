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
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;

import de.hsnr.eal.ArtificialDispatcher.controll.TickEngine;

import java.util.Date;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TickControllPanel extends JPanel {
	private JTextField timeTxtField;
	private TickEngine te;
	private JSpinner spinner;
	private JButton tickButton;
	private DateEditor de;

	/**
	 * Create the panel.
	 */
	public TickControllPanel(TickEngine te) {
		this.te = te;
		
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		timeTxtField = new JTextField();
		timeTxtField.setText("10:00");
		timeTxtField.setEditable(false);
		add(timeTxtField);
		timeTxtField.setColumns(5);
		
		/*
		spinner = new JSpinner();
		spinner.setModel(new SpinnerDateModel(new Date(te.getTick()), null, null, Calendar.HOUR_OF_DAY));
		de = new JSpinner.DateEditor(spinner, "HH:mm");
		de.getTextField().setEditable(false);
		spinner.setEditor(de);*/
		
		
		timeTxtField.setText(te.actTime());
		
		tickButton = new JButton("+1 Minute");
		tickButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				te.tickPlusOne();
			}
		});
		tickButton.setIcon(new ImageIcon(TickControllPanel.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaPlayDisabled.png")));
		add(tickButton);
		
		
	}

	public void updateTime() {
		/*
		spinner.setModel(new SpinnerDateModel(new Date(te.getTick()), null, null, Calendar.HOUR_OF_DAY));
		de = new JSpinner.DateEditor(spinner, "HH:mm");
		de.getTextField().setEditable(false);
		spinner.setEditor(de);
		
		timeTxtField.setText(de.getFormat().format(spinner.getValue()));
		*/
		timeTxtField.setText(te.actTime());
		this.validate();
		this.repaint();
	}

}
