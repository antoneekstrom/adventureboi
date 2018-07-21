package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import adventuregame.GlobalData;
import gamelogic.ObjectInspector;
import objects.NewObject;

public class GUI {

	protected int screenCenterX = GlobalData.getScreenDim().width / 2;
	protected int screenCenterY = GlobalData.getScreenDim().height / 2;
	protected Color BACKGROUND_COLOR = Color.orange;
	protected Color BACKGROUND_HOVER_COLOR = Color.white;
	protected Color TEXT_HOVER_COLOR = Color.orange;
	private Color TEXT_COLOR = Color.white;
	protected int FONT_SIZE = 40;
	protected String FONT_NAME = "Comic Sans MS";
	private int BORDER_THICKNESS = 10;

	private int guidelineY1 = 150;
	private int guidelineSpacing = 150;
	private int guidelineUses = -1;

	private Rectangle box = new Rectangle(0, 0, GlobalData.getScreenDim().width, GlobalData.getScreenDim().height);

	private ArrayList<UIObject> removeQueue = new ArrayList<UIObject>();
	private ArrayList<UIObject> UIObjects = new ArrayList<UIObject>();

	private boolean incognito = false;
	private boolean showOutline = false;
	private boolean visible = false;
	private String name;

	/** In which layer this GUI resides. */
	private int layerIndex = 0;
	/** If GUI has the highest/latest layerIndex */
	private boolean onTop = true;

	public GUI(String name) {
		this.name = name;
	}

	public Font getUIFont() {
		return new Font(FONT_NAME, 20 , FONT_SIZE);
	}

	public void start() {

	}

	public void incognito(boolean b) {incognito = b;}
	/** If GUI is incognito (method returns true) it should not be added to GUI history. */
	public boolean incognito() {return incognito;}

	public int xCenter(int width) {return (GlobalData.getScreenDim().width / 2) - width / 2;}
	public int yCenter(int height) {return (GlobalData.getScreenDim().height / 2) - height / 2;}

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

	/**  */
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

	public UIObject[] getObjectsThatStartsWithTag(String s) {
		ArrayList<UIObject> l = new ArrayList<UIObject>();
		for (UIObject o : UIObjects) {
			if (o.tag().startsWith(s)) {l.add(o);}
		}
		UIObject[] arr = new UIObject[l.size()];
		l.toArray(arr);
		return arr;
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

	public void addedToHistory() {
		
	}

	/** Adds UIObject to remove queue. */
	public void remove(UIObject o) {
		removeQueue.add(o);
	}
	/** Removes all UIObjects in the removeQueue from this GUI. */
	private void remove() {
		for (UIObject object : removeQueue) {
			UIObjects.remove(object);
		}
		removeQueue.clear();
	}

	public void removeAlerts() {
		for (UIObject object : getObjectsThatStartsWithTag("alert")) {
			remove(object);
		}
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

		//determine layerindex
		layerIndex = UIManager.getAllVisible().size() -1;

		//put this one on top
		for (GUI g : UIManager.getAllVisible()) {
			g.onTop = false;
		}
		onTop = true;
	}

	public boolean onTop() {return onTop;}
	public void onTop(boolean b) {onTop = b;}
	public int getLayerIndex() {return layerIndex;}

	public boolean isVisible() {
		return visible;
	}
	
	public void update() {
		for (UIObject b : UIObjects) {
			b.update();
		}
		remove();
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

	public void applyMenuStyle(UIObject o) {
		o.setFontSize(40);
		o.autoAdjustBackgroundWidth(false);
		o.get().width = 500;
		o.hasBorder(true);
		o.setBorderThickness(BORDER_THICKNESS);
		o.setBackgroundPadding(40);
	}

	public void addMenuButton(String text, int y) {
		addObject(new UIButton(getName(), text, true) {
			{
            this.get().y = y;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
			get().width = 500;
			hasBorder(true);
			setBorderThickness(BORDER_THICKNESS);
            setBackgroundPadding(40);
			}
		});
	}
	public void addMenuButton(String text, int y, NavTask task) {
		addObject(new UIButton(getName(), text, true) {
			{
            this.get().y = y;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
			get().width = 500;
			hasBorder(true);
			setBorderThickness(BORDER_THICKNESS);
			setBackgroundPadding(40);
			giveTask(task);
			}
		});
	}
	/** Invoked when mouse is clicked while this GUI is active. */
	public void leftClick() {
	}

	public void inspectionContextMenu(NewObject o) {
		UIContextMenu c = new UIContextMenu(getName());
		c.setText(o.getName());
		c.get().setSize(320, 350);
		c.setLocation();
		c.setTag("objectInspect");
		c.getList().setFontSize(30);

		c.textColor(Color.white);
		c.setBackgroundColor(Color.white);
		c.getList().textColor(Color.white);
		c.getList().entry().takeInput(true);
		c.getList().takeInput(true);
		c.getList().setTag(c.tag());
		ObjectInspector.inspectObject(o);

		c.getList().handle().setBackgroundColor(Color.white);
		c.getList().handle().hoverColorChange(getUIBackgroundColor().brighter());

		c.getList().get().setSize(c.get().getSize());
		c.getList().get().setLocation(c.get().getLocation());
		c.hasText(false);
		c.getList().refreshList(new String[] {
			o.getName(),
			"width:" + o.get().width,
			"height:" + o.get().height,
			"x:" + o.get().x,
			"y:" + o.get().y,
			"gravity:" + o.physics().hasGravity(),
			"focus:" + o.cameraFocus(),
		});
		c.getList().getList().get(0).setInputPrefix("name");
		c.getList().getList().get(1).setInputPrefix("width");
		c.getList().getList().get(2).setInputPrefix("height");
		c.getList().getList().get(3).setInputPrefix("x");
		c.getList().getList().get(4).setInputPrefix("y");
		c.getList().getList().get(5).setInputPrefix("gravity");
		c.getList().getList().get(6).setInputPrefix("focus");
		
		addObject(c);
	}
	
	public void applyGeneralStyle(UIObject o) {
		o.hasBorder(true);
		o.setBorderThickness(BORDER_THICKNESS);
		o.setBackgroundPadding(40);
		o.centerTextY(true);
		o.centerTextX(true);
		o.setFontSize(40);
	}

	public void applySliderStyle(UISlider slider) {
		applyGeneralStyle(slider);
		slider.setBackgroundColor(getUITextColor());
		slider.textColor(getUIBackgroundColor());
		slider.setBorderColor(getUIBackgroundColor());
		slider.handle().setBackgroundColor(getUIBackgroundColor());
		slider.handle().hoverColorChange(getUIBackgroundColor().brighter());
		slider.handle().hasText(true);
	}

	public void applyListStyle(UIList l) {
		l.handle().get().setSize(50, 100);
		l.setSpacing(0);
		l.hasText(false);
		l.entry().setBackgroundPadding(0);
		l.setBackgroundColor(Color.white);
		l.setFontSize(40);
		l.handle().hoverColorChange(l.handle().getBackgroundColor().brighter());
	}

	public void addTitle(String text) {
		addObject(new UIText(getName(), text, true) {{
            this.textColor(getBackgroundColor());
            this.get().y = 200;
			this.setFontSize(80);
			this.setTag("title");
        }});
	}

}
