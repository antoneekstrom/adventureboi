package gamelogic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import UI.GUI;
import UI.UIManager;
import UI.UIObject;

public class ClickListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
    }
    
	@Override
	public void mousePressed(MouseEvent e) {
    }
    
	@Override
	public void mouseReleased(MouseEvent e) {
        for (GUI gui : UIManager.getGUIList()) {
            for (UIObject o : gui.getUIObjectList()) {
                if (o.checkMouse()) {MouseFunctions.executeClickActionByText(o.getText());}
            }
        }
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
