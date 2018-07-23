package UI;

import java.awt.Color;
import java.util.HashMap;

import adventuregame.GlobalData;
import adventuregame.Position;
import data.PlayerData;
import gamelogic.NewObjectStorage;
import objects.NewPlayer;

public class InspectPlayerUI extends GUI {

    private String playerName = "PlayerName";
    public void playerName(String n) {
        playerName = n;
        getObjectsThatStartsWithTag("title")[0].setText(playerName);
    }

    //xp bar
    int xpOffsetY = 20;
    int xpwidth = 400, xpheight = 50;
    
    public InspectPlayerUI() {
        super("InspectPlayer");
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    @Override
    public void enable(boolean addToHistory) {
        super.enable(addToHistory);

        //refresh
        refreshList();

        //set player view
        p.setSprite(player());
        p.fixSize();
        p.resize(2f);
        p.get().x = Position.placeBetweenX(background.get().x, stats.get().x, p.get()).x;

        updateXp();
    }

    private void updateXp() {
        UIMeter xp = (UIMeter) getObjectsThatStartsWithTag("xp")[0];
        xp.get().width = p.getFullWidth();
        xp.get().setLocation(p.get().x, p.get().y - xp.get().height - xpOffsetY);

        PlayerData pd = player().playerData();
        if (pd != null) {
            xp.setValue((int) pd.experiencepoints());
            xp.setMaxValue((int) pd.experiencegoal());
            xp.textObject.setText("level " + String.valueOf(pd.experiencelevel()));
        }
    }

    @Override
    public void addedToHistory() {
        UIManager.removeFromHistory(this.getName());
    }

    UISprite p;
    UIObject background;

    public void refreshList() {
        NewPlayer player = player();
        HashMap<String, Object> map = player().playerData().getStatMap();
        String[] arr = new String[map.size()];
        int i = 0;
        for (String key : map.keySet()) {
            arr[i] = key + " : " + map.get(key);
            i++;
        }
        stats.refreshList(arr);
    }
    
    int screenWidth = GlobalData.getScreenDim().width, screenHeight = GlobalData.getScreenDim().height;
    UIList stats;
    int windowWidth = (int) (screenWidth * 0.7), windowHeight = (int) (screenHeight * 0.75);
    int listWidth = (int) (windowWidth * 0.6), listHeight = (int) (windowHeight * 0.8);
    Color BACKGROUND_COLOR = Color.white;
    int listOffset = 500;

    public void start() {

        //title
        addTitle(playerName);
        UIText title = (UIText) getObjectByText(playerName);
        title.setTag("title");
        title.get().y -= 150;
        title.textColor(Color.white);
        title.setBackgroundColor(getUIBackgroundColor());
        title.setBorderColor(Color.white);
        title.setBorderThickness(getBorderThickness());
        title.setBackgroundPadding(20);

        //background
        background = new UIObject() {
            {
                get().setSize(windowWidth, windowHeight);
                get().setLocation(xCenter(windowWidth), yCenter(windowHeight) + 75);
                setBackgroundColor(BACKGROUND_COLOR);
                setBorderColor(getUIBackgroundColor());
                this.setBorderThickness(10);
            }
        };
        addObject(background);

        //player stats list
        stats = new UIList(getName()) {
            {
                get().setSize(listWidth, listHeight);
                entryFullWidth = false;
                entryWidth = listWidth - (getBackgroundPadding() * 2) - handle().getFullWidth();
                get().setLocation(background.get().x + listOffset, this.get().y);
                Position.centerY(background.get(), this.get());
                setBackgroundColor(getUIBackgroundColor());
            }
        };
        applyListStyle(stats);
        stats.setSpacing(stats.entryHeight);
        stats.entry().setBackgroundPadding(20);
        addObject(stats);

        int pvo = 150;
        //player view
        p = new UISprite(getName(), player());
        p.get().setLocation(p.get().x, background.get().y + pvo);
        p.get().x = Position.placeBetweenX(background.get().x, stats.get().x, p.get()).x;
        p.setTag("playerView");
        addObject(p);

        //xp bar
        UIMeter xp = new UIMeter(getName()) {
            {
                this.setTag("xp");
                this.setForegroundColor(Color.green);
                this.get().setSize(xpwidth, xpheight);
                this.get().setLocation(p.get().x, p.get().y - this.get().height - xpOffsetY);
                this.valueSide = "middle";
                this.values.textColor(Color.black);
                this.values.setFontSize(this.values.getFontSize() - 10);
                this.setBackgroundColor(getUIBackgroundColor());
            }
        };
        addObject(xp);
    }

    public NewPlayer player() {return NewObjectStorage.getPlayer(playerName);}

}
