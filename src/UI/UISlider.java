package UI;

import java.awt.Graphics;
import java.awt.Rectangle;

public class UISlider extends UIObject {

    private double value = 0;
    private double maxValue;

    private UIObject handle;

    public UISlider(String parentname, int maxval) {
        super();
        maxValue = maxval;
        setParentName(parentname);
        start();
    }

    public void start() {
        setBackgroundPadding(40);
        //handle
        handle = new UIObject();
        handle.setParentName(getParentName());
        handle.setBox(new Rectangle(get().x, get().y, 30, 30));
        handle.setBackgroundColor(getTextColor());
    }

    public void setInnerPadding(int i) {
        handle.setBackgroundPadding(i);
    }
    
    public void updateHandlePosition() {
        handle.get().setLocation(get().x + handle.getBackgroundPadding(), get().y + (get().height / 2) - (handle.get().height / 2) );
    }

    public void update() {
        super.update();
        handle.update();
        updateHandlePosition();
    }

    public void paint(Graphics g) {
        super.paint(g);
        handle.paint(g);
    }

    public void setValue(double d) {value = d;}
    public void setMaxValue(double d) {maxValue = d;}
    public double value() {return value;}
    public double maxValue() {return maxValue;}

}
