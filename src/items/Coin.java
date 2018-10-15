package items;

import java.util.HashMap;

import gamelogic.Item;

public class Coin extends Item implements Currency {

	private static final long serialVersionUID = 1L;
    public static final String COPPER = "boin_copper", SILVER = "boin_silver", GOLD = "boin_gold2";
    public static final String BOIN = "boin";

    double VALUE;

    public Coin(int value) {
        super("boin");
        start();
        setValue(value);
    }
    
    public Coin() {
        super("boin");
        start();
        setValue(Currency.SMALL);
    }

    private void start() {
        description = description();
        effect = effect();
        addTag(Item.CURRENCY);
        addTag(Item.MISC);
        addTag(BOIN);
        imageName(COPPER);
    }

    public void setSize() {
        if (VALUE <= Currency.MEDIUM2) {
            size.setSize(25, 25);
            imageName(COPPER);
        }
        else if (VALUE <= Currency.LARGE) {
            size.setSize(50, 50);
            imageName(SILVER);
        }
        else if (VALUE >= Currency.HUGE) {
            size.setSize(75, 75);
            imageName(GOLD);
        }
    }

    public void setValue(double value) {
        VALUE = value;
        setSize();
    }
    public double getValue() {return VALUE;}

    @Override
    public HashMap<String, Object> getValueMap() {
        HashMap<String, Object> map = super.getValueMap();
        map.remove("effect");
        map.remove("level");
        map.put("value", getValue());
        map.put("id", getIdentifier());
        return map;
    }

    @Override
    public String[] description() {
        String[] arr = new String[] {"A boin with a value of " + ( String.valueOf(getValue()) ) + " buck."};
        return arr;
    }

    @Override
    public String getTitle() {
        return name();
    }

    @Override
    protected void scaleStats() {
        super.scaleStats();
    }

    @Override
	public String effect() { /* Set an effect. */
		return (float)getValue() + " boin";
    }

    @Override
    public String getIdentifier() {
        return (int)getValue() + imageName() + name();
    }
}