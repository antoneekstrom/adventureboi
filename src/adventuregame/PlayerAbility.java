package adventuregame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import worlds.ListWorld;

public class PlayerAbility extends AbstractAction {
	
	String action;
	ListWorld world;
	
	public PlayerAbility(String a, ListWorld lw) {
		world = lw;
		action = a;
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		if (action.equals("a-rightp")) {
			world.p.charging(true);
		}
		if (action.equals("a-rightr")) {
			world.p.fireDirection("right");
			world.p.charging(false);
		}
		if (action.equals("a-leftp")) {
			world.p.charging(true);
		}
		if (action.equals("a-leftr")) {
			world.p.fireDirection("left");
			world.p.charging(false);
		}
		if (action.equals("a-upp")) {
		}
		if (action.equals("a-upr")) {
		}
	}

}
