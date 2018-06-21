package UI;

import adventuregame.GameEnvironment;
import data.PlayerData;
import gamelogic.Item;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;

public class InventoryUI extends GUI {

    UIList inv;
    String playerName = GameEnvironment.player1Name();
    int invwidth = 700;
    int invheight = 1000;

    public InventoryUI() {
        super("Inventory");
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            refreshInv();
        }
    }

    public void refreshInv() {
        NewPlayer p = NewObjectStorage.getPlayer(playerName);
        if (NewObjectStorage.getPlayer(playerName).playerData().inventory() != null) {

            String[] arr = new String[p.playerData().inventory().size()];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = p.playerData().inventory().get(i).displayName();
            }

            if (arr != null) {
                inv.refreshList(arr);
            }
        }
    }

    @Override
    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(100);

        //inventory list
        inv = new UIList(getName()) {
            {
                get().setSize(invwidth, invheight);
                get().setLocation(xCenter(get().width), yCenter(get().height));
                setBackgroundColor(getUITextColor());
                handle().get().setSize(50, 100);
                setSpacing(0);
                entry().setBackgroundPadding(0);
                setFontSize(40);
                handle().hoverColorChange(handle().getBackgroundColor().brighter());
            }
        };
        addObject(inv);

        //add item
        UIButton add = new UIButton(getName(), "add item", false) {
            @Override
            public void leftMouseReleased() {
                PlayerData player = NewObjectStorage.getPlayer(playerName).playerData();
                player.inventory().add(new Item("diarrhea"));
                refreshInv();
            }
            {
                get().setLocation( (int) inv.get().getMaxX() + 50, getGuidelineY1());
            }
        };
        applyGeneralStyle(add);
        addObject(add);
    }

}
