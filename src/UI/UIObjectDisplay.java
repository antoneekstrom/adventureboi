package UI;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import adventuregame.Position;
import gamelogic.Item;
import objects.ItemObject;
import objects.GameObject;

public class UIObjectDisplay extends UIObject {

    GameObject object;
    Item item;
    Rectangle imageBox;
    
    public UIObjectDisplay(String parentname, GameObject object) {
        this.object = object;
        setParentName(parentname);
        start();
        objectStart();
    }

    public UIObjectDisplay(String parentname, Item item) {
        this.item = item;
        setParentName(parentname);
        start();
        itemStart();
    }

    void start() {
    }

    private void objectStart() {
        object.setCamera(false);
    }

    private void itemStart() {
        object = new ItemObject(item);
        objectStart();    
    }

    @Override
    public void update() {
        super.update();
        objectUpdate();
    }

    private void objectUpdate() {
        //center object box inside UIObject box
        object.setDisplayCoordinate(Position.centerOnPoint(new Point((int)get().getCenterX(), (int)get().getCenterY()), object.get()).getLocation());
    }

    public void paint(Graphics g) {
        super.paint(g);
        object.paint(g);
    }

}