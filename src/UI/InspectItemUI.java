package UI;

import java.awt.Color;
import java.util.HashMap;

import gamelogic.Item;

public class InspectItemUI extends GUI {

    UIList inspectionstats;
    int lwidth = 600, lheight = 900;

    public InspectItemUI() {
        super("InspectItem");
    }

    public void refreshList(String[] arr) {
        inspectionstats.refreshList(arr);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    private String getDescription(String stat) {
        String s = stat;

        if (s.equals("damagepercent")) {s = "damage efficiency";}

        return s;
    }

    public void inspectItem(Item i) {
        HashMap<String, Object> stats = i.getValueMap();
        String[] arr = new String[stats.size()];

        int k = 0;
        for (String s : stats.keySet()) {
            arr[k] = getDescription(s) + ": " + stats.get(s);
            k++;
        }
        refreshList(arr);
        getObjectsByTag("title")[0].setText(i.name());
        inspectionstats.setText(i.name());
        inspectionstats.hasText(false);
        setVisible(true);
    }

    public void start() {
        addTitle("Inspect Item");
        getObjectByText("Inspect Item").setTag("title");

        //list
        inspectionstats = new UIList(getName()) {
            {
                setBackgroundColor(Color.white);
                get().setSize(lwidth, lheight);
                centerInParentX(true);
                this.get().y = yCenter(this.get().height);
                handle().get().setSize(50, 100);
                setSpacing(0);
                handle().hoverColorChange(handle().getBackgroundColor().brighter());
                centerTextX(true);
                textCenterHeight = -40;
            }
        };
        addObject(inspectionstats);

        //close button
        UIButton close = new UIButton(getName(), "Close", false) {
            @Override
            public void leftMouseReleased() {   
                UIManager.getGUI("InspectItem").setVisible(false);
            }
            {
                get().setLocation(500, 200);
            }
        };
        applyGeneralStyle(close);
        addObject(close);
        
    }

}