package de.hsnr.eal.ArtificialDispatcher.controll;

import java.awt.EventQueue;
import java.util.Observable;
import java.util.Observer;

import de.hsnr.eal.ArtificialDispatcher.gui.AppWindow;

public class Controller implements Observer{
	
	private AppWindow view;
	private Model model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Controller controller = new Controller();
			}
		});
	}
	
	public Controller(){
		initModel();
		initView();
	}

	private void initModel() {
		model = new Model();
		model.addObserver(this);
	}

	private void initView() {
		try {
			view = new AppWindow(model.ml, model.vehicles, model.stations, model.emergencyTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {

	}
	


}
