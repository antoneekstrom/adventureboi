package gamelogic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import UI.UIManager;
import objects.GameObject;

public class ObjectPlacement {

    /** Size of the squares on the grid. */
    private static int SQUARE_SIZE = 50;
    public static int MAX_SNAP_DISTANCE = 0;
    private static Dimension gridSize = new Dimension(700, 700);

    public static void gridSize(int size) {SQUARE_SIZE = size; createGrid();}
    public static int gridSize() {return SQUARE_SIZE;}

    /** Return the coordinates for the closest point on the grid for this rectangle.
     *  But only if it perfectly fits in the grid, as in if the rectangles sides return null when: "side % squaresize".
     *  Otherwise it returns null. */
    public static Point closestSquare(Rectangle r) {
        Point p = null;

        if (fitsInGrid(r)) {
            p = closestPoint(r);
        }

        return p;
    }

    /** closestSquare() but without fitsInGrid(). */
    public static Point closestPoint(Rectangle r) {

        int xoffset = (int) ( (r.x - r.width) % (SQUARE_SIZE) ),
        yoffset = (int) ( (r.y - r.height) % (SQUARE_SIZE) );

        UIManager.dstring = "xoffset:" + xoffset + " yoffset:" + yoffset;

        r.x -= xoffset;
        r.y -= yoffset;

        return r.getLocation();
    }

    /** Returns a distance to closest point on the grid as an integer of combined distance on x and y distance. */
    public static int distanceToClosestPoint(Rectangle r) {
        Point p = closestPoint(r);

        int xdif = r.getLocation().x - p.x;
        int ydif = r.getLocation().y - p.y;

        return xdif + ydif;
    }

    /** Returns true if rectangle fits in grid. */
    private static boolean fitsInGrid(Rectangle r) {
        if (r.width % SQUARE_SIZE == 0 && r.height % SQUARE_SIZE == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    static class DrawLine {

        int x1, x2, y1, y2;
        public DrawLine(int x1, int y1, int x2, int y2) {
            this.x1 = x1; this.x2 = x2; this.y1 = y1; this.y2 = y2;
        }

        public void run(Graphics g, GameObject object) {
            Point dc = new Point(object.getDisplayCoordinate());

            dc.x -= (gridSize.width / 2) - (object.get().width / 2);
            dc.y -= (gridSize.height / 2) - (object.get().height / 2);

            g.drawLine(x1 + dc.x, y1 + dc.y, x2 + dc.x, y2 + dc.y);
        }
    }

    private static ArrayList<DrawLine> gridLines = new ArrayList<DrawLine>();

    public static void createGrid() {
        int xamount = (int) (gridSize.width / SQUARE_SIZE);
        int yamount = (int) (gridSize.height / SQUARE_SIZE);
        
        gridLines.clear();

        for (int i = 0; i < xamount + 1; i++) {

            int y1 = 0, y2 = gridSize.height, x = i * SQUARE_SIZE;

            gridLines.add(new DrawLine(x, y1, x, y2));
        }

        for (int i = 0; i < yamount + 1; i++) {

            int x1 = 0, x2 = gridSize.width, y = i * SQUARE_SIZE;

            gridLines.add(new DrawLine(x1, y, x2, y));
        }
    }

    private static class Grid extends GameObject {

        @Override
        public void paint(Graphics g) {
            if (ObjectCreator.isEnabled()) {
                ObjectPlacement.drawGrid(g, this);
            }
        }
        {
            setCollision(false);
            moveWhenColliding(false);
            collidable(false);
            physics().setGravity(false);
            get().setLocation(0, 0);
        }

    }

    public static void drawGrid(Graphics g, GameObject object) {
        for (DrawLine dl : gridLines) {
            dl.run(g, object);
        }
    }
}