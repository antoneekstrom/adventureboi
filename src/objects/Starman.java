package objects;

import java.awt.Color;
import java.awt.Graphics;

import adventuregame.Images;

public class Starman extends NewObject implements ObjectMethods {

    public Starman() {
        super();
        setName("starman");
    }

    public void initialize() {
        super.initialize();
        enableAnimator();
        getAnimator().addList(Images.getFolderImages("assets/animated_sprites/star"));
        getAnimator().speed(40);
        getAnimator().setIndexRange(0, getAnimator().getLastIndex());
        setImage(getAnimator().getSprite());
        get().setSize(250, 250);
        super.setColor(Color.GREEN);
    }

    public void logic() {

    }

    public void animate() {
        super.animate();
    }

    public void update() {
        super.update();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);
        g.drawString(super.getText(), getDisplayCoordinate().x, getDisplayCoordinate().y);
    }

    public void ai() {

    }

}
