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

    /** Show value of meter. */
    UIText values;

    /** If meter values should be on the left or right. */
    String valueSide = "right";

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
        hasText(true);
        textObject = new UIText(getParentName(), text, false);
        textObject.textColor(getTextColor());
        textObject.autoAdjustBackground(true);
        textObject.setParentRectangle(get());
        textObject.textColor(getTextColor());
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

    public double value() {
        return value;
    }

    public void showValues() {
        values = new UIText(getParentName(), value + "/" + maxValue, false);
        values.textColor(getTextColor());
        values.autoAdjustBackground(true);
        values.setParentRectangle(get());
        values.centerInParentX(false);
        values.textColor(getTextColor());
        values.centerTextY(true);
        values.showOutline(false);
        values.centerTextX(true);
    }

    private void start() {
        fill = new Rectangle();
        get().setSize(350, 50);
        get().setLocation(0,0);
        setBackgroundPadding(10);
        setBackgroundColor(Color.white);
        showValues();
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

    private void setValueLocation() {
        if (valueSide.equals("right")) {
            values.get().setLocation( (int) get().getMaxX() + 20, (int) get().getCenterY() - (values.get().height / 2) );     
        }
        else if (valueSide.equals("left")) {
            values.get().setLocation( (int) get().getMinX() - 20 - values.get().width, (int) get().getCenterY() - (values.get().height / 2) );     
        }
    }

    public void update() {
        super.update();
        calculatePercentage();
        setFill();
        calculateFillWidth();
        if (textObject != null) {textObject.update();}
        if (values != null) {values.update();}
        textObject.get().setLocation(get().x + get().width / 2 - textObject.getTextWidth() / 2, get().y - 20); /* Center text in meter. */
        setValueLocation();
        values.setText(value + "/" + maxValue);
    }

    public void paint(Graphics g) {
        g.setColor(getBackgroundColor());
        g.fillRect(get().x - getBackgroundPadding() / 2, get().y - getBackgroundPadding() / 2, get().width + getBackgroundPadding(), get().height + getBackgroundPadding());

        g.setColor(FOREGROUND_COLOR);
        g.fillRect(getFill().x, getFill().y, getFill().width, getFill().height);
        if (textObject != null) {
            textObject.setFontSize(getFontSize());
            textObject.paint(g);
        }
        if (values != null) {
            values.setFontSize(getFontSize());
            values.paint(g);
        }
    }

}
