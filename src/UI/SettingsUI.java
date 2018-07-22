package UI;

import java.awt.Dimension;

import data.Configuration;

public class SettingsUI extends GUI {

    String UICOLOR = "Set UI Color";
    int MAXVOLUME = 100;
    Dimension SLIDERDIM = new Dimension(500, 55);
    String VOLSLIDER_TEXT = "Volume";

    public SettingsUI() {
        super("Settings");
        setGuidelineSpacing(150);
        setGuidelineY1(200);
    }
    int timesVisible = 0;
    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            UISlider s = (UISlider) getObjectByText(VOLSLIDER_TEXT);
        }
    }

    public void start() {
        //back
        addBackButton();

        //title
        addTitle("Settings");
        getObjectByText("Settings").get().y = getGuidelineY1() - 50;

        //keybindings button
        addMenuButton("Keybindings", getGuidelineY1());

        //volume slider
        int volume = Integer.parseInt(Configuration.getProperty("volume"));

        addObject(new UISlider(getName(), MAXVOLUME) {
            @Override
            public void setValue(double d) {
                super.setValue(d);
                Configuration.setProperty("volume", String.valueOf((int)value()));
            }
            {
                //transform
                get().y = getGuidelineY1();
                centerInParentX(true);
                centerTextX(true);
                get().setSize(SLIDERDIM);

                //style
                applySliderStyle(this);
                setText(VOLSLIDER_TEXT);
                VALUE_UNIT = "%";

                setValue(volume);
                moveHandleToValue(value());
            }
        });

        //UI color
        addMenuButton(UICOLOR, getGuidelineY1(), new NavTask() {
            @Override
            public void run() {
                UIManager.enableGUI("ColorPicker_UIColor");
            }
        });

    }
}
