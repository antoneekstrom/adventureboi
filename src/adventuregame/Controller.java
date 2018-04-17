package adventuregame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class Controller implements ActionListener {

	//key mappings, Left:name, Right:event
	int right = 68;
	int left = 65;
	int up1 = 87;
	int up2 = 32;
	int down = 83;
	int esc = 27;

	JPanel jp = new JPanel();
	
	//player controller
	public void startPlayerController(Player p, Main frame) {
		//right
		jp.getInputMap().put(KeyStroke.getKeyStroke(right, 0, true), "rightreleased");
		jp.getInputMap().put(KeyStroke.getKeyStroke(right, 0, false), "rightpressed");
		jp.getActionMap().put("rightreleased", new PlayerAction("rightreleased", p, frame));
		jp.getActionMap().put("rightpressed", new PlayerAction("rightpressed", p, frame));
		//left
		jp.getInputMap().put(KeyStroke.getKeyStroke(left, 0, true), "leftreleased");
		jp.getInputMap().put(KeyStroke.getKeyStroke(left, 0, false), "leftpressed");
		jp.getActionMap().put("leftreleased", new PlayerAction("leftreleased", p, frame));
		jp.getActionMap().put("leftpressed", new PlayerAction("leftpressed", p, frame));
		//up
		jp.getInputMap().put(KeyStroke.getKeyStroke(up1, 0, true), "upreleased");
		jp.getInputMap().put(KeyStroke.getKeyStroke(up1, 0, false), "uppressed");
		jp.getActionMap().put("uppressed", new PlayerJump("uppressed", p));
		jp.getActionMap().put("upreleased", new PlayerJump("upreleased", p));
		//down
		jp.getInputMap().put(KeyStroke.getKeyStroke(down, 0, true), "dr");
		jp.getInputMap().put(KeyStroke.getKeyStroke(down, 0, false), "dp");
		jp.getActionMap().put("dr", new PlayerJump("dr", p));
		jp.getActionMap().put("dp", new PlayerJump("dp", p));
		//esc
		jp.getInputMap().put(KeyStroke.getKeyStroke(esc, 0, true), "escr");
		jp.getInputMap().put(KeyStroke.getKeyStroke(esc, 0, false), "escp");
		jp.getActionMap().put("escp", new PlayerAction("escp", p, frame));
		jp.getActionMap().put("escr", new PlayerAction("escr", p, frame));
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
