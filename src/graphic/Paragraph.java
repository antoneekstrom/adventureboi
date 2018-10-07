package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

public class Paragraph extends Graphic {
    
    String[] text;
    Point location;
    int lineSpacing = 10;
    Color fontColor = Color.white;
    Font font = new Font("Comic Sans MS", Font.PLAIN, 45);

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
    public void setFontColor(Color fontColor) { this.fontColor = fontColor; }
    public void setFont(Font font) { this.font = font; }
    public Font getFont() { return font; }

    @Override
    public void paintComponent(Graphics2D g) {
        g.setColor(fontColor);
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