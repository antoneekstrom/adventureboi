package objects;

import java.awt.Rectangle;

import adventuregame.AI;
import adventuregame.Images;

public class Spikeboi extends NewObject implements ObjectMethods {

    private AI ai;

    public Spikeboi() {
    }

    public void initialize() {
        super.initialize();
        super.setImage(Images.getImage("angryshroom"));
        createAI();
        ai = getAI();
    }

    public void ai() {
        ai.passObject(this);
        Rectangle r = new Rectangle(getX(), getY(), getWidth(), getHeight());
        ai.update(r);
        setX(ai.returnPos().x);
        setY(ai.returnPos().y);
    }

    public void logic() {
    }

    public void animate() {
        super.animate();
    }
    
	public void intersect() {
        super.getForce().applyForce(0, 15);
    }

    public void update() {
        super.update();
    }
}
