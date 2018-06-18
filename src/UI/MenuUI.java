package UI;

import java.awt.Rectangle;

import gamelogic.NewObjectStorage;

public class MenuUI extends GUI {

    public MenuUI() {
        super("Menu");
    }

    public void start() {
        setVisible(true);

        setGuidelineSpacing(150);
        setGuidelineY1(150);

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
        player1.setFontSize(50);
        player1.autoAdjustBackground(true);
        if (NewObjectStorage.playerCount() > 0) {
            player1.setText(NewObjectStorage.getPlayer(1).getName());
        }
        addObject(player1);
    }
}
