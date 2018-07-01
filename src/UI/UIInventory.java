package UI;

import gamelogic.Item;

public class UIInventory extends UIList {

    public UIInventory(String parentname) {
        super(parentname);
    }

    public UIInvItem getEntry(Item i) {
        UIInvItem o = new UIInvItem(getParentName(), i);
        o.setParentRectangle(get());

        o.setBackgroundColor(entry().getBackgroundColor());
        o.textColor(entry().getTextColor());
        o.setHoverTextColor(entry().getHoverTextColor());
        o.hoverColorChange(entry().getHoverBackgroundColor());
        o.start();

        if (entryFullWidth) {o.get().setSize(get().width - scrollbarWidth, entryHeight);}
        else {o.get().setSize(entryWidth, entryHeight);}
        
        return o;
    }

    public void leftMouseReleased() {
        super.leftMouseReleased();
        for (UIObject o : list) {
            if (o.checkMouse()) {
                UIInvItem i = (UIInvItem) o;
                i.leftMouseReleased();
            }
        }
    }

    public void refreshList(Item[] content) {
        list.clear();
        for (Item item : content) {
            list.add(getEntry(item));
        }
        determineContentHeight();
        determineHandleHeight();
    }

}
