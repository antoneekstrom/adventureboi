package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import adventuregame.GlobalData;

public class UIObject {
    
    private Rectangle box = new Rectangle(0, 0, 100, 100);
    private Color BACKGROUND_COLOR = Color.orange;
    private Color HIGHLIGHT_COLOR = Color.WHITE;
    private Color TEXT_COLOR = Color.white;
    private Color BORDER_COLOR = Color.white;
    private Color activeBackgroundColor = BACKGROUND_COLOR;
    
    private boolean hasBackground = false;
    protected boolean hasText = false;
    private boolean showOutline = false;
    private boolean autoAdjustBackgroundWidth = false;
    private boolean autoAdjustBackgroundHeight = false;
    private boolean backgroundHoverColorChange = false;
    private boolean centerInParentX = false;
    private boolean hasBorder = false;
    
    private Rectangle parentRectangle;
    private int BORDER_THICKNESS = 5;
    private int backgroundPadding = 0;
    private Font font;
    private float FONT_SIZE;
    protected String text = "";
    private int textWidth = 0;
    private int textHeight = 0;
    private boolean centerTextX = false;
    private boolean centerTextY = false;
    private int textCenterWidth = 0;
    private int textCenterHeight = 0;
    
    private String parentName;
    
    public void setParentName(String s) {
        parentName = s;
    }

    public UIObject() {
        font = GlobalData.getStandardFont();
    }
    
    public void setFontSize(float i) {
        FONT_SIZE = i;
        font = font.deriveFont(FONT_SIZE);
    }

    public void copyProperties(UIObject o) {
        BACKGROUND_COLOR = o.getBackgroundColor();
        TEXT_COLOR = o.getTextColor();
    }

    public boolean hasBackground() {
        return hasBackground;
    }
    
    public String getParentName() {
        return parentName;
    }
    
    public void showOutline(boolean b) {
        showOutline = b;
    }

    public void hasBorder(boolean b) {
        hasBorder = b;
    }

    public void setBorderColor(Color c) {
        BORDER_COLOR = c;
    }

    public void setBorderThickness(int i) {
        BORDER_THICKNESS = i;
    }

    public void hoverColorChange(Color secondarycolor) {
        backgroundHoverColorChange = true;
        HIGHLIGHT_COLOR = secondarycolor;
    }

    public void setParentRectangle(Rectangle r) {
        parentRectangle = r;
    }
    
    /** Center UIObject on x-axis (horizontally) in the GUI box. */
    private void centerInParentX() {
        if (parentRectangle == null) {
            parentRectangle = getParent().get();
        }
        box.setLocation( (parentRectangle.width / 2) - (get().width / 2), get().y);
    }

    public void centerInParentX(boolean b) {
        centerInParentX = b;
    }

    public void setBackgroundPadding(int i) {
        backgroundPadding = i;
    }
    
    public int getTextWidth() {
        return textWidth;
    }

    public int getFullHeight() {
        return get().height + backgroundPadding;
    }
    
    public GUI getParent() {
        return UIManager.getGUI(parentName);
    }
    
    /** Center text in UIObject on x-axis (horizontally). */
    public void centerTextX(boolean b) {
        centerTextX = b;
    }
    
    /** Get UIObject box/rectangle. */
    public Rectangle get() {
        return box;
    }
    
    /** Give UIOBject new box/rectangle. */
    public void setBox(Rectangle newBox) {
        box = newBox;
    }
    
    public void update() {
        setTextCenterWidth();
        setTextCenterHeight();
        adjustBackgroundSize();
        onMouseOver(checkMouse());
        if (centerInParentX) {centerInParentX();}
    }

    private void hoverColorChange(boolean hasMouse) {
        if (backgroundHoverColorChange && hasMouse) {
            activeBackgroundColor = HIGHLIGHT_COLOR;
        }
        else {
            activeBackgroundColor = BACKGROUND_COLOR;
        }
    }

    public int getBackgroundPadding() {
        return backgroundPadding;
    }

    public void onMouseOver(boolean hasMouse) {
        hoverColorChange(hasMouse);
    }
    
    public void setTextCenterWidth() {
        if (hasText && centerTextX) {
            textCenterWidth = 0 - (textWidth / 2) + (get().width / 2);
        }
        else {
            textCenterWidth = 0;
        }
    }
    
    public void setText(String s) {
        text = s;
        hasText = true;
    }

    public void setTextCenterHeight() {
        if (hasText && centerTextY) {
            textCenterHeight = 0 + (get().height / 2) + (textHeight / 4);
        }
        else {
            textCenterHeight = 0;
        }
    }

    private void adjustBackgroundSize() {
        if (autoAdjustBackgroundWidth) {
            get().width = textWidth;
        }
        if (autoAdjustBackgroundHeight) {
            get().height = textHeight;
        }
    }

    public int getFullWidth() {
        return get().width + backgroundPadding;
    }

    public void autoAdjustBackgroundWidth(boolean b) {
        autoAdjustBackgroundWidth = b;
    }

    public void autoAdjustBackgroundHeight(boolean b) {
        autoAdjustBackgroundHeight = b;
    }

    public void autoAdjustBackground(boolean b) {
        autoAdjustBackgroundHeight = b;
        autoAdjustBackgroundWidth = b;
    }

    public void centerTextY(boolean b) {
        centerTextY = b;
    }
    
    /** Returns true if object contains mouse. */
    public boolean checkMouse() {
        boolean b = false;
        if (UIManager.getGUI(parentName).isVisible() && getActualBox().contains(GlobalData.getMouse())) {
            b = true;
        }
        return b;
    }

    /** Get calculated box that includes padding and such. */
    public Rectangle getActualBox() {
        return new Rectangle(getActualLocation().x, getActualLocation().y, getFullWidth(), getFullHeight());
    }

    /** Returns calculated position that includes padding and such. */
    public Point getActualLocation() {
        return new Point(get().x - backgroundPadding / 2, get().y - backgroundPadding / 2);
    }
    
    /** Method called when object is clicked. */
    public void click() {
        
    } 

    public String getText() {
        return text;
    }
    
    public void paint(Graphics g) {
        if (hasBackground) {
            g.setColor(activeBackgroundColor);
            g.fillRect(get().x - backgroundPadding / 2, get().y - backgroundPadding / 2, get().width + backgroundPadding, get().height + backgroundPadding);
        }
        if (hasText) {
            g.setFont(font);
            textWidth = g.getFontMetrics().stringWidth(text);
            textHeight = g.getFontMetrics().getHeight();
            g.setColor(TEXT_COLOR);
            g.drawString(text, get().x + textCenterWidth, get().y + textCenterHeight);
        }
        if (showOutline) {
            g.drawRect(get().x, get().y, get().width, get().height);
        }
        if (hasBorder) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(BORDER_THICKNESS));
            g2d.setColor(BORDER_COLOR);
            g2d.drawRect(get().x - BORDER_THICKNESS * 2, get().y - BORDER_THICKNESS * 2, get().width + BORDER_THICKNESS * 4, get().height + BORDER_THICKNESS * 4);
        }
    }
    
    public void setBackgroundColor(Color c) {
        BACKGROUND_COLOR = c;
        hasBackground = true;
    }

    public Color getBackgroundColor() {
        return BACKGROUND_COLOR;
    }
    
	public Color getTextColor() {
        return TEXT_COLOR;
	}
    
	public void textColor(Color c) {
        TEXT_COLOR = c;
	}
    
}
