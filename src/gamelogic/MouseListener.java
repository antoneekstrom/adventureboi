package gamelogic;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import UI.GUI;
import UI.UIManager;
import UI.UIObject;

public class MouseListener implements MouseWheelListener {

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
        for (GUI g : UIManager.getGUIList()) {
            for (UIObject o : g.getUIObjectList()) {
                if (o.checkMouse()) {
                    o.scroll(e);
                }
            }
        }
	}   

}