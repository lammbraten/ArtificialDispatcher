package de.hsnr.eal.ArtificialDispatcher.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import de.hsnr.eal.ArtificialDispatcher.firedepartment.RadioMessage;
import javax.swing.JTextField;

public class RadioMessagePanelRenderer extends JPanel implements ListCellRenderer<RadioMessage> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6468319725519606408L;
	private JTextArea textArea;


	
	public RadioMessagePanelRenderer() {
		setLayout(null);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(5, 5, 240, 40);
		add(textArea);
		textArea.setColumns(1);
		textArea.setWrapStyleWord(true);;
		textArea.setLineWrap(true); 		

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
	public Component getListCellRendererComponent(JList<? extends RadioMessage> list, RadioMessage value, int index,
			boolean isSelected, boolean cellHasFocus) {
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
        textArea.setText(value.toString());
        //setStatus(value.getStatus());

        
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
