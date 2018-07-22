package UI;

import java.awt.Color;
import java.awt.Point;

public class ColorPickerUI extends GUI {

    String TITLE = "Pick a Color", CONFIRM = "Confirm";

    int redValue = 0, greenValue = 0, blueValue = 0, MAXVAL = 255; 

    public ColorPickerUI(String name) {
        super("ColorPicker");
        setName(getName() + "_" + name);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }

    @Override
    public void start() {
        addTitle(TITLE);
        addBackButton();
        setGuidelineY1(getObjectsByTag("title")[0].get().y + 150);

        //red slider
        addObject(new UISlider(getName(), 100) {
            @Override
            public void setValue(double d) {
                super.setValue(d);
                redValue = (int)value();
                updateColors();
            }
            @Override
            public void update() {
                super.update();
                if (grabbed()) {updateColors();}
            }
            {
                Color COLOR = Color.red;
                get().y = getGuidelineY1();
                applySliderStyle(this);
                centerInParentX(true);
                setBackgroundColor(COLOR);
                setBorderColor(Color.white);
                handle().setBackgroundColor(Color.white);
                handle().hoverColorChange(getBackgroundColor().darker());
                handle().textColor(COLOR);
                setMaxValue(MAXVAL);
            }
        });
        
        //green slider
        addObject(new UISlider(getName(), 100) {
            @Override
            public void setValue(double d) {
                super.setValue(d);
                greenValue = (int)value();
            }
            @Override
            public void update() {
                super.update();
                if (grabbed()) {updateColors();}
            }
            {
                Color COLOR = Color.green;
                get().y = getGuidelineY1();
                applySliderStyle(this);
                centerInParentX(true);
                setBackgroundColor(COLOR);
                setBorderColor(Color.white);
                handle().setBackgroundColor(Color.white);
                handle().hoverColorChange(getBackgroundColor().darker());
                handle().textColor(COLOR);
                setMaxValue(MAXVAL);
            }
        });

        //blue slider
        addObject(new UISlider(getName(), 100) {
            @Override
            public void setValue(double d) {
                super.setValue(d);
                blueValue = (int)value();
                updateColors();
            }
            @Override
            public void update() {
                super.update();
                if (grabbed()) {
                    updateColors();
                }
            }
            {
                Color COLOR = Color.blue;
                get().y = getGuidelineY1();
                applySliderStyle(this);
                centerInParentX(true);
                setBackgroundColor(COLOR);
                setBorderColor(Color.white);
                handle().setBackgroundColor(Color.white);
                handle().hoverColorChange(getBackgroundColor().darker());
                handle().textColor(COLOR);
                setMaxValue(MAXVAL);
            }
        });

        //confirm button
        addObject(new UIButton(getName(), CONFIRM, true) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                confirm(getColor());
            }
            {
                applyMenuStyle(this);
                get().y = getGuidelineY1();
            }
        });

    }

    void confirm(Color color) {
    }

    private void updateColors() {
        getObjectByText(CONFIRM).setBackgroundColor(getColor());
    }

    Color getColor() {
        return new Color(redValue, greenValue, blueValue);
    }

}