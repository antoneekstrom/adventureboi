package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import objects.GameObject;

public class Dialog extends Graphic {

    public Dialog(GameObject object) {
        this.object = object;
    }

    int xOffset = 0, yOffset = 0;

    void calc(Graphics2D g) {
        int w = g.getFontMetrics().stringWidth(object.getText());
        Rectangle r = object.get();

        xOffset = (r.width / 2) - (w / 2);
    }

    @Override
    public void paint(Graphics2D g) {
        //pre
        Point p = object.getDisplayCoordinate();
        calc(g);

        //draw
        g.setColor(Color.black);
        g.drawString(object.getText(), p.x + xOffset, p.y + yOffset);
    }
}