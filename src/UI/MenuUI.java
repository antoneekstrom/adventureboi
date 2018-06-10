package UI;

import java.awt.Color;
import java.awt.Graphics;

import adventuregame.GlobalData;

public class MenuUI extends GUI {

    public MenuUI() {
        start();
    }

    public void start() {
        HudText t = new HudText(GlobalData.getScreenDim().width / 2,
        GlobalData.getScreenDim().height / 2, "", GlobalData.getStandardFont());
        t.setText("This is text.");
        addText(t);

        UIButton b = new UIButton() {{
            color(Color.ORANGE);
            addText(new HudText(get().x, get().y, "text", GlobalData.getStandardFont()));
        }};
        addButton(b);
    }

    public void update() {
        super.update();
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

}
