package adventuregame;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import worlds.ListWorld;
import worlds.World;

public class MethodAction extends AbstractAction {

	public ListWorld world;
	public String action;
	
	public MethodAction(String a, ListWorld w) {
		world = w;
		action = a;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (action == "escp") {
			if (world.options.visible == false) {
				world.options.visible = true;
			} else {
				world.options.visible = false;
			}
		}
	}

}
