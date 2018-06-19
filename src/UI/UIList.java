package UI;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import adventuregame.GlobalData;
import gamelogic.MouseFunctions;

public class UIList extends UIObject {

    private UIObject handle;
    private int handleHeight = 50;
    private double listContentHeight = 0;
    /** How many percent larger/smaller the content of the list is than the list. */
    private double listContentPercent = 0;

    private boolean pressed = false;
    private int contentSpacing = 50;

    private int contentScrollHeight = 0;
    private int entryHeight = 50;
    private int entryWidth = 100;
    private boolean entryFullWidth = true;

    /** The distance the handle is offset from the mouse when holding/pressing on scrollbar. */
    private int handleHoldOffset = 0;
    private int scrollbarWidth = 50;
    private Rectangle scrollbar;

    private UIObject entry = new UIObject();
    public UIObject entry() {return entry;}
    public void setEntry(UIObject o) {entry = o;}

    private ArrayList<UIObject> list = new ArrayList<UIObject>();
    public ArrayList<UIObject> getList() {return list;}

    public UIList(String parentname) {
        super();
        setParentName(parentname);
        start();
    }
    
    public void isEntryFullWidth(boolean b) {entryFullWidth = b;}
    public Rectangle getScrollbar() {return scrollbar;}
    public UIObject handle() {return handle;}
    public void setSpacing(int i) {contentSpacing = i;}
    
    public void start() {
        handle = new UIObject();
        handle.setParentName(getParentName());
        handle.setBackgroundColor(getParent().getUIBackgroundColor());
        handle.get().height = handleHeight;
        scrollbar = new Rectangle( (int) get().getMaxX() - scrollbarWidth, (int) get().getMinY(), scrollbarWidth, get().height);
    }

    private UIObject getEntry(String text) {
        UIObject o = new UIObject();
        o.setParentName(getParentName());
        o.setTag(tag());
        o.setText(text);
        o.setParentRectangle(get());
        o.centerTextY(true);
        o.setFontSize(getFontSize());
        o.takeInput(entry().takeInput());

        o.setBackgroundColor(entry().getBackgroundColor());
        o.textColor(entry().getTextColor());
        o.setHoverTextColor(entry().getHoverTextColor());
        o.hoverColorChange(entry().getHoverBackgroundColor());

        if (entryFullWidth) {o.get().setSize(get().width - scrollbarWidth, entryHeight);}
        else {o.get().setSize(entryWidth, entryHeight);}

        return o;
    }

    public void refreshList(String[] content) {
        list.clear();
        for (String t : content) {
            list.add(getEntry(t));
        }
        determineContentHeight();
        determineHandleHeight();
    }

    private void determineEntryLocation() {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).get().y = get().y + (contentSpacing * (i-1)) + (i * entryHeight) + contentScrollHeight;
            list.get(i).get().x = get().x;
        }
    }

    public void scroll(int distance) {contentScrollHeight = distance;}
    public int getScrollHeight() {return contentScrollHeight;}


    private void determineContentHeight() {
        //content height
        int s = list.size();
        int height = 0;
        for (int i = 1; i <= s; i++) {
            height += contentSpacing + entryHeight;
        }
        listContentHeight = height;
        //content percent
        listContentPercent = (get().height / listContentHeight);
        if (listContentPercent > 1) {listContentPercent = 1;}
    }

    private void determineHandleHeight() {
        double h = get().height * listContentPercent;
        if (h > get().height) {h = get().height;}
        handle.get().height = (int) h;
    }

    private boolean isPressed() {return handle.checkMouse() && MouseFunctions.getClickListener().isLeftPressed();}
    
    /** Move handle with mouse. */
    private void moveHandle() {
        //first press
        if (!pressed && isPressed()) {
            handleHoldOffset = handle.get().y - GlobalData.getMouse().y;
            pressed = true;
        }
        //logic
        if (!MouseFunctions.getClickListener().isLeftPressed()) {pressed = false;}
        //while holding
        if (pressed) {
            handle.get().y = GlobalData.getMouse().y + handleHoldOffset;
        }
        calculateScrollLength();
    }

    public void leftMouseReleased() {
        for (UIObject o : list) {
            if (o.checkMouse()) {
                MouseFunctions.executeListAction(o);
            }
        }
    }

    private void hideOverflow(UIObject o) {
        if (o.get().getMinY() < get().getMinY() || o.get().getMaxY() > get().getMaxY()) {
            o.setVisible(false);
        }
        else {o.setVisible(true);}
    }

    private void calculateScrollLength() {
        int handleDistance = (int) (get().getMinY() - handle.get().getMinY());
        double scrollRatio = ( ((double) handleDistance ) / get().height) + 1;        
        int scrollDistance = (int) ( (listContentHeight * scrollRatio) - listContentHeight);
        scroll(scrollDistance);        
    }

    /** Prevent handle from leaving scrollbar area. */
    private void limitHandle() {
        if (handle.get().getMaxY() > get().getMaxY()) {
            handle.get().y = (int) get().getMaxY() - handle.get().height;
        }
        if (handle.get().y < get().getMinY()) {
            handle.get().y = (int) get().getMinY();
        }
        if (handle.get().x != get().getMaxX() - handle.get().width) {
            handle.get().x = (int) get().getMaxX() - handle.get().width;
        }
    }
    
    public void update() {
        super.update();
        handle.update();
        moveHandle();
        limitHandle();
        determineContentHeight();
        determineEntryLocation();
        for (UIObject o : list) {o.update(); hideOverflow(o);}
    }
    
    public void paint(Graphics g) {
        super.paint(g);
        handle.paint(g);
        for (UIObject o : list) {o.paint(g);}
    }

}