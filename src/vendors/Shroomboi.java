package vendors;

import adventuregame.Images;
import objects.Vendor;

public class Shroomboi extends Vendor {

    public Shroomboi() {
        super();
        setPlayerRange(700);
        setImage(Images.getImage("deadangryshroom"));
    }

}