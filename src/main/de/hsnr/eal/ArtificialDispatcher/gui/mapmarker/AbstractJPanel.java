package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

public class AbstractJPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1175921066355092210L;
	JButton moreBtn;
	
	public Component getMoreButton(){
		return moreBtn;
	}
}
