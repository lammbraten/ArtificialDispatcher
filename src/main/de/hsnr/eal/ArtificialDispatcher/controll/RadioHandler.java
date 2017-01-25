package de.hsnr.eal.ArtificialDispatcher.controll;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.RadioMessage;

public class RadioHandler extends Observable {
	
	private List<RadioMessage> messages;
	
	public RadioHandler(){
		this.setMessages(new LinkedList<RadioMessage>());
		addMessage(new RadioMessage("LST", "", "Hier Florian Krefeld mit Funkalarm!", ""));
		
	}

	public List<RadioMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<RadioMessage> messages) {
		this.messages = messages;
	}
	
	public void addMessage(RadioMessage rm){
		this.setChanged();
		this.messages.add(rm);
		this.notifyObservers(rm);
	}
	

}
