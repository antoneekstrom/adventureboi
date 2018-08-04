package objects;

import adventuregame.Images;

public class MushroomBlock extends Platform {

    public MushroomBlock() {
        super();
    }

    @Override
    void start() {
        super.start();
        setImage(Images.getImage("mushroomblock"));
    }

}