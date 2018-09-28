package objects;

import adventuregame.Images;

public class Dummy extends GameObject {

    public Dummy() {
        super();
        setImage(Images.getImage("lowercaseb"));
        enableHealthModule(69);
        getHealthModule().showHp(true);
        getHealthModule().healthbar().showLevel();
    }

    @Override
    public void damage(int i) {
        getHealthModule().damageNumber(i);
    }
}