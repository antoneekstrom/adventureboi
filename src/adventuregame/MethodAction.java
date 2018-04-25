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
			if (world.optionsactive == false) {
				world.optionsactive = true;
				world.options.visible = true;
				world.switchHud("options");
			} else {
				world.optionsactive = false;
				world.switchHud(world.lastHud);
			}
			System.out.println("world name: " + world.name);
			if (!(world.getName() == "menu") && !(world.getName() == "levels")) {
				world.lastHud = "";
			}
		}

	}
}