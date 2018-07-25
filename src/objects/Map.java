package objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

public class Map {

    /** Percent amount to scale from original size. Ex. 0.5 will make a map showing the list 50% smaller. */
    private float percent;
    public void scale(float f) {percent = f;}
    public float scale() {return percent;}

    private ArrayList<GameObject> list;
    public void setList(ArrayList<GameObject> l) {list = l;}
    public ArrayList<GameObject> list() {return list;}

    private Dimension size = new Dimension(200, 200);
    public void size(Dimension d) {size = d;}
    public Dimension size() {return size;}

    private Canvas canvas;

    /* Constructors */
    public Map() {
    }
    public Map(float scale) {
        scale(scale);
    }
    public Map(ArrayList<GameObject> l) {
        setList(l);
    }
    public Map(ArrayList<GameObject> l, float scale) {
        setList(l);
        scale(scale);
    }

    /** Display */
    private static class Canvas {

        public Canvas() {

        }

        public void paint(Graphics g) {

        }
    }

    public void paint(Graphics g) {
        if (canvas != null) {
            canvas.paint(g);
        }
    }

    /** Create the map. */
    public void create() {
        canvas = new Canvas();
    }

}