package UI;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;

import data.PlayerData;
import data.Players;

public class PlayerSelectUI extends GUI {

    public PlayerSelectUI() {
        super("PlayerSelect");
    }

    private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
    private UIList list;

    private int listwidth = 600, listheight = 800;

    public void refreshList() {
        String[] arr = new String[Players.playerData().size()];
        Players.playerData().toArray(arr);
        list.refreshList(arr);
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
            setText("playerText");
            hasText(false);
            entry().setBackgroundPadding(25);
            setSpacing(20);
            centerTextX(true);
            centerTextY(true);
            setFontSize(40);
            handle().hoverColorChange(handle().getBackgroundColor().brighter());
        }};
        addObject(list);
        refreshList();

        //new player button
        UIButton newPlayer = new UIButton(getName(), "New Player", false) {{
            get().setLocation((int)list.get().getMaxX() + 50, 400);
        }};
        applyGeneralStyle(newPlayer);
        addObject(newPlayer);

        //refresh button
        UIButton refresh = new UIButton(getName(), "Refresh", false) {{
            get().setLocation((int)list.get().getMaxX() + 50, 535);
            setTag("refreshPlayerList");
        }};
        applyGeneralStyle(refresh);
        addObject(refresh);
    }

}
