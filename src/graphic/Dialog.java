package graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import objects.GameObject;

public class Dialog extends Graphic {

    ResponsiveText rt;
    int textWidth = 200, yOffset = -75;

    public Color BACKGROUND_COLOR = new Color(0.78f, 0.78f, 0.78f, 0.7f);

    public Dialog(GameObject object) {
        setObject(object);

        rt = new ResponsiveText("", textWidth);
    }

    public void setyOffset(int yOffset) { this.yOffset = yOffset; }
    public void setTextWidth(int textWidth) { this.textWidth = textWidth; }

    Point getDisplayLocation() {
        Point p = object.getDisplayCenter();

        p.y -= ((object.getHeight()) + yOffset);

        p.x -= (rt.getmaxWidth() * 1.5);
        
        return p;
    }

    void drawBackground(Graphics2D g) {
        drawRectangle(rt.getBounds(), BACKGROUND_COLOR);
    }

    @Override
    public void paintComponent(Graphics2D g) {
        rt.setText(object.getText());
        rt.paint(g, getDisplayLocation());
    }
}