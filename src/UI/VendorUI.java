package UI;

import gamelogic.ObjectStorage;
import objects.Vendor;

public class VendorUI extends GUI {

    public VendorUI() {
        super("vendor");
    }

    List list;
    BoinCounter boinCounter;

    @Override
    public void start() {
        super.start();

        //items
        list = new List(getName());
        list.setText("nice");
        list.get().translate(0, 300);
        list.setBackgroundColor(getUITextColor());
        list.centerInParentX(true);
        addObject(list);

        //boins
        boinCounter = new BoinCounter(ObjectStorage.getPlayer(1).getName(), getName());
        addObject(boinCounter);
    }

    void setBoinCounterPosition() {
        boinCounter.get().setLocation((int) list.get().getMaxX() + 200, list.get().y + list.getFullHeight() / 2);
    }

    void updateCounter() {
        boinCounter.updateValue();
        setBoinCounterPosition();
    }

    public static void updateValues() {
        VendorUI v = (VendorUI) UIManager.getGUI("vendor");
        v.updateCounter();
    }

    public void enable(Vendor v) {
        //list
        list.refresh( v.getListEntries(getName()) );
        list.setText("trade with " + v.getName());

        //counter
        boinCounter.setPlayerToClosest(v.getCenter());
        boinCounter.updateValue();
        setBoinCounterPosition();

        enable(false);
    }

}