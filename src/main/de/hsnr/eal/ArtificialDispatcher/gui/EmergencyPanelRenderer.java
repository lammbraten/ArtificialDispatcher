package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import de.hsnr.eal.ArtificialDispatcher.emergency.Emergency;
import de.hsnr.eal.ArtificialDispatcher.firedepartment.trucks.Vehicle;
import de.hsnr.eal.ArtificialDispatcher.gui.test.TestListCellRenderer.Item;
import java.awt.ScrollPane;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;

public class EmergencyPanelRenderer extends JPanel implements ListCellRenderer<Emergency>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3893635196282310548L;
	JLabel einsatzName = new JLabel("Einsatzname");
	JLabel vehicleLabel = new JLabel("Zugeordnete Fahrzeuge:");	
	JLabel enrLabel = new JLabel("Nr.");	
	JLabel enr = new JLabel("..");
	JList<String> vehicleList = new JList<String>();	
	DefaultListModel<String> vehicleListModel = new DefaultListModel<String>();
	JScrollPane scrollPane = new JScrollPane(vehicleList);

	
	public EmergencyPanelRenderer(){
		setLayout(null);
		
		einsatzName.setBounds(10, 11, 170, 14);
		add(einsatzName);
		
		vehicleLabel.setBounds(10, 31, 147, 14);
		add(vehicleLabel);
		
		enrLabel.setBounds(239, 11, 29, 14);
		add(enrLabel);
		
		enr.setBounds(278, 11, 46, 14);
		add(enr);
		scrollPane.setBounds(10, 51, 202, 88);
		
		add(scrollPane);
		vehicleList.setModel(vehicleListModel);
		vehicleList.setBounds(86, 69, 59, 60);

	}
	
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(350, 150);
    }

    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }


	@Override
	public Component getListCellRendererComponent(JList<? extends Emergency> list,
			Emergency value, int index, boolean isSelected, boolean cellHasFocus) {	     
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
         einsatzName.setText(value.getName());
         enr.setText(value.getNr()+"");
         
         vehicleListModel.clear();
         for(Vehicle v : value.getAssignedVehicles())
        	 vehicleListModel.addElement(v.getName());

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
}
