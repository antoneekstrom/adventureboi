package UI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImage extends UIObject {

    private BufferedImage image;
    private int imageWidth, imageHeight;
    private boolean fitToImage = false;
    private boolean centerImage = true;
    private int imageOffsetX, imageOffsetY;

    public UIImage(String parentname) {
        super();
        setParentName(parentname);
        start();
    }

    public void setImage(BufferedImage i) {
        image = i;
    }

    public void setImageSize(int w, int h) {
        imageWidth = w;
        imageHeight = h;
    }

    public void start() {
        setBackgroundColor(getParent().getUIBackgroundColor());
        setImageSize(100, 100);
        hasBorder(true);
        setBorderThickness(getParent().getBorderThickness());
        setBackgroundPadding(40);
    }

    public void centerImage(boolean b) {
        centerImage = b;
    }

    private void centerImage() {
        imageOffsetX = (get().width / 2) - imageWidth / 2;
        imageOffsetY = (get().height / 2) - imageHeight / 2;
    }

    /** Adjust element size so that it fits the image. */
    public void fitToImage(boolean b) {
        fitToImage = b;
    }

    private void fitToImage() {

    }

    public void update() {
        super.update();
        if (fitToImage) {fitToImage();}
        if (centerImage) {centerImage();}
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (image != null) {
            g.drawImage(image, get().x + imageOffsetX, get().y + imageOffsetY, imageWidth, imageHeight, null);
        }
    }

}
