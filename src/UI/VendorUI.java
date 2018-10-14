package UI;

import objects.Vendor;

public class VendorUI extends GUI {

    public VendorUI() {
        super("vendor");
    }

    List list;

    @Override
    public void start() {
        super.start();

        list = new List(getName());
        list.setText("nice");
        list.get().translate(0, 300);
        list.setBackgroundColor(getUITextColor());
        list.centerInParentX(true);
        addObject(list);
    }

    public void enable(Vendor v) {
        list.refresh( v.getListEntries(getName()) );
        list.setText("trade with " + v.getName());
        enable(false);
    }

}