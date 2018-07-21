package objects;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import adventuregame.Position;
import gamelogic.NewCamera;
import gamelogic.ObjectCreator;
import gamelogic.ObjectPlacement;

public class ObjectPreview extends NewObject {

    private float opacity = 0.5f;
    private boolean followMouse = true;

    public void opacity(float f) {opacity = f;}
    public void followMouse(boolean b) {followMouse = b;}

    public ObjectPreview(NewObject object) {
        super();
        setImage(object.getImage());
        start();
    }

    public ObjectPreview(BufferedImage image) {
        super();
        setImage(image);
        start();
    }

    public void set(NewObject object) {
        get().setSize(object.get().getSize());
        setImage(object.getImage());
    }

    @Override
    protected void updateDisplayCoordinates() {
        super.updateDisplayCoordinates();
    }

    @Override
    public void select() {
    }

    public void setLocation(Point p) {
        get().setLocation(p);
    }

    private void start() {
        setCollision(false);
        moveWhenColliding(false);
        collidable(false);
        setCamera(false);
    }

    void followMouse() {
        if (followMouse && ObjectCreator.useGrid() && ObjectPlacement.distanceToClosestPoint(get()) <= ObjectPlacement.MAX_SNAP_DISTANCE) {
            get().setLocation(ObjectPlacement.closestPoint(Position.centerOnPoint(NewCamera.getMouse(), get())));
        }
        else if (followMouse) {
            get().setLocation(Position.centerOnPoint(NewCamera.getMouse(), get()).getLocation());
        }
    }

    @Override
    protected void logic() {
        super.logic();
        followMouse();
    }

    @Override
    public void paint(Graphics g) {

        //apply transparency
        Graphics2D g2d = (Graphics2D) g;
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        super.paint(g);

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        //draw grid
        ObjectPlacement.drawGrid(g, this);
    }

}