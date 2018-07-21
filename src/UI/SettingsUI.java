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

    public void start() {
        //back
        addBackButton();

        //title
        addTitle("Settings");
        getObjectByText("Settings").get().y = getGuidelineY1() - 50;

        //keybindings button
        addMenuButton("Keybindings", getGuidelineY1());

        //volume slider
        addObject(new UISlider(getName(), MAXVOLUME) {
            @Override
            void updateValue() {
            super.updateValue();
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
