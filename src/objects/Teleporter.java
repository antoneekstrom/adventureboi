package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import data.ObjectData;
import data.TeleporterData;
import gamelogic.ObjectStorage;
import graphic.Dialog;

public class Teleporter extends GameObject {

    Point destination;

    public Teleporter() {
        super();
        start();
    }

    void start() {
        setDestination(new Point(0,0));
        get().setSize(200, 50);
        setColor(Color.pink);
    }

    public Point getDestination() { return destination; }
    public void setDestination(Point d) {
        destination = d;
        setText("(" + d.x + ", " + d.y + ")");
    }

    @Override
    public void playerContact(Player player) {
        super.playerContact(player);
    }

    @Override
    public ObjectData extractData() {
        return new TeleporterData(this);
    }

    @Override
    public void collide(GameObject collision) {
        super.collide(collision);
        teleport(collision);
    }

    void activate(Player player) {
        teleport(player);
    }

    void teleport(GameObject object) {
        if (object.get().getMaxY() < get().getMaxY()) {

            object.setLocation(getDestination());
        }
    }

    //info on proximity
    int range = 500;
    boolean inRange = false;
    Dialog d = new Dialog(this);

    @Override
    protected void logic() {
        super.logic();
        inRange = ObjectStorage.distanceToNearestPlayer(getCenter()) <= range;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (inRange) {
            d.paint(g, this);
        }
    }
}