package adventuregame;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.AbstractAction;

/** handles player jump input */
public class PlayerJump extends AbstractAction {
	
	//keyinput as string
	public String action;
	//player object
	private Player player;
	
	public PlayerJump(String a, Player p) {
		action = a;
		player = p;
	}
	
	public void actionPerformed(ActionEvent e) {

		//jump
		if (action == "uppressed" && (e.getModifiers() & InputEvent.SHIFT_DOWN_MASK) == 0) {
			player.jump = true;
		} else if (action == "upreleased") {
			player.jump = false;
		}
		//sit
		if (action == "dp" && (e.getModifiers() & InputEvent.SHIFT_DOWN_MASK) == 0) {
			player.setDirection("down");
		}
		if (action == "dr") {
			player.setDirection("none");
		}

	}
}
