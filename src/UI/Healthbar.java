package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import objects.NewObject;

public class Healthbar {

    private Rectangle box;
    public int yOffset = -40;
    public Color BACKGROUND_COLOR = Color.white, FOREGROUND_COLOR = Color.red;
    float fillPercent = 1;

    public Healthbar(int width, int height) {
        box = new Rectangle(width, height);
    }

    public void update(double value, double maxvalue, NewObject obj) {
        Point pos = obj.getDisplayCoordinate();
        box.setLocation(pos.x + (obj.get().width / 2) - (box.width / 2), pos.y + yOffset);

        calcFillWidth(value, maxvalue);
    }

    public void calcFillWidth(double val, double maxval) {
        fillPercent = (float) (val / maxval);
        if (fillPercent < 0) {fillPercent = 0;}
    }

    public void paint(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(box.x, box.y, box.width, box.height);

        g.setColor(FOREGROUND_COLOR);
        g.fillRect(box.x, box.y, (int) (box.width * fillPercent), box.height);
    }

}