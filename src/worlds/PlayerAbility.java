package worlds;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

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
			world.p.fire("right");
		}
		if (action.equals("a-leftp")) {
			world.p.setDirection("left");
		}
	}

}
