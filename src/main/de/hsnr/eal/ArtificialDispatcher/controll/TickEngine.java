package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

import javax.swing.JSpinner;
import javax.swing.JSpinner.DateEditor;
import javax.swing.SpinnerDateModel;

public class TickEngine extends Observable{
	long tick;
	public long TICK = 60l*1000l;
	private JSpinner spinner;
	private DateEditor de;

	public TickEngine(){
		this.tick = 1483513200000L;
	}
	
	public long getTick() {
		return tick;
	}

	public void setTick(long tick) {
		setChanged();
		this.tick = tick;
		notifyObservers();
	}
	
	public void tickPlusOne(){
		setChanged();		
		tick += TICK;
		notifyObservers(this);
	}

	public String actTime() {
		spinner = new JSpinner();
		spinner.setModel(new SpinnerDateModel(new Date(getTick()), null, null, Calendar.HOUR_OF_DAY));
		de = new JSpinner.DateEditor(spinner, "HH:mm");
		de.getTextField().setEditable(false);
		spinner.setEditor(de);
		
		
		return de.getFormat().format(spinner.getValue());
	}
}
