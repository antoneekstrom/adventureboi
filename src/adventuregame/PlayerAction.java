package adventuregame;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;

public class PlayerAction extends AbstractAction {

	private Main frame;
	//keyinput as string
	public String action;
	//player object
	private Player player;
	public ArrayList<RectangleObject> rects;
	
	//gets player object and actionstring
	public PlayerAction(String a, Player p, Main f) {
		action = a;
		player = p;
		frame = f;
	}
	
	public void getList(ArrayList<RectangleObject> l) {
		rects = l;
	}
	
	//actions go here
	public void actionPerformed(ActionEvent e) {
		//move right
		if (action == "rightpressed")  {
			player.setDirection("right");
		} else if (action == "rightreleased") {
			if (player.direction != "left") { /* this if statement allows for smooth left to right movement */
				player.setDirection("none");
				player.MOVACC = 1;
			}
		}
		//move left
		if (action == "leftpressed") {
			player.setDirection("left");
		} else if (action == "leftreleased") {
			
			if (player.direction != "right") { /* this if statement allows for smooth left to right movement */
				player.setDirection("none");
				player.MOVACC = 1;
			}
		}
	}
}
