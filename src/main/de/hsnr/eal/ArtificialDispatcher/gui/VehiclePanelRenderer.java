package de.hsnr.eal.ArtificialDispatcher.gui;

import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.members.equipment.EquipmentItem;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Status;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;

public class VehiclePanelRenderer extends JPanel implements ListCellRenderer<Vehicle>{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4932991616348314406L;
	JLabel nameLbl = new JLabel("HLF 0-0");
	JPanel statusPnl = new JPanel();
	JLabel statusLbl = new JLabel("2");
	JLabel strkeLbl = new JLabel("St\u00E4rke: ");
	JLabel typeLbl = new JLabel("HLF-BF");

	/**
	 * Create the panel.
	 */
	public VehiclePanelRenderer() {
		setLayout(null);
		
		nameLbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		nameLbl.setBounds(5, 5, 100, 17);
		add(nameLbl);
		

		statusPnl.setBackground(Color.GREEN);
		statusPnl.setBounds(200, 5, 40, 40);
		add(statusPnl);
		statusPnl.setLayout(null);
		statusLbl.setToolTipText("no Tooltipp");
		
		statusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		statusLbl.setBounds(5, 5, 30, 30);
		statusLbl.setFont(new Font("Tahoma", Font.BOLD, 25));
		statusPnl.add(statusLbl);
		
		strkeLbl.setBounds(5, 25, 100, 14);
		add(strkeLbl);

		typeLbl.setBounds(115, 7, 46, 14);
		add(typeLbl);
	}
	
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(250, 50);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Vehicle> list,
			Vehicle value, int index, boolean isSelected, boolean cellHasFocus) {	     
         Color bg = null;
         Color fg = null;

         JList.DropLocation dropLocation = list.getDropLocation();
         if (dropLocation != null
                         && !dropLocation.isInsert()
                         && dropLocation.getIndex() == index) {

             bg = UIManager.getColor("List.dropCellBackground");
             fg = UIManager.getColor("List.dropCellForeground");

             isSelected = true;
         }

         if (isSelected) {
             setBackground(bg == null ? list.getSelectionBackground() : bg);
             setForeground(fg == null ? list.getSelectionForeground() : fg);
         } else {
             setBackground(list.getBackground());
             setForeground(list.getForeground());
         }


         setEnabled(list.isEnabled());
         nameLbl.setText(value.getName());
         setStatus(value.getStatus());
         strkeLbl.setText("Stärke: " + value.getCrewStrength());
         typeLbl.setText(value.getType().toString());
         
         Border border = null;
         if (cellHasFocus) {
             if (isSelected) {
                 border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
             }
             if (border == null) {
                 border = UIManager.getBorder("List.focusCellHighlightBorder");
             }
         } else {
             border = new EmptyBorder(1, 1, 1, 1);
         }
         setBorder(border);

		return this;
	}

	private void setStatus(Status status) {
		statusPnl.setBackground(status.getColor());
		statusLbl.setText(status.toString());
		statusLbl.setToolTipText(status.getLabel());
	}
	
}
