package graphic;

import java.awt.Graphics2D;
import java.awt.Point;

public class Paragraph extends Graphic {
    
    String[] text;
    Point location;
    int lineSpacing = 10;

    public Paragraph(String[] text) {
        setText(text);
        setLocation(new Point(0,0));
    }

    public Paragraph(String[] text, int lineSpacing) {
        setText(text);
        setLineSpacing(lineSpacing);
        setLocation(new Point(0,0));
    }

    public void setLineSpacing(int lineSpacing) { this.lineSpacing = lineSpacing; }
    public void setText(String[] text) { this.text = text; }
    public void setLocation(Point location) { this.location = location; }

    @Override
    public void paintComponent(Graphics2D g) {

        int yOffset = g.getFontMetrics().getHeight() + lineSpacing, yPointer = 0;

        for (String t : text) {
            g.drawString(t, location.x, location.y + yPointer);
            yPointer += yOffset;
        }
    }
    
    public void paint(Point p, Graphics2D g) {
        setLocation(p);
        paintComponent(g);
    }

}