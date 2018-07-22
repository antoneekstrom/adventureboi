package UI;

import java.awt.Color;
import java.util.ArrayList;

import adventuregame.GameEnvironment;
import adventuregame.GlobalData;
import gamelogic.Item;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;

public class InventoryUI extends GUI {

    UIInventory inv;
    static String playerName = GameEnvironment.player1Name();
    String filter = "all";

    int invwidth = 700;
    int invheight = 1000;
    int buttonwidth = 175;
    int indentation = 100;

    static final String BOIN_COUNTER = "BoinCounter";

    public InventoryUI() {
        super("Inventory");
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
        if (!b) {
            UIManager.getHUD().showXpBar(150, playerName);
        }
        updateBoinCounter();
    }

    void updateBoinCounter() {
        UIObject[] arr = getObjectsByTag(BOIN_COUNTER);
        if (arr.length > 0) {
            UIObject object = arr[0];
            object.setText(String.valueOf(NewObjectStorage.getPlayer(playerName).boinCount()));
        }
    }

    @Override
    public void enable(boolean addToHistory) {
        super.enable(addToHistory);
        refreshInv();
    }

    public void refreshInv() {
        playerName = GameEnvironment.player1Name();
        NewPlayer p = NewObjectStorage.getPlayer(playerName);
        if (p.playerData().inventory() != null) {

            ArrayList<Item> l = new ArrayList<Item>();
            
            for (int i = 0; i < p.playerData().inventory().size(); i++) {
                if (p.playerData().inventory(). get(i).hasTag(filter) || filter.equals("all")) {
                    try {
                        l.add(p.playerData().inventory().get(i));
                    } catch (NullPointerException e) {e.printStackTrace();}
                }
            }

            inv.refreshList(l);
        }
        updateBoinCounter();
    }

    public void selectFilter(String tag) {
        for (UIObject object : getObjectsThatStartsWithTag("filter")) {
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
        setGuidelineSpacing(100);
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

        //equipped
        filterButton("Equipped", "equipped");

        selectFilter("all");

        //inspect player
        UIButton iplayer = new UIButton(getName(), "View Player", false) {
            @Override
            public void leftMouseReleased() {
                InspectPlayerUI pui = (InspectPlayerUI) UIManager.getGUI("InspectPlayer"); 
                pui.playerName(playerName);
                UIManager.addToHistory("Inventory");
                UIManager.getGUI("InspectPlayer").enable(false);
            }
            {
                get().setSize(200, 100);
                get().setLocation(inv.get().x - this.get().width - 100, 100);
            }
        };
        applyGeneralStyle(iplayer);
        addObject(iplayer);

        
        //boincounter
        addObject(new UIText(getName(), "7", false) {
            {
                applyGeneralStyle(this);
                get().setLocation(iplayer.get().x - getFullWidth() - 50, 100);
                setTag(BOIN_COUNTER);
                setBackgroundColor(getUIBackgroundColor());
                textColor(Color.yellow);
                setBorderColor(Color.yellow);
            }
        });

        addObject(inv);
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
