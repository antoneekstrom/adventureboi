package UI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import adventuregame.GlobalData;

public class GUI {

	private boolean visible = true;
	private ArrayList<HudText> UIText = new ArrayList<HudText>() {
		private static final long serialVersionUID = 1L;
	{
	}};

	private ArrayList<UIButton> UIButton = new ArrayList<UIButton>() {
		private static final long serialVersionUID = 1L;
	{
	}};
	
	public GUI() {
		
	}

	public Rectangle getRectangle() {
		return new Rectangle(0, 0, GlobalData.getScreenDim().width,
		GlobalData.getScreenDim().height);
	}

	public ArrayList<HudText> getUITextList() {
		return UIText;
	}

	public ArrayList<UIButton> getUIButtonList() {
		return UIButton;
	}

	void addText(HudText text) {
		UIText.add(text);
	}

	void addButton(UIButton button) {
		UIButton.add(button);
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void update() {
		for (HudText t : UIText) {
			t.update();
		}
		for (UIButton b : UIButton) {
			b.update();
		}
	}
	
	public void paint(Graphics g) {
		for (HudText t : UIText) {
			t.paint(g);
		}
		for (UIButton b : UIButton) {
			b.paint(g);
		}
	}

}
