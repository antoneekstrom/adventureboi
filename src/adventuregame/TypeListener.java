package adventuregame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TypeListener implements KeyListener {
	
	char key = 'k';
	String text = "";
	String output = "";
	private boolean enabled = false;
	
	public TypeListener() {
		
	}
	
	public void enable() {
		enabled = true;
	}

	public void disable() {
		enabled = false;
	}
	
	public boolean getEnabled() {
		return enabled;
	}
	
	public String getOutput() {
		return output;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		if (enabled && arg0.getKeyCode() != 8 && arg0.getKeyChar() != '§' && arg0.getKeyCode() != 13) {
			text = text + arg0.getKeyChar();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			text = text.substring(0, text.length() - 1);
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			output = text;
			text = "";
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
