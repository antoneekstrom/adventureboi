package UI;

import java.awt.Graphics;

import graphic.ResponsiveText;
import graphic.TextGraphic;

public class GraphicContainerUI extends UIObject {

    TextGraphic textGraphic;

    public GraphicContainerUI(String parentname, String text) {
        super(parentname);
        textGraphic = new ResponsiveText(text, getFullWidth());
    }

    public GraphicContainerUI(String parentname, TextGraphic rt) {
        setParentName(parentname);
        textGraphic = rt;
    }

    public void setGraphic(TextGraphic t) {
        textGraphic = t;
    }

    public GraphicContainerUI(String parentname) {
        setParentName(parentname);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        if (b) {
            update();
            textGraphic.update();
        }
    }

    @Override
    public void update() {
        super.update();
        textGraphic.setWidth(getFullWidth());
        get().height = textGraphic.getTotalHeight();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (visible() && textGraphic != null) {
            textGraphic.paint(g);
        }
    }

}