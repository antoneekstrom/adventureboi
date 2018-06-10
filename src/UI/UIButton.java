package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class UIButton {

    private Rectangle r = new Rectangle();
    private Color color = Color.ORANGE;
    private HudText text;

    public UIButton() {

    }

    public Rectangle get() {
        return r;   
    }

    public void color(Color c) {
        color = c;
    }

    public void update() {
        if (text != null) {text.update();}
    }

    public void addText(HudText t) {
        text = t;
    }

    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(r.x, r.y, r.width, r.height);
        if (text != null) {text.paint(g);}
    }

}
