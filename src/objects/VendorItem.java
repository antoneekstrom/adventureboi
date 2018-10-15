package objects;

import gamelogic.Item;

public class VendorItem {

    public Item item;
    public double price;
    public int level;

    public VendorItem(Item item, double price, int level) {
        start(item, price, level);
    }

    void start(Item item, double price, int level) {
        this.item = item;
        this.price = price;
        this.level = level;
    }

    public Item getCopy() {
        return item.duplicate(level);
    }

}