package UI;

import java.awt.Color;
import java.awt.Graphics;

import gamelogic.Item;
import gamelogic.ObjectStorage;

public class UIInvItem extends UIObject {

    int BACKGROUND_PADDING = 0;

    UIObject listitem = this;
    
    UIButton inspectButton;
    int bwidth = 150, bheight = 50;

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
        if (item.equippable() && item.hasTag(Item.ABILITY) && !inspectButton.checkMouse()) {
            ObjectStorage.getPlayer(InventoryUI.playerName).equip(item, Item.ABILITY);
        }
        if (inspectButton.checkMouse()) {
            inspectButton.leftMouseReleased();
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
        if (inspectButton != null && visible()) {inspectButton.update(); positionInspectionButton();}
    }

    public void positionInspectionButton() {
        inspectButton.get().x = get().x + get().width - inspectButton.get().width;
        inspectButton.get().y = get().y;
        inspectButton.get().height = this.get().height;
    }

    @Override
    public boolean checkMouse() {
        boolean b = super.checkMouse();

        return b;
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (inspectButton != null && visible()) {inspectButton.paint(g);}
    }

    public void enableInspectButton() {
        inspectButton = new UIButton(getParentName(), "Inspect", false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                InspectItemUI ui = (InspectItemUI) UIManager.getGUI("InspectItem");
                ui.inspectItem(item);
            }
            {
                setBackgroundColor(getParent().getUITextColor());
                textColor(getParent().getUIBackgroundColor());
                this.hoverColorChange(getParent().getUIBackgroundColor());
                this.setHoverTextColor(getParent().getUITextColor());
                this.get().setSize(bwidth, bheight);
                this.centerTextY(true);
                this.get().setLocation(listitem.get().getLocation());
            }
        };
    }

    public void start() {
        setFontSize(getFontSize());
        setTag("inventory");
        setText(item.displayName());
        centerTextY(true);
        setBackgroundPadding(BACKGROUND_PADDING);

        enableInspectButton();
        enableTooltip( new UITooltip(this, item) );
    }

}