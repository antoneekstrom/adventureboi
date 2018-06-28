package items;

import gamelogic.Item;

public class DeceasedAngryShroom extends Item {

	private static final long serialVersionUID = 1L;

    public DeceasedAngryShroom() {
        super("angryshroom");
        description = new String[] {"A very angery boi", "who has now been deceased."};
        effect = "+5 attack damage";
        addTag(STATUP);
    }

}