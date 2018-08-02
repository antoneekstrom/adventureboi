package objects;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

import adventuregame.GlobalData;
import gamelogic.Camera;
import gamelogic.ObjectCreator;
import gamelogic.ObjectStorage;

public class Background {

    BufferedImage background;
    Point pos = new Point(0, 0);

    Dimension bgSize, screenSize;

    int levelWidth, minX, maxX;
    public double percentTraveled, distanceTraveled;

    public Background(BufferedImage bg) {
        background = bg;
        bgSize = new Dimension(background.getWidth(), background.getHeight());
        screenSize = GlobalData.getScreenDim();
        LevelWidth();
        percentTraveled();
    }

    void percentTraveled() {

        int cameraX = Camera.getCameraPosition().x + (screenSize.width / 2);
        distanceTraveled = cameraX - minX;
        percentTraveled = distanceTraveled / levelWidth;

        if (percentTraveled < 0) {percentTraveled = 0;}
        else if (percentTraveled > 1) {percentTraveled = 1;}
    }

    public void reCalculate() {
        LevelWidth();
        percentTraveled();
        setBackgroundPos();
    }

    public void update() {
        reCalculate();
    }

    void setBackgroundPos() {
        pos.x = 0 - (int) (percentTraveled * (bgSize.width / 2));
    }

    void LevelWidth() {

        minX = 0;
        maxX = 0;
        for (GameObject object : ObjectStorage.getObjectList()) {
            if (checkObject(object)) {
                measureObject(object);
            }
        }

        levelWidth = maxX - minX;
    }

    boolean checkObject(GameObject object) {
        boolean b = true;

        if (object.getText().equals(ObjectCreator.PREVIEW_OBJECT)) {b = false;}
        if (object.getClass().equals(Player.class) || Projectile.class.isInstance(object)) {b = false;}

        return b;
    }

    void measureObject(GameObject object) {

        int objectMinX = (int)object.get().getMinX(),
            objectMaxX = (int)object.get().getMaxX();

        if (objectMaxX > maxX) {
            maxX = objectMaxX;
        }
        
        if (objectMinX <= minX) {
            minX = objectMinX;
        }
    }

    public void paint(Graphics g) {
        g.drawImage(background, pos.x, pos.y, null);
    }

}