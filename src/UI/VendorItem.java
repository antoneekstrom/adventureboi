package UI;

import gamelogic.Item;
import objects.Vendor;

public class VendorItem extends EntryData {

    String parentname;
    Item item;
    Vendor vendor;

    public VendorItem(String parentname, Item item, Vendor vendor) {
        this.parentname = parentname;
        this.item = item;
        this.vendor = vendor;
    }

    @Override
    public UIObject createEntry() {
        UIText t = new UIText(parentname, item.displayName() + " [" + vendor.getPrice(item) + "]", false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                vendor.purchase(item, vendor.playersWithinRange()[0]);
            }
        };
        t.autoAdjustBackgroundWidth(true);
        t.autoAdjustBackgroundHeight(true);
        t.centerText();
        t.setBackgroundPadding(10);
        t.setBackgroundColor(t.getParent().getUIBackgroundColor());
        t.textColor(t.getParent().getUITextColor());
        t.setHoverTextColor(t.getParent().getUIBackgroundColor());
        t.hoverColorChange(t.getParent().getUITextColor());
        t.enableTooltip(new UITooltip(t, item));

        return t;
    }

}