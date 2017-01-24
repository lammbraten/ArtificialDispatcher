package de.hsnr.eal.ArtificialDispatcher.controll;

import java.awt.EventQueue;
import java.util.Observable;
import java.util.Observer;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.RadioMessage;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
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
			view = new AppWindow(model.ml, model.getEmergencyHandler(), model.te, model.rh, model.vehicles, model.stations, model.emergencyTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		//System.out.println("update Controller");
		if(o instanceof Model){
			if(arg instanceof Vehicle){
				view.renderVehicleList();
				view.renderRadioMessages();
				view.renderMap();
			}else if(arg instanceof Emergency){
				view.renderEmergencyList();
				view.renderMap();
			}else if(arg instanceof TickEngine){
				view.renderTick();
				view.renderEmergencyList();
				view.renderMap();
			}else if(arg  instanceof RadioMessage){
				view.renderRadioMessages();
				//view.renderRadioMessages();
				//view.renderMap();
				//System.out.println(arg);
			}
		}
	}
	


}
