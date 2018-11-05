package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import objects.GameObject;

public abstract class Graphic {

    GameObject object;
    private Graphics2D graphics;

    boolean visible = true;
    public void setVisible(boolean visible) { this.visible = visible; }
    public boolean isVisible() { return visible; }

    public Graphic(GameObject object) {
        this.object = object;
    }

    public Graphic() {}

    public void setObject(GameObject object) { this.object = object; }

    private void setGraphics(Graphics2D g) { graphics = g; }

    public Graphics2D getGraphics() { return graphics; }

    /**
     * Where the actual painting is being done. This one should never be called explicitly.
     * @param g {@code Graphics2D} object that is used for painting.
     */
    public abstract void paintComponent(Graphics2D g);

    private void paint(Graphics2D g) {
        setGraphics(g);
        if (isVisible()) {
            paintComponent(g);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paint(g2d);
    }

    public void paint(Graphics2D g, GameObject object) {
        setObject(object);
        paint(g);
    }

    public void paint(Graphics g, GameObject object) {
        setObject(object);
        paint(g);
    }

    public void drawRectangle(Rectangle r, Color c) {
        Graphics2D g = getGraphics();
        g.setColor(c);
        g.fillRect(r.x, r.y, r.width, r.height);
    }

}