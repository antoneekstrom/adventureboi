package UI;

import adventuregame.GameEnvironment;
import adventuregame.GlobalData;
import data.Players;
import gamelogic.NewObjectStorage;

public class MenuUI extends GUI {

    public MenuUI() {
        super("Menu");
    }

    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b && !GameEnvironment.levelData().name().equals("menu")) {
            UIManager.enableGUI("HUD");
        }
    }

    public void update() {
        super.update();
        
        if (Players.exists(GameEnvironment.player1Name())) {
            getObjectsThatStartsWithTag("player1_Name")[0].setText("player 1: " + GameEnvironment.player1Name());
        }
        else {
            getObjectsThatStartsWithTag("player1_Name")[0].setText("player unavailable");
        }

        UIText p2 = (UIText) getObjectsThatStartsWithTag("player2_Name")[0];
        p2.setText("player 2: " + GameEnvironment.player2Name());
        p2.get().setLocation(GlobalData.getScreenDim().width - 75 - p2.get().width, 100);
    }

    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(150);
        home(true);

        //title
        addTitle("Adventureboi");
        getObjectByText("Adventureboi").get().y = getGuidelineY1() - 50;

        //campaign
        addMenuButton("Campaign (Not Available)", getGuidelineY1());

        //custom levels button
        addMenuButton("Custom Levels", getGuidelineY1());

        //player menu
        addMenuButton("Select Player", getGuidelineY1());

        //settings button
        addMenuButton("Settings", getGuidelineY1());
        
        //quit button
        addMenuButton("Quit Game", getGuidelineY1());

        //player1 name
        UIText player1 = new UIText(getName(), "player1", false);
        player1.get().setLocation(75, 100);
        player1.setFontSize(40);
        player1.setTag("player1_Name");
        player1.autoAdjustBackground(true);
        if (NewObjectStorage.playerCount() > 0) {
            player1.setText(NewObjectStorage.getPlayer(1).getName());
        }
        addObject(player1);

        //player2 name
        UIText player2 = new UIText(getName(), "player2", false);
        player2.get().setLocation(GlobalData.getScreenDim().width - 700, 100);
        player2.setFontSize(40);
        player2.setTag("player2_Name");
        player2.autoAdjustBackground(true);
        if (NewObjectStorage.playerCount() > 1) {
            player2.setText(NewObjectStorage.getPlayer(2).getName());
        }
        if (NewObjectStorage.playersToSpawn() > 1) {player2.setVisible(true);} else {player2.setVisible(false);}
        addObject(player2);

        setVisible(true);
    }
}
