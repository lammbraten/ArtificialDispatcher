package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import de.hsnr.eal.ArtificialDispatcher.emergency.EmergencyType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.StationType;

import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JButton;

public class EmergencyPanelMapMarker extends AbstractJPanel {
	
	private static final int PICTURE_X_SIZE = 25;
	private static final int PICTURE_Y_SIZE = 25;

	private static final long serialVersionUID = -3645039294211799233L;
	private static final String PIC_PATH = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Pics\\";

	private String name;
	private EmergencyType type;

	/**
	 * Create the panel.
	 * @param type 
	 * @param name 
	 */
	public EmergencyPanelMapMarker(EmergencyType et) {
		this.name = et.getCodeword() + " - " + et.getName();
		this.type = et;
		
		
		try {
			BufferedImage myPicture;
			setLayout(new BorderLayout(0, 0));
			
			JLabel lblNewLabel = new JLabel(name);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
			if(type.getCodeword().startsWith("B"))
				myPicture = ImageIO.read(new File(PIC_PATH + "fire.png" ));
			else
				myPicture = ImageIO.read(new File(PIC_PATH + "TH.png" ));
			
			Image myImage = myPicture.getScaledInstance(PICTURE_X_SIZE, PICTURE_Y_SIZE, Image.SCALE_SMOOTH);
			JLabel picLabel = new JLabel(new ImageIcon(myImage));
			picLabel.setPreferredSize(new Dimension(PICTURE_X_SIZE, PICTURE_Y_SIZE));
			add(picLabel, BorderLayout.WEST);

			add(lblNewLabel, BorderLayout.NORTH);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		moreBtn = new JButton("?");
		add(moreBtn, BorderLayout.EAST);
		moreBtn.setPreferredSize(new Dimension(30, 17));

	}
	
	public Component getMoreButton(){
		return moreBtn;
		
	}

}

