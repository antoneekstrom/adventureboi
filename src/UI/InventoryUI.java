package UI;

import java.util.ArrayList;

import adventuregame.GameEnvironment;
import adventuregame.GlobalData;
import gamelogic.Item;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;

public class InventoryUI extends GUI {

    UIInventory inv;
    String playerName = GameEnvironment.player1Name();
    String filter = "all";

    int invwidth = 700;
    int invheight = 1000;
    int buttonwidth = 175;
    int indentation = 100;

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
        if (p.playerData().inventory() != null) {

            ArrayList<Item> l = new ArrayList<Item>();
            
            for (int i = 0; i < p.playerData().inventory().size(); i++) {
                if (p.playerData().inventory().get(i).sortingTag().equals(filter) || filter.equals("all")) {
                    try {
                        l.add(p.playerData().inventory().get(i));
                    } catch (NullPointerException e) {e.printStackTrace();}
                }
            }

            Item[] arr = new Item[l.size()];
            l.toArray(arr);

            if (arr != null) {
                inv.refreshList(arr);
            }
        }
    }

    public void selectFilter(String tag) {
        for (UIObject object : getObjectsByTag("filter")) {
            if (object.tag().replace("filter-", "").equals(tag)) {
                filter = tag;
                object.toggleForceHoverState();
            }
            else {
                if (object.getForceHoverState()) {object.toggleForceHoverState();}
            }
        }
        if (NewObjectStorage.getPlayer(1) != null) {
            refreshInv();
        }
    }

    @Override
    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(100);

        //inventory list
        inv = new UIInventory(getName()) {
            {
                get().setSize(invwidth, invheight);
                get().setLocation(GlobalData.getScreenDim().width -50 -get().width, yCenter(get().height));
                setBackgroundColor(getUITextColor());
                handle().get().setSize(50, 100);
                setSpacing(0);
                setText("invList");
                setTag("inventory");
                hasText(false);
                entry().setBackgroundPadding(0);
                setFontSize(40);
                handle().hoverColorChange(handle().getBackgroundColor().brighter());
            }
        };
        addObject(inv);
        
        //filters
        UIText t1 = new UIText(getName(), "Filters", false);
        t1.get().setLocation(indentation, getGuidelineY1());
        t1.autoAdjustBackgroundWidth(false);
        t1.get().width = buttonwidth;
        t1.setTag("filter");
        applyGeneralStyle(t1);
        addObject(t1);
        
        //sort by all
        filterButton("All", "all");

        //stats
        filterButton("Stats", "statup");

        //sort by abilities
        filterButton("Abilities", "ability");

        //equipment
        filterButton("Equipment", "equipment");

        //misc
        filterButton("Misc", "misc");

        selectFilter("all");
    }

    public void filterButton(String text, String tag) {
        UIButton filterbutton = new UIButton(getName(), text, false) {
            @Override
            public void leftMouseReleased() {
                selectFilter(tag);
            }
            {
                setTag("filter-" + tag);
                get().width = buttonwidth;
                autoAdjustBackgroundWidth(false);
                get().setLocation(indentation, getGuidelineY1());
            }
        };
        applyGeneralStyle(filterbutton);
        addObject(filterbutton);
    }

}
