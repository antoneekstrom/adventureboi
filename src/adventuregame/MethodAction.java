package adventuregame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import worlds.World;

public class MethodAction extends AbstractAction {

	public World world;
	public String action;
	
	public MethodAction(String a, World w) {
		world = w;
		action = a;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (action == "escp") {
		}
	}

}
