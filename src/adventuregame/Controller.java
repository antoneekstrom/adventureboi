package adventuregame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.KeyStroke;

import worlds.ListWorld;

public class Controller extends JPanel implements ActionListener {

	//key mappings, Left:name, Right:event
	int right = 68;
	int left = 65;
	int up1 = 87;
	int up2 = 32;
	int down = 83;
	int esc = 27;

	JPanel jp = new JPanel();
	
	public JPanel getPanel() {
		return jp;
	}
	
	//player controller
	public void startPlayerController(Player p, Main frame, ListWorld w) {
		//right
		w.getInputMap().put(KeyStroke.getKeyStroke(right, 0, true), "rightreleased");
		w.getInputMap().put(KeyStroke.getKeyStroke(right, 0, false), "rightpressed");
		w.getActionMap().put("rightreleased", new PlayerAction("rightreleased", p, frame));
		w.getActionMap().put("rightpressed", new PlayerAction("rightpressed", p, frame));
		//left
		w.getInputMap().put(KeyStroke.getKeyStroke(left, 0, true), "leftreleased");
		w.getInputMap().put(KeyStroke.getKeyStroke(left, 0, false), "leftpressed");
		w.getActionMap().put("leftreleased", new PlayerAction("leftreleased", p, frame));
		w.getActionMap().put("leftpressed", new PlayerAction("leftpressed", p, frame));
		//up
		w.getInputMap().put(KeyStroke.getKeyStroke(up1, 0, true), "upreleased");
		w.getInputMap().put(KeyStroke.getKeyStroke(up1, 0, false), "uppressed");
		w.getActionMap().put("uppressed", new PlayerJump("uppressed", p));
		w.getActionMap().put("upreleased", new PlayerJump("upreleased", p));
		//down
		w.getInputMap().put(KeyStroke.getKeyStroke(down, 0, true), "dr");
		w.getInputMap().put(KeyStroke.getKeyStroke(down, 0, false), "dp");
		w.getActionMap().put("dr", new PlayerJump("dr", p));
		w.getActionMap().put("dp", new PlayerJump("dp", p));
		//esc
		w.getInputMap().put(KeyStroke.getKeyStroke(esc, 0, true), "escr");
		w.getInputMap().put(KeyStroke.getKeyStroke(esc, 0, false), "escp");
		w.getActionMap().put("escp", new PlayerAction("escp", p, frame));
		w.getActionMap().put("escr", new PlayerAction("escr", p, frame));
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
