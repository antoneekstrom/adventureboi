package adventuregame;

import java.awt.event.ActionEvent;

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
	
	public void actionPerformed(ActionEvent arg0) {
		System.out.println(action);

		//jump
		if (action == "uppressed") {
			player.jump = true;
		} else if (action == "upreleased") {
			player.jump = false;
		}
		//sit
		if (action == "dp") {
			player.setDirection("down");
		}
		if (action == "dr") {
			player.setDirection("none");
		}

	}
}
