package graphic;

import java.awt.Graphics2D;
import java.awt.Point;

import objects.GameObject;

public class Dialog extends Graphic {

    ResponsiveText rt;
    int textWidth = 200, yOffset = -75;

    public Dialog(GameObject object) {
        setObject(object);

        rt = new ResponsiveText("", textWidth);
    }

    public void setyOffset(int yOffset) { this.yOffset = yOffset; }
    public void setTextWidth(int textWidth) { this.textWidth = textWidth; }

    Point getDisplayLocation() {
        Point p = object.getDisplayCenter();

        p.y -= (object.getHeight() / 2) + yOffset;
        
        return p;
    }

    @Override
    public void paint(Graphics2D g) {
        rt.setText(object.getText());
        rt.paint(g, getDisplayLocation());
    }
}