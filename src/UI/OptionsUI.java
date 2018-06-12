package UI;

import adventuregame.Input;

public class OptionsUI extends GUI {

    int y0 = 450;
    int buttonCount = 0;
    int interval = 150;

    public int newButton(int firsty) {
        int y = firsty;
        for (int i = 1; i <= buttonCount; i++) {
            y += interval;
        }
        buttonCount++;
        return y;
    }

    public OptionsUI() {
        super("Options");
    }

    public void start() {
        addTitle("Options");
        getObjectByText("Options").get().y = 150;
        y0 = getObjectByText("Options").get().y + 200;
        addMenuButton("Resume", newButton(y0));
        addMenuButton("Settings", newButton(y0));
        addMenuButton("Menu", newButton(y0));
        addMenuButton("Quit Game", newButton(y0));
    }

}
