package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import data.PlayerData;
import data.Players;
import gamelogic.NewObjectStorage;

public class PlayerSelectUI extends GUI {

    public PlayerSelectUI() {
        super("PlayerSelect");
    }

    private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
    private UIList list;

    private String[] playerslots = new String[] {
        "player 1",
        "player 2",
    };
    private int currentPlayerSlot = 1;
    public void nextPlayerSlot() {
        if (currentPlayerSlot + 1 <= playerslots.length -1) {currentPlayerSlot++;}
        else {currentPlayerSlot = 0;}
        getObjectsByTag("playerSlotButton")[0].setText(playerslots[currentPlayerSlot]);
    }
    public int getCurrentPlayer() {return currentPlayerSlot + 1;}

    private int listwidth = 600, listheight = 800;

    public void refreshList() {
        Players.loadPlayerData();
        list.refreshList(Players.getPlayerNames());
    }

    public void setVisible(boolean b) {
        super.setVisible(b);

        //lock gui
        if (Players.playerData().size() > 0 && b) {
            addBackButton();
        }
        else if (b) {
            UIManager.lockCurrentGUI(true);
        }
        else if (!b) {
            getUIObjectList().remove(getObjectByText("Back"));
        }

        refreshList();
    }

    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(100);

        addTitle("Select Player");
        getObjectByText("Select Player").get().y = getGuidelineY1() - 50;

        list = new UIList(getName()) {{
            setBackgroundColor(Color.white);
            setBox(new Rectangle(xCenter(listwidth),getGuidelineY1() - 50, listwidth, listheight));
            handle().get().setSize(50, 100);
            setTag("playerList");
            setText("PlayerList");
            hasText(false);
            entry().get().width = (int) (get().width * 0.6);
            entry().setBackgroundPadding(0);
            setSpacing(0);
            centerTextX(true);
            centerTextY(true);
            setFontSize(40);
            handle().hoverColorChange(handle().getBackgroundColor().brighter());
        }};
        addObject(list);
        refreshList();

        //player slot selection
        UIButton playerSlot = new UIButton(getName(), "player", false) {
            @Override
            public void leftMouseReleased() {
                nextPlayerSlot();
            }
            {
                setTag("playerSlotButton");
                get().setLocation((int)list.get().getMaxX() + 50, 265);
            }
        };
        applyGeneralStyle(playerSlot);
        addObject(playerSlot);
        nextPlayerSlot();

        //new player button
        UIButton newPlayer = new UIButton(getName(), "New Player", false) {
            @Override
            public void leftMouseReleased() {
                UIManager.lockCurrentGUI(false);
            }
            {
                get().setLocation((int) list.get().getMaxX() + 50, 400);
            }
        };
        applyGeneralStyle(newPlayer);
        addObject(newPlayer);

        //refresh button
        UIButton refresh = new UIButton(getName(), "Refresh", false) {
            @Override
            public void leftMouseReleased() {
                refreshList();
            }
            {
            get().setLocation((int)list.get().getMaxX() + 50, 535);
            setTag("refreshPlayerList");
            }
        };
        applyGeneralStyle(refresh);
        addObject(refresh);

        //player amount button
        UIButton playercount = new UIButton(getName(), "", false) {
            @Override
            public void useInput() {
                int input = Integer.valueOf(getInput());
                if (input > NewObjectStorage.maxPlayers()) {input = NewObjectStorage.maxPlayers();}
                NewObjectStorage.playersToSpawn(input);
                setText("Active players:" + input);
            }
            {
                setTag("playerAmount");
                takeInput(true);
                setInputPrefix("Active players");
                setText("Active players:" + NewObjectStorage.playersToSpawn());
                get().setLocation( (int) list.get().getMaxX() + 50, 670);
                setTag("playerCount");
            }
        };
        applyGeneralStyle(playercount);
        addObject(playercount);

        //remove player
        UIButton remove = new UIButton(getName(), "Remove Player", false) {
            @Override
            public void leftMouseReleased() {
                UIManager.enableGUI("InputField_RemovePlayer");
            }
            {
                get().setLocation( (int) list.get().getMaxX() + 50, 805);
            }
        };
        applyGeneralStyle(remove);
        addObject(remove);
    }

    //player input methods
    public static void newPlayer(String name) {
        PlayerData data = new PlayerData();
        data.name(name);
        data.healthregen(0);
        data.damage(15);
        data.maxHealth(100);
        data.maxenergy(100);
        data.maxstamina(100);
        data.energyregen(0.2);
        Players.savePlayerData(data);
        Players.serializePlayerData();
    }

    public static void removePlayer(String name) {
        Players.removePlayer(name);
    }

}
