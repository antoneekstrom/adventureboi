package UI;

import java.awt.Graphics;
import java.awt.Rectangle;

import adventuregame.GlobalData;

public class UIContextMenu extends UIObject {

	private UIList options;
	
	public UIContextMenu(String parentname) {
		super();
		setParentName(parentname);
		hasText(false);
		setBackgroundColor(getParent().getUIBackgroundColor());
		options = new UIList(getParentName()) {{
			setBox(new Rectangle(get()));
			setText(getText());
			setBackgroundColor(getParent().getUIBackgroundColor());
			entry().takeInput(true);
			entry().setTag(tag());
			handle().get().setSize(25, 50);
			setSpacing(0);
			hasText(false);
			isEntryFullWidth(true);
			get().setLocation(get().getLocation());
			handle().hoverColorChange(getParent().getUIBackgroundColor().brighter());
		}};
	}

	public void setLocation() {
		Rectangle r = new Rectangle();
		r.setLocation(GlobalData.getMouse());
		r.setSize(get().getSize());

		if (r.getMaxY() > GlobalData.getScreenDim().height) {r.y = GlobalData.getScreenDim().height - r.height;}
		setBox(r);
	}

	public void leftMouseReleased() {
		super.leftMouseReleased();
		options.leftMouseReleased();
	}

	public void start() {
		get().setSize(150, 200);
		
	}

	public UIList getList() {return options;}
	
	public void paint(Graphics g) {
		super.paint(g);
		options.paint(g);
	}
	
	public void update() {
		super.update();
		options.update();
	}
	
}
