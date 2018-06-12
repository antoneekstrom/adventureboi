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

        //custom levels button
        addMenuButton("Custom Levels", 450);
        
        //settings button
        addMenuButton("Settings", 600);
        
        //quit button
        addMenuButton("Quit Game", 750);
    }
}
