package UI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import adventuregame.GlobalData;
import adventuregame.Images;
import gamelogic.Item;
import items.abilities.Ability;

public class UITooltip extends UIObject {

    //objects
    Rectangle parentObject;
    Item item;
    
    //layout
        //background
        int width = 100, height = 100;
        int widthPadding = 75;
        //offset
        int spacing = 75;
        int descOffset = 55;
        int titleOffset = 25;
        int effectOffset = 20;
        int imageOffset = 30;
        int lineSpacing = 35;
        int abildescOffset = 15;
        //image
        int imgwidth = 75;
        int imgheight = 75;
        Rectangle imagebox = new Rectangle(width, height);

    //text
    float descriptionFontSize = 30;
    Color DESCRIPTION_COLOR = Color.gray;

    //content
    ArrayList<UIText> lines;
    BufferedImage image;


    public UITooltip(String parentname, UIObject parentobject) {
        super();
        parentObject = parentobject.get();
        setParentName(parentname);
        start();
    }

    public UITooltip(UIObject o, Item i) {
        super();
        setParentName(o.getParentName());
        parentObject = o.get();
        this.item = i;
        setText(i.name());
        start();
    }

    public void populateLines() {
        //description
        for (String text : item.description()) {
            UIText t = new UIText(getParentName(), text, true) {
                {
                    get().setLocation(get().x, get().y + descOffset);
                    setParentRectangle(get());
                    centerInParentX(true);
                    textColor(DESCRIPTION_COLOR);
                    this.setFontSize(descriptionFontSize);
                }
            };
            lines.add(t);
        }
        //title
        lines.add(new UIText(getParentName(), item.getTitle(), false) {
            {
                this.setTag("title");
                setParentRectangle(get());
                centerInParentX(true);
                this.textColor(getParent().getUIBackgroundColor());
                this.get().y = get().y + titleOffset;
            }
        });
        //effect
        lines.add(new UIText(getParentName(), item.effect(), false) {
            {
                this.setTag("effect");
                get().setLocation(get().x, get().y + descOffset + effectOffset);
                setParentRectangle(get());
                centerInParentX(true);
                textColor(getParent().getUIBackgroundColor());
                this.setFontSize(descriptionFontSize + 5);
            }
        });
        //effect description
        try {
            Ability a = (Ability) item;

            lines.add(new UIText(getParentName(), a.abilityDescription(), false) {
                {
                    this.setTag("abilitydescription");
                    get().setLocation(get().x, get().y + descOffset + effectOffset + abildescOffset);
                    setParentRectangle(get());
                    centerInParentX(true);
                    textColor(DESCRIPTION_COLOR);
                    this.setFontSize(descriptionFontSize);
                }
            });
        }
        catch (ClassCastException e) {}
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);

        //set height and width to size of content
        if (b) {
            width = findLongest();
            height = calcHeight();
            get().setSize(width, height);
        }
    }
    
    public void start() {
        lines = new ArrayList<UIText>();
        populateLines();
        image = Images.getImage(item.imageName());
        imagebox.setLocation(get().getLocation());

        get().setSize(width, height);
        this.get().setLocation(parentObject.getLocation());
        setBackgroundColor(getParent().getUITextColor());
        textColor(getParent().getUIBackgroundColor());
        setBorderColor(getParent().getUIBackgroundColor());
        centerInParentX(true);
        setBackgroundPadding(15);
        setBorderThickness(20);
        autoAdjustBackground(false);
        hasText(true);
        textColor(getBackgroundColor());
        setFontSize(0);

        setTag("tooltip");
        getParent().applyGeneralStyle(this);
    }

    public void update() {
        super.update();
        textCenterHeight = 0;
        adjustLocation();
        for (int i = 0; i < lines.size(); i++) {
            updateDescription(lines.get(i), i);
            lines.get(i).update();
        }

        //position image below effect text
        if (visible()) {
            imagebox.setLocation(get().x + (get().width / 2) - (imagebox.width / 2), lines.get(lines.size()-1).get().y + imageOffset);
        }
    }

    public int findLongest() {
        int longest = getTextWidth();

        for (UIText t : lines) {
            if (t.getTextWidth() > longest) {longest = t.getTextWidth();}
        }

        return longest + widthPadding;
    }

    public int calcHeight() {
        int height = 0;
        for (UIText text : lines) {
            height += text.getFullHeight();
        }
        height += (imagebox.height + imageOffset);
        return height;
    }

    public void updateDescription(UIText t, int i) {
        t.setVisible(this.visible());
        t.setParentRectangle(get());

        if (t.tag().equals("title")) {
            t.get().y = get().y + titleOffset;
        }
        else if (t.tag().equals("effect")) {
            t.get().y = get().y + descOffset + effectOffset + (i * lineSpacing);
        }
        else if (t.tag().equals("abilitydescription")) {
            t.get().y = get().y + descOffset + effectOffset + (i * lineSpacing) + abildescOffset;
        }
        else {
            t.get().y = get().y + descOffset + (i * lineSpacing);
        }
    }

    public void adjustLocation() {
        get().setLocation(parentObject.x - this.get().width -75, parentObject.y);
        //if box goes off screen on the right
        
        if (get().getMaxX() > GlobalData.getScreenDim().width) {
            get().x = GlobalData.getScreenDim().width - get().width;
        }
        //if box goes off screen at the top
        if (get().getMinY() < 0) {
            get().y = 0;
        }
        //if box goes off screen at the bottom
        if (get().getMaxY() > GlobalData.getScreenDim().height) {
            get().x = GlobalData.getScreenDim().height - get().height;
        }
        //if box goes off screen on the left
    }

    public void paint(Graphics g) {
        super.paint(g);
        for (UIText t : lines) {
            t.paint(g);
        }
        if (visible()) {
            g.drawImage(image, imagebox.x, imagebox.y, imagebox.width, imagebox.height, null);
        }
    }

}
