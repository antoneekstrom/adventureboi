package objects;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import adventuregame.Images;

public class NewPlayer extends NewObject implements ObjectMethods {

    private HashMap <String, BufferedImage> playerimages;

    public NewPlayer() {
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
        
    }

    public void animate() {
    }

    public void intersect() {

    }

    public void update() {
        super.update();
    }

}
