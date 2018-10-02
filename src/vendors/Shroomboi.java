package vendors;

import adventuregame.Images;
import items.DeceasedAngryShroom;
import objects.Player;
import objects.Vendor;

public class Shroomboi extends Vendor {

    public Shroomboi() {
        super();
        setPlayerRange(450);
        setImage(Images.getImage("longvendor"));
        get().setSize(150, 300);
    }

    int price = 23;

    @Override
    public boolean interact(Player player) {
        boolean successful = player.purchase(price);

        if (successful) {
            givePrompt("haha nice get scammed bitch that was way too expensive");
            player.addItem(new DeceasedAngryShroom());
        }
        else {
            givePrompt("Woah you are to poor to purchase my wares, that is very unfortunate.");
        }

        return successful;
	}

}