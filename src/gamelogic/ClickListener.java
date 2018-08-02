package gamelogic;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.SwingUtilities;

import UI.GUI;
import UI.UIManager;
import UI.UIObject;

public class ClickListener implements MouseListener {

	private boolean leftPressed = false;
	private boolean rightPressed = false;

	public boolean isLeftPressed() {return leftPressed;}
	public boolean isRightPressed() {return rightPressed;}

	@Override
	public void mouseClicked(MouseEvent e) {
	}
    
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
			objectLeftMousePressed();
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			rightPressed = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
			objectLeftMouseReleased();
		}
		if (e.getButton() == MouseEvent.BUTTON2) {
			rightPressed = false;
		}		

		if (SwingUtilities.isLeftMouseButton(e)) {leftClick();}
		if (SwingUtilities.isRightMouseButton(e)) {rightClick();}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	public void leftClick() {
		//interact with UI
		UIManager.getCurrentGUI().leftClick();
        for (GUI gui : UIManager.getGUIList()) {
            for (UIObject o : gui.getUIObjectList()) {
                if (o.checkMouse()) {
					if (o.hasTag()) {
						MouseFunctions.executeClickActionByTag(o.tag());
					}
					else {
						MouseFunctions.executeClickActionByText(o.getText());}
					}
            }
		}
		//place objects on click
		ObjectCreator.createOnMouse();
		//select objects
		ObjectInspector.selectWithMouse();
	}

	/** Call UIObject click method. */	
	public void objectLeftMouseReleased() {
		for (UIObject o : UIManager.getCurrentGUI().getUIObjectList()) {
			if (o.checkMouse()) {
				o.leftMouseReleased();
			}
			o.leftMouseReleasedSomewhere();
		}
	}

	/** Call UIObject click method. */
	public void objectLeftMousePressed() {
		for (UIObject o : UIManager.getCurrentGUI().getUIObjectList()) {
			if (o.checkMouse()) {
				o.leftMousePressed();
			}
		}
	}

	public void rightClick() {
		ObjectInspector.inspect();
	}

}
