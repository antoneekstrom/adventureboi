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
	private int BORDER_THICKNESS = 10;

	private int guidelineY1;
	private int guidelineSpacing;
	private int guidelineUses = -1;

	private Rectangle box = new Rectangle(0, 0, GlobalData.getScreenDim().width, GlobalData.getScreenDim().height);

	private ArrayList<UIObject> UIObjects = new ArrayList<UIObject>();

	private boolean showOutline = false;
	private boolean visible = false;
	private String name;

	public GUI(String name) {
		this.name = name;
	}

	public Font getUIFont() {
		return new Font(FONT_NAME, 20 , FONT_SIZE);
	}

	public void start() {

	}

	public void setGuidelineSpacing(int i) {
		guidelineSpacing = i;
	}

	public void setGuidelineY1(int y) {
		guidelineY1 = y;
	}

	public int getGuidelineY1() {
		int y = guidelineY1;
		guidelineUses++;
		for (int i = 0; i < guidelineUses; i++) {y += guidelineSpacing;}
		return y;
	}

	public static UIObject findObject(UIObject[] arr, String text) {
		UIObject object = null;
		for (UIObject o :  arr) {
			if (o.getText().equals(text)) {object = o;}
		}
		return object;
	}

	public void resetGuidelines() {
		guidelineUses = -1;
		guidelineY1 = 0;
		guidelineSpacing = 0;
	}

	public UIObject[] getObjectsByTag(String s) {
		ArrayList<UIObject> l = new ArrayList<UIObject>();
		for (UIObject o : UIObjects) {
			if (o.tag().equals(s)) {l.add(o);}
		}
		UIObject[] arr = new UIObject[l.size()];
		l.toArray(arr);
		return arr;
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

	public int getBorderThickness() {
		return BORDER_THICKNESS;
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
			if (b.visible()) {b.paint(g);}
		}

		if (showOutline) {
			g.drawRect(get().x, get().y, get().width, get().height);
		}
	}

	public void addBackButton() {
		UIButton b = new UIButton(getName(), "Back", false);
		b.get().setLocation(100, 100);
		b.setBackgroundPadding(40);
		b.centerTextX(true);
		b.centerTextY(true);
		b.hasBorder(true);
		b.setBorderThickness(BORDER_THICKNESS);
		addObject(b);
	}

	public void addMenuButton(String text, int y) {
		addObject(new UIButton(getName(), text, true) {{
            this.get().y = y;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
			get().width = 500;
			hasBorder(true);
			setBorderThickness(BORDER_THICKNESS);
            setBackgroundPadding(40);
        }});
	}

	public void addTitle(String text) {
		addObject(new UIText(getName(), text, true) {{
            this.textColor(Color.orange);
            this.get().y = 200;
            this.setFontSize(80);
        }});
	}

}
