package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class UIMeter extends UIObject {

    /** Max value of meter. */
    private double maxValue = 100;
    /** Current value of meter. */
    private double value = 50;
    /** Between 0-1, percent filled. */
    private double percent;

    /** Padding for background box. */
    private int backgroundPadding = 0;

    /** Text object */
    UIText textObject;

    /** Fill color */
    private Color FOREGROUND_COLOR = Color.orange;

    private Rectangle fill;

    public UIMeter(String parentname) {
        super();
        setParentName(parentname);
        start();
    }

    public void setForegroundColor(Color c) {
        FOREGROUND_COLOR = c;
    }

    public void createText(String s) {
        text = s;
        hasText = true;
        textObject = new UIText(getParentName(), text, false);
        textObject.textColor(getTextColor());
        textObject.centerTextX(true);
        textObject.setParentRectangle(get());
        textObject.get().setLocation(get().getLocation());
    }

    public Rectangle getFill() {
        return fill;
    }

    public void setMaxValue(double i) {
        maxValue = i;
    }

    public void setValue(double d) {
        value = d;
    }

    private void start() {
        fill = new Rectangle();
        get().setSize(350, 50);
        setBackgroundColor(Color.white);
    }

    private void calculatePercentage() {
        percent = (double) (value / maxValue);
    }

    /** Set foreground dimensions and location. */
    private void setFill() {
        //location and height will be the same as background
        fill.setLocation(get().getLocation());
        fill.height = get().height;

        //determine width and set
        calculateFillWidth();
    }

    /** Calculate width of foreground. */
    private void calculateFillWidth() {
        fill.width = (int) (get().width * percent);
    }

    public void update() {
        super.update();
        calculatePercentage();
        setFill();
        calculateFillWidth();
        if (textObject != null) {textObject.update();}
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(FOREGROUND_COLOR);
        g.fillRect(getFill().x, getFill().y, getFill().width, getFill().height);
        if (textObject != null) {textObject.paint(g);}
    }

}
