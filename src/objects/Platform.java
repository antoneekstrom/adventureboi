package objects;

public class Platform extends NewObject {

    public Platform() {
        super();
        start();
    }

    void start() {
        physics().setGravity(false);
        moveWhenColliding(false);
    }

}