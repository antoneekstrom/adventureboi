package graphic;

import java.awt.Graphics;

public interface TextGraphic {

    public void update();

    public void setWidth(int i);

    public int getTotalHeight();

    public void paint(Graphics g);

}