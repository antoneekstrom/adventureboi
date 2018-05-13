package adventuregame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import worlds.ListWorld;

public class TypeListener implements KeyListener {
	
	char key = 'k';
	String text = "";
	String output = "";
	private boolean enabled = false;
	Console c;
	ListWorld lw;
	
	public TypeListener(ListWorld w) {
		lw = w;
		c = new Console(w, this);
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
		if (enabled && arg0.getKeyCode() != 8 && arg0.getKeyChar() != '§' && arg0.getKeyCode() != KeyEvent.VK_ENTER && arg0.getKeyCode() != KeyEvent.VK_UP && arg0.getKeyCode() != KeyEvent.VK_DOWN) {
			text = text + arg0.getKeyChar();
		}
		if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if (text.length() != 0) {
				text = text.substring(0, text.length() - 1);
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			output = text;
			text = "";
			if (c.visible) {
				c.enter(output);
				c.currentIndex = c.history.size();
			}
		}
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			if (text == "") {
				c.prev();
			}
			else {
				c.next();
			}
		}
		else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			c.prev();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
}
