package UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import adventuregame.GlobalData;

public class UISlider extends UIObject {

    private double value = 25;
    private double maxValue;
    String VALUE_UNIT = "";

    Dimension HANDLE_SIZE = new Dimension(75, 35);

    boolean handleGrabbed = false;
    public boolean grabbed() {return handleGrabbed;}
    int grabOffset = 0;

    private UIObject handle;

    public UISlider(String parentname, int maxval) {
        super();
        maxValue = maxval;
        setParentName(parentname);
        start();
    }

    public void start() {
        //handle
        handle = new UIObject(getParentName()) {
            @Override
            public void update() {
                super.update();
                if (this.hasText()) {
                    setText((int)value + VALUE_UNIT);
                }
            }
            @Override
            public void leftMousePressed() {
                super.leftMousePressed();
            }
            @Override
            public void leftMouseReleasedSomewhere() {
                super.leftMouseReleasedSomewhere();
                handleGrabbed = false;
            }
            {
                this.setParentName(getParentName());
                this.setBox(new Rectangle(get().x, get().y, 50, 30));
                this.setBackgroundColor(getTextColor());
                this.setBackgroundPadding(0);
                this.centerTextX(true);
                this.centerTextY(true);
                this.setFontSize(30);
                this.autoAdjustBackgroundHeight(true);
                this.get().setSize(HANDLE_SIZE);
                this.setBackgroundPadding(10);
            }
        };

        //this
        get().setSize(500, 55);
    }

    public void grab(Point pos) {
        handleGrabbed = true;
        if (handle().checkMouse()) {
            grabOffset = pos.x - handle().get().x;
        }

        else {
            grabOffset = handle().get().width / 2;
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            setHandleStartPos();
        }
    }

    public void setInnerPadding(int i) {
        handle.setBackgroundPadding(i);
    }
    
    public void setHandleStartPos() {
        handle.get().setLocation(get().x + getHandlePos() + handle.getBackgroundPadding(), get().y + (get().height / 2) - (handle.get().height / 2) );
    }

    private int getHandlePos() {
        return (int) ( (value / maxValue) * get().width );
    }

    private void moveHandle() {
        if (handleGrabbed) {
            handle().get().x = GlobalData.getMouse().x - grabOffset;
        }
        limitHandle();
        updateValue();
    }

    private void limitHandle() {
        if (handle().get().x <= get().x) {
            handle().get().x = get().x;
        }
        else if (handle().get().getMaxX() >= get().getMaxX()) {
            handle().get().x = (int) get().getMaxX() - handle().get().width;
        }
        handle().get().y = get().y + (get().height / 2) - (handle.get().height / 2);
    }

    @Override
    public void leftMousePressed() {
        super.leftMousePressed();
        if (handle().checkMouse()) {
            handle().leftMousePressed();
        }
        grab(GlobalData.getMouse());
    }

    @Override
    public void leftMouseReleasedSomewhere() {
        super.leftMouseReleasedSomewhere();
        handle().leftMouseReleasedSomewhere();
        setValue(value);
    }

    public void moveHandleToValue(double value) {
        handle().get().x = get().x + (int) ((value / maxValue) * get().width);
    }

    void updateValue() {
        value = maxValue * (1 - ( (get().getMaxX() - handle().get().getMaxX()) / (get().width - handle().get().width) ));
    }

    public void update() {
        super.update();
        handle.update();
        moveHandle();
    }

    public void paint(Graphics g) {
        super.paint(g);
        handle.paint(g);
    }

    public UIObject handle() {return handle;}
    public void setValue(double d) {value = d;}
    public void setMaxValue(double d) {maxValue = d;}
    public double value() {return value;}
    public double maxValue() {return maxValue;}

}
