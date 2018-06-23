package UI;

import gamelogic.Item;

public class UIInvItem extends UIObject {

    Item item;

    public UIInvItem(String parentname, Item i) {
        super();
        setParentName(parentname);
        item = i;
    }

    public void start() {
        setFontSize(getFontSize());
        setTag("inventory");
        setText(item.name());
        centerTextY(true);
        
        EnabletoolTip( new UITooltip(this, item) );
    }

}