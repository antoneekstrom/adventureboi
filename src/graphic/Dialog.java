package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import objects.GameObject;

public class Dialog extends Graphic implements TextGraphic {

    ResponsiveText rt;
    static public int DEFAULT_TEXT_WIDTH = 500;
    int yOffset = -10, textWidth = DEFAULT_TEXT_WIDTH;

    public static final Color BACKGROUND_COLOR = new Color(0.38f, 0.38f, 0.38f, 0.6f), FONT_COLOR = Color.white;
    public static final Font DIALOG_FONT = new Font("Comic Sans MS", Font.PLAIN, 40);

    public Dialog(GameObject object) {
        setObject(object);

        rt = new ResponsiveText("", DEFAULT_TEXT_WIDTH);
        rt.setFont(DIALOG_FONT);
        rt.setFontColor(FONT_COLOR);
    }

    public void setyOffset(int yOffset) { this.yOffset = yOffset; }
    public void setTextWidth(int textWidth) { textWidth = DEFAULT_TEXT_WIDTH; }

    Point getDisplayLocation() {
        Point p = object.getDisplayCenter();

        p.y += -(object.getHeight() / 2) - rt.getTotalHeight() + yOffset;

        p.x -= (rt.getmaxWidth() * 0.5);
        
        return p;
    }

    void drawBackground(Graphics2D g) {
        rt.setLocation(getDisplayLocation());
        drawRectangle(rt.getBounds(), BACKGROUND_COLOR);
    }

    @Override
    public void paintComponent(Graphics2D g) {
        g.setFont(DIALOG_FONT);

        rt.setText(object.getText());
        drawBackground(g);
        rt.paint(g, getDisplayLocation());
    }

    @Override
    public void update() {
        rt.update();
    }

    @Override
    public void setWidth(int i) {
        rt.setWidth(i);
    }

    @Override
    public int getTotalHeight() {
        return rt.getTotalHeight();
    }
}