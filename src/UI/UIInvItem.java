package UI;

import java.awt.Color;

import gamelogic.Item;
import gamelogic.NewObjectStorage;

public class UIInvItem extends UIObject {

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
            this.setBackgroundColor(EQUIPPED_COLOR);
            hasBorder(true);
        }
        else {
            this.setBackgroundColor(getParent().getUIBackgroundColor());
            hasBorder(false);
        }
    }

    public void start() {
        setFontSize(getFontSize());
        setTag("inventory");
        setText(item.name());
        centerTextY(true);
        setBackgroundPadding(0);

        this.setBorderColor(BORDER_COLOR);
        this.setBorderThickness(getParent().getBorderThickness());
        
        EnabletoolTip( new UITooltip(this, item) );
    }

}