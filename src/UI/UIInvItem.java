package UI;

import java.awt.Color;

import gamelogic.Item;
import gamelogic.NewObjectStorage;

public class UIInvItem extends UIObject {

    int BACKGROUND_PADDING = 0;

    public static Color EQUIPPED_COLOR = Color.cyan, BORDER_COLOR = Color.orange;
    Item item;

    public UIInvItem(String parentname, Item i) {
        super();
        setParentName(parentname);
        item = i;
    }

    @Override
    public void leftMouseReleased() {
        super.leftMouseReleased();
        if (item.equippable() && item.hasTag(Item.ABILITY)) {
            NewObjectStorage.getPlayer(InventoryUI.playerName).equip(item, Item.ABILITY);
        }
    }

    public void update() {
        super.update();

        if (item.hasTag(Item.EQUIPPED)) {
            this.textColor(EQUIPPED_COLOR);
            this.setBackgroundColor(Color.white);
        }
        else {
            this.textColor(getParent().getUITextColor());
            this.setBackgroundColor(getParent().getUIBackgroundColor());
        }
    }

    public void start() {
        setFontSize(getFontSize());
        setTag("inventory");
        setText(item.name());
        centerTextY(true);
        setBackgroundPadding(BACKGROUND_PADDING);

        EnabletoolTip( new UITooltip(this, item) );
    }

}