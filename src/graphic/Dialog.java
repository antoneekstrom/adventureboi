package graphic;

import java.awt.Graphics2D;
import java.awt.Point;

import objects.GameObject;

public class Dialog extends Graphic {

    public Dialog(GameObject object) {
        this.object = object;
    }

    int xOffset = 0, yOffset = 0;

    @Override
    public void paint(Graphics2D g) {
        Point p = object.getDisplayCoordinate();
        g.drawString(object.getText(), p.x + xOffset, p.y + yOffset);
    }
}