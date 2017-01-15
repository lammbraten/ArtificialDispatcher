package de.hsnr.eal.ArtificialDispatcher.gui.mapmarker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.stations.StationType;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Status;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.VehicleType;

public class VehiclePanelMapMarker extends AbstractJPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 145151653590476094L;
	private static final int PICTURE_X_SIZE = 70;
	private static final int PICTURE_Y_SIZE = 30;
	private static final String PIC_PATH = "C:\\Users\\lammbraten\\Dropbox\\Master\\1.Semester\\EAL\\Projekt\\Pics\\vehicles\\";

	
	private String name;
	private VehicleType type;
	private Vehicle vehicle;
	
	
	
	public VehiclePanelMapMarker(Vehicle vehicle) {
		
		this.name = vehicle.getName();
		this.type = vehicle.getType();
		this.vehicle = vehicle;
		
		if (this.vehicle.getStatus() == Status.ZWEI)
			this.setVisible(false);
		else
			this.setVisible(true);


		
		try {
			BufferedImage myPicture;
			if(type == VehicleType.HLF_BF || type == VehicleType.HLF || type == VehicleType.HLF_A )
				myPicture = ImageIO.read(new File(PIC_PATH + "HLF.gif" ));
			else if(type == VehicleType.LF8 || type == VehicleType.LF20)
				myPicture = ImageIO.read(new File(PIC_PATH + "LF.gif" ));
			else if(type == VehicleType.ELW)
				myPicture = ImageIO.read(new File(PIC_PATH + "ELW.png"));
			else if(type == VehicleType.GTLF)
				myPicture = ImageIO.read(new File(PIC_PATH + "GTLF.png"));
			else if(type == VehicleType.DLK_23)
				myPicture = ImageIO.read(new File(PIC_PATH + "DLK.png"));
			else
				myPicture = ImageIO.read(new File(PIC_PATH + "MTF.gif"));
			
			Image myImage = myPicture.getScaledInstance(PICTURE_X_SIZE, PICTURE_Y_SIZE, Image.SCALE_SMOOTH);

			setLayout(new BorderLayout(0, 0));
			JLabel picLabel = new JLabel(new ImageIcon(myImage));
			picLabel.setPreferredSize(new Dimension(PICTURE_X_SIZE, PICTURE_Y_SIZE));
			add(picLabel, BorderLayout.WEST);
			
			JLabel lblNewLabel = new JLabel(name);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
			add(lblNewLabel, BorderLayout.NORTH);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		moreBtn = new JButton("?");
		add(moreBtn, BorderLayout.EAST);
		moreBtn.setPreferredSize(new Dimension(30, 17));
	}

}
