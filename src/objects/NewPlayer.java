package objects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import adventuregame.Images;

public class NewPlayer extends NewObject implements ObjectMethods {

    private boolean sitting = false;
    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean jumping = false;

    private HashMap <String, BufferedImage> playerimages;
    private int playerId;
    private String debugString = "";

    private int movementSpeed = 15;

    public NewPlayer(int id) {
        playerId = id;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public int getId() {
        return playerId;
    }

    public void setDebugString(String s) {
        debugString = s;
    }

    public void sit(boolean b) {
        sitting = b;
    }

    public void moveLeft(boolean b) {
        movingLeft = b;
    }

    public void MoveRight(boolean b) {
        movingRight = b;
    }

    public void moveDown() {
        if (!getForce().hasGravity()) {
            setY(getY() + 20);
        }
    }

    public void jump(boolean b) {
        jumping = b;
    }
    
    public void initialize() {
        super.initialize();
        get().setSize(150, 125);

        //animation/images
        playerimages = Images.getImageHashMap("assets/animated_sprites/aboi");
        setImage(playerimages.get("still"));
    }

    public void ai() {
        super.ai();
    }

    public void logic() {
        //player movement
        if (movingRight) {
            setX(getX() + movementSpeed);
        }
        if (movingLeft) {
            setX(getX() - movementSpeed);
        }
        if (jumping) {
            setY(getY() -  (int) getForce().getGravityStrength() - 20);
        }
        if (sitting) {
            setImage(playerimages.get("falling"));
            moveDown();
        }
        
        if (doesIntersect()) {
            setImage(playerimages.get("still"));
        } else {
            setImage(playerimages.get("falling"));
        }
    }

    public void animate() {
    }

    public void intersect() {
    }

    public void update() {
        super.update();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawString(debugString, getDisplayCoordinate().x, getDisplayCoordinate().y - 150);
    }

}
