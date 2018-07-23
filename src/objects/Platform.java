package objects;

public class Platform extends GameObject {

    public Platform() {
        super();
        start();
    }

    void start() {
        physics().setGravity(false);
        moveWhenColliding(false);
    }

}