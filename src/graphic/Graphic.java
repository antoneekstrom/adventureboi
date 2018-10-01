package graphic;

import java.awt.Graphics;
import java.awt.Graphics2D;

import objects.GameObject;

public abstract class Graphic {

    GameObject object;

    public Graphic(GameObject object) {
        this.object = object;
    }

    public Graphic() {}

    public void setObject(GameObject object) {
        this.object = object;
    }

    public abstract void paint(Graphics2D g);

    public void paint(Graphics2D g, GameObject object) {
        setObject(object);
        paint(g);
    }

    public void paint(Graphics g, GameObject object) {
        setObject(object);
        paint(g);
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        paint(g2d);
    }

}