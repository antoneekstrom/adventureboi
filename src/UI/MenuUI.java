package UI;

import java.awt.Color;

public class MenuUI extends GUI {

    public MenuUI() {
        super("Menu");
    }

    public void start() {
        setVisible(true);
        //title
        addTitle("Adventureboi");
        getObjectByText("Adventureboi").get().y -= 100;

        //custom levels button
        addMenuButton("Custom Levels", 350);

        //start button
        addMenuButton("Start", 500);
        
        //settings button
        addMenuButton("Settings", 650);
        
        //quit button
        addMenuButton("Quit Game", 800);
    }
}
