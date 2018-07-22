package actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import UI.UIManager;
import adventuregame.Input;

public class UINavigation extends AbstractAction {

	private static final long serialVersionUID = 1L;

	String action;
	boolean released;

	public UINavigation(String action, boolean released) {
		this.action = action;
		this.released = released;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (released && Input.UIKeysEnabled()) {
			if (action.equals("UI_inventory")) {
				if (UIManager.getGUI("Inventory").isVisible()) {
					UIManager.enableGUI("HUD");
				}
				else {
					UIManager.enableGUI("Inventory");
				}
			}
			if (action.equals("UI_options")) {
				if (UIManager.getGUI("Options").isVisible()) {
					UIManager.back();
				}
				else if (UIManager.getGUI("HUD").isVisible()) {
					UIManager.enableGUI("Options");
				}
				else if (UIManager.allHidden()) {
					UIManager.enableGUI("Options");
				}
				else {
					UIManager.back();
				}
			}
			if (action.equals("UI_creative")) {
				if (UIManager.getGUI("Creative").isVisible()) {
					UIManager.enableGUI("HUD");
				}
				else {
					UIManager.enableGUI("Creative");
				}
			}
		}
	}

}
