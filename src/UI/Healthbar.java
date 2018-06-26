package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import objects.NewObject;

public class Healthbar {

    private Rectangle box;
    public int yOffset = -40;
    public Color BACKGROUND_COLOR = Color.white, FOREGROUND_COLOR = Color.red, TEXT_COLOR = Color.black;
    float fillPercent = 1;

    String numberText = "";
    boolean showNumbers = true;

    public Healthbar(int width, int height) {
        box = new Rectangle(width, height);
    }

    public void update(double value, double maxvalue, NewObject obj) {
        Point pos = obj.getDisplayCoordinate();
        box.setLocation(pos.x + (obj.get().width / 2) - (box.width / 2), pos.y + yOffset);

        calcFillWidth(value, maxvalue);
        determineText( (int) value, (int) maxvalue);
    }

    public void calcFillWidth(double val, double maxval) {
        fillPercent = (float) (val / maxval);
        if (fillPercent < 0) {fillPercent = 0;}
    }

    private void determineText(int val, int maxval) {
        numberText = val + "/" + maxval;
    }

    public void paint(Graphics g) {
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(box.x, box.y, box.width, box.height);

        g.setColor(FOREGROUND_COLOR);
        g.fillRect(box.x, box.y, (int) (box.width * fillPercent), box.height);

        if (showNumbers) {
            g.setColor(TEXT_COLOR);
            g.drawString( numberText, box.x + (box.width / 2) - (g.getFontMetrics().stringWidth(numberText) / 2), box.y + (box.height / 2) - (g.getFontMetrics().getHeight() / 2) );
        }
    }

}