package de.hsnr.eal.ArtificialDispatcher.controll;

import java.awt.EventQueue;
import de.hsnr.eal.ArtificialDispatcher.gui.AppWindow;

public class Controller {
	
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
	}

	private void initView() {
		try {
			view = new AppWindow(model.ml, model.vehicles, model.stations, model.emergencyTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


}
