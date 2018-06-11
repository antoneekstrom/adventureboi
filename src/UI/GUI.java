package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import adventuregame.GlobalData;

public class GUI {

	protected int screenCenterX = GlobalData.getScreenDim().width / 2;
	protected int screenCenterY = GlobalData.getScreenDim().height / 2;
	protected Color BACKGROUND_COLOR = Color.orange;
	private Color TEXT_COLOR = Color.white;
	protected int FONT_SIZE = 40;
	protected String FONT_NAME = "Comic Sans MS";

	private Rectangle box = new Rectangle(0, 0, GlobalData.getScreenDim().width, GlobalData.getScreenDim().height);

	private ArrayList<UIObject> UIObjects = new ArrayList<UIObject>();

	private boolean showOutline = true;
	private boolean visible = true;
	private String name;

	public GUI(String name) {
		this.name = name;
	}

	public Font getUIFont() {
		return new Font(FONT_NAME, 20 , FONT_SIZE);
	}

	public void start() {

	}

	public UIObject getObjectByText(String objectText) {
		UIObject returnObject = null;
		for (UIObject o : UIObjects) {
			if (o.getText().equals(objectText)) {returnObject = o;}
		}
		return returnObject;
	}

	public Color getUITextColor() {
		return TEXT_COLOR;
	}

	public void setUIBackgroundColor(Color c) {
		BACKGROUND_COLOR = c;
	}

	public Color getUIBackgroundColor() {
		return BACKGROUND_COLOR;
	}

	public void setUITextColor(Color c) {
		TEXT_COLOR = c;
	}

	public String getName() {
		return name;
	}

	public void setName(String n) {
		name = n;
	}

	public void showOutline(boolean b) {
		showOutline = b;
	}

	public void addObject(UIObject o) {
		o.setParentName(name);
		UIObjects.add(o);
	}

	public void setBox(Rectangle newBox) {
		box = newBox;
	}

	public Rectangle get() {
		return box;
	}

	public ArrayList<UIObject> getUIObjectList() {
		return UIObjects;
	}

	public void setVisible(boolean b) {
		visible = b;
	}

	public boolean isVisible() {
		return visible;
	}
	
	public void update() {
		for (UIObject b : UIObjects) {
			b.update();
		}
	}
	
	public void paint(Graphics g) {
		for (UIObject b : UIObjects) {
			b.paint(g);
		}

		if (showOutline) {
			g.drawRect(get().x, get().y, get().width, get().height);
		}
	}

}
