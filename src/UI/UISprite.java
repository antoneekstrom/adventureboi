package UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import objects.NewObject;

public class UISprite extends UIObject {

    private BufferedImage image;
    private NewObject object;

    public UISprite(String pname, NewObject o) {
        super();
        setParentName(pname);
        if (o != null) {
            image = o.getImage();
            object = o;
        }
    }

    public void resize(float percentage) {
        if (object != null) {
            Dimension d = object.get().getSize();
            get().setSize( (int) (d.width * percentage), (int) (d.height * percentage) );
        }
    }

    public void fixSize() {
        if (object != null) {
            get().setSize(object.get().getSize());
        }
    }

    public void setSprite(NewObject o) {
        image = o.getImage();
        object = o;
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(image, get().x, get().y, get().width, get().height, null);
    }

}