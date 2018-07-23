package UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import adventuregame.GlobalData;
import adventuregame.Input;
import gamelogic.ObjectInspector;

public class UIObject {
    
    private Rectangle box = new Rectangle(0, 0, 100, 100);
    private Color BACKGROUND_COLOR = Color.orange;
    private Color HIGHLIGHT_COLOR = Color.WHITE;
    private Color TEXT_COLOR = Color.white;
    private Color HIGHLIGHT_TEXT_COLOR = Color.orange;
    private Color BORDER_COLOR = Color.white;
    private Color activeTextColor = TEXT_COLOR;
    private Color activeBackgroundColor = BACKGROUND_COLOR;

    private UIObject tooltip;
    private boolean hasTooltip = false;
    
    private boolean textColorChange = false;
    private boolean hasBackground = false;
    private boolean hasText = false;
    private boolean showOutline = false;
    private boolean autoAdjustBackgroundWidth = false;
    private boolean autoAdjustBackgroundHeight = false;
    private boolean backgroundHoverColorChange = false;
    private boolean centerInParentX = false;
    private boolean hasBorder = false;
    private boolean visible = true;
    private boolean hasTag = false;
    private boolean takeInput = false;
    private boolean typeable = false;
    private boolean forceHoverState = false;
    private boolean alwaysOnTop = false;
    private boolean alignRight = false;

    public void alignRight(boolean b) {alignRight = b;}
    
    private String submittedInput = "";
    private String inputPrefix = "input: ";
    private Rectangle parentRectangle;
    protected int BORDER_THICKNESS = 10;
    private int backgroundPadding = 0;
    private Font font;
    private float FONT_SIZE = 40;
    private String tag = "none";
    protected String text = "";
    private int textWidth = 0;
    private int textHeight = 0;
    private boolean centerTextX = false;
    private boolean centerTextY = false;
    private int textCenterWidth = 0;
    protected int textCenterHeight = 0;
    
    private String parentName;
    
    public void setParentName(String s) {
        parentName = s;
    }

    public void setVisible(boolean b) {
        visible = b;
    }

    public boolean visible() {
        return visible;
    }

    private void setColors() {
        BACKGROUND_COLOR = getParent().getUIBackgroundColor();
        HIGHLIGHT_TEXT_COLOR = getParent().getUIBackgroundColor();
    }

    public UIObject() {
        font = GlobalData.getStandardFont();
    }

    public UIObject(String parentname) {
        font = GlobalData.getStandardFont();
        setParentName(parentname);
        setColors();
    }

    public void setTag(String s) {
        tag = s;
        hasTag = true;
    }

    public float getFontSize() {
        return FONT_SIZE;
    }

    public void enableTooltip(UIObject tooltip) {
        hasTooltip = true;
        this.tooltip = tooltip;
    }

    public void setInputPrefix(String s) {inputPrefix = s; takeInput(true);}
    public boolean hasTag() {return hasTag;}
    public void takeInput(boolean b) {takeInput = b;}
    public boolean takeInput() {return takeInput;}
    public void typeable(boolean b) {typeable = b;}
    public boolean typeable() {return typeable;}
    public String getInputPrefix() {return inputPrefix;}

    private void write() {
        if (typeable) {
            setText(inputPrefix + ":" + Input.getInputString());
        }
    }

    public void useInput() {
        if (tag().equals("objectInspect")) {
            ObjectInspector.changeProperties(getInputPrefix(), submittedInput);
        }
    }

    public void submitInput(String s) {
        submittedInput = s;
        useInput();
    }
    public String getInput() {return submittedInput;}

    public void clearInput() {submittedInput = "";}

    public void toggleTyping() {
        if (takeInput) {
            toggleForceHoverState();
            if (typeable()) {
                typeable(false);
                Input.keyInput(false);
                Input.enableMovement(true);
                Input.enableUIKeys(true);
            }
            else {
                typeable(true);
                Input.enableUIKeys(false);
                Input.focusObject(this);
                Input.enableMovement(false);
                Input.keyInput(true);
            }
        }
    }

    public String tag() {
        return tag;
    }
    
    public void setFontSize(float i) {
        FONT_SIZE = i;
        font = font.deriveFont(FONT_SIZE);
    }

    public void copyProperties(UIObject o) {
        BACKGROUND_COLOR = o.getBackgroundColor();
        TEXT_COLOR = o.getTextColor();
    }

    public void setHoverTextColor(Color c) {
        textColorChange = true;
        HIGHLIGHT_TEXT_COLOR = c;
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

    public Color getHoverTextColor() {return HIGHLIGHT_TEXT_COLOR;}
    public Color getHoverBackgroundColor() {return HIGHLIGHT_COLOR;}

    public void hasBorder(boolean b) {
        hasBorder = b;
    }

    public void setBorderColor(Color c) {
        hasBorder = true;
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
        box.setLocation( (parentRectangle.x + parentRectangle.width / 2) - (get().width / 2), get().y);
    }

    public void centerInParentX(boolean b) {
        centerInParentX = b;
    }

    public void setBackgroundPadding(int i) {
        backgroundPadding = i;
    }

    public Rectangle parentRectangle() {return parentRectangle;}
    
    public int getTextWidth() {
        return textWidth;
    }

    public int getFullHeight() {
        int height = get().height + backgroundPadding;
        if (hasBorder) {height += BORDER_THICKNESS;}
        return height;
    }

    public int getFullWidth() {
        int width = get().width + backgroundPadding;
        if (hasBorder) {width += BORDER_THICKNESS;}
        return width;
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

    private void alignRight() {
        if (alignRight) {
            get().x = GlobalData.getScreenDim().width - get().width - 100;
        }
    }
    
    public void update() {
        setTextCenterWidth();
        setTextCenterHeight();
        adjustBackgroundSize();
        onMouseOver(checkMouse());
        write();
        forceHoverState();
        alignRight();
        if (hasTooltip) {tooltip.update();}
        if (centerInParentX) {centerInParentX();}
    }

    /** Force UIObject to look like when it is being hovered upon. */
    public void toggleForceHoverState() {
        if (forceHoverState) {forceHoverState = false;} else {forceHoverState = true;}
    }

    public boolean getForceHoverState() {return forceHoverState;}

    private void forceHoverState() {
        if (forceHoverState) {
            activeBackgroundColor = HIGHLIGHT_COLOR;
            activeTextColor = HIGHLIGHT_TEXT_COLOR;
        }
        else {
        }
    }
    
    private void hoverColorChange(boolean hasMouse) {
        if (backgroundHoverColorChange && hasMouse) {
            activeBackgroundColor = HIGHLIGHT_COLOR;
        }
        else {
            activeBackgroundColor = BACKGROUND_COLOR;
        }
        if (textColorChange && hasMouse) {activeTextColor = HIGHLIGHT_TEXT_COLOR;}
        else {activeTextColor = TEXT_COLOR;}
    }

    public int getBackgroundPadding() {
        return backgroundPadding;
    }

    public void onMouseOver(boolean hasMouse) {
        hoverColorChange(hasMouse);
        if (hasTooltip) {
            if (hasMouse) {
                tooltip.setVisible(true);
            } else {tooltip.setVisible(false);}
        }
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
        if (UIManager.getGUI(parentName).isVisible() && getActualBox().contains(GlobalData.getMouse()) && (getParent().onTop() || alwaysOnTop) ) {
            b = true;
        }
        return b;
    }

    public void alwaysOnTop(boolean b) {alwaysOnTop = b;}

    /** Invoked when mouse is pressed on this element. */
    public void leftMousePressed() {

    }

    /** Invoked when mouse is released on this element. */
    public void leftMouseReleased() {
        toggleTyping();
    }

    /** Invoked when left mouse button has been released anywhere onscreen. */
    public void leftMouseReleasedSomewhere() {

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
        if (visible()) {
            if (hasBackground) {
                g.setColor(activeBackgroundColor);
                g.fillRect(get().x - backgroundPadding / 2, get().y - backgroundPadding / 2, get().width + backgroundPadding, get().height + backgroundPadding);
            }
            if (hasText) {
                g.setFont(font);
                textWidth = g.getFontMetrics().stringWidth(text);
                textHeight = g.getFontMetrics().getHeight();
                g.setColor(activeTextColor);
                g.drawString(text, get().x + textCenterWidth, get().y + textCenterHeight);
            }
            if (showOutline) {
                g.drawRect(get().x, get().y, get().width, get().height);
            }
            if (hasBorder) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setStroke(new BasicStroke(BORDER_THICKNESS));
                g2d.setColor(BORDER_COLOR);
                g2d.drawRect(get().x - BORDER_THICKNESS / 2 - backgroundPadding / 2, get().y - BORDER_THICKNESS / 2 - backgroundPadding / 2, getFullWidth(), getFullHeight());
            }
            if (hasTooltip) {
                tooltip.paint(g);
            }
        }
    }

    public void hasText(boolean b) {hasText = b;}
    public boolean hasText() {return hasText;}
    
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
