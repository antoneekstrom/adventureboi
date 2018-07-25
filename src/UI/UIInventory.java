package UI;

import java.util.ArrayList;
import java.util.Iterator;

import gamelogic.Item;
import items.Currency;

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

    private void compareLists(ArrayList<Item> n) {
        for (Iterator<UIObject> iterator = list.iterator(); iterator.hasNext();) {
            UIInvItem i = (UIInvItem) iterator.next();
            if (n.contains(i.item)) {
                n.remove(i.item);
            }
            else {
                iterator.remove();
            }
        }
    }

    private void groupEntries(ArrayList<Item> l) {
        int count = 0;
        ArrayList<Item> unique = new ArrayList<Item>();
        for (Item i : l) {
            if (!contains(i, unique)) {
                unique.add(i);
            }
        }
        for (Item i : unique) {
            count = 0;
            count = containmentCount(i, l);
            if (count > 1) {
                for (int k = 0; k < count; k++) {
                    l.remove(findItem(i, l));
                }
                try {
                    Item collectionitem = i.getClass().newInstance();
                    collectionitem.imageName(collectionitem.imageName());
                    fixCurrency(collectionitem, i);
                    collectionitem.displayName(collectionitem.name() + ((count <= 1) ? ("") : (" (" + count + ")")) );
                    l.add(collectionitem);
                }
                catch (Exception e) {e.printStackTrace();}
            }
        }
    }

    private void fixCurrency(Item colitem, Item item) {
        try {
            Currency colc = (Currency) colitem;
            Currency c = (Currency) item;
            colc.setValue(c.getValue());
        }
        catch (Exception e) {}
    }

    private boolean contains(Item i, ArrayList<Item> l) {
        boolean b = false;

        for (Item ci : l) {
            if (ci.getIdentifier().equals(i.getIdentifier())) {return true;}
        }
        return false;
    }

    private Item findItem(Item tofind, ArrayList<Item> l) {
        for (Item i : l) {
            if (i.getIdentifier().equals(tofind.getIdentifier())) {return i;}
        }
        return null;
    }

    private int containmentCount(Item i, ArrayList<Item> l) {
        int count = 0;
        for (Item item : l) {
            if (item.getIdentifier().equals(i.getIdentifier())) {count++;}
        }

        return count;
    }

    public void refreshList(ArrayList<Item> content) {
        compareLists(content);
        groupEntries(content);
        for (Item item : content) {
            list.add(getEntry(item));
        }
        determineContentHeight();
        determineHandleHeight();
    }

}
