package graphic;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

public class ResponsiveText extends Graphic {

    private int preferredWidth = 1;
    private String rawText = "";
    private String[] rows = {""};
    private Font font;
    private Point location;

    private GraphicMetrics gm;
    private Paragraph par;

    public ResponsiveText() {
        gm = new GraphicMetrics();
        par = new Paragraph(rows, 10);
    }

    public ResponsiveText(Font font, int width, String text) {
        gm = new GraphicMetrics();
        par = new Paragraph(rows, 0);
        setWidth(width);
        setFont(font);
        setText(text);
    }

    public ResponsiveText(String text, int width) {
        gm = new GraphicMetrics();
        par = new Paragraph(rows, 0);
        setWidth(width);
        setText(text);
    }

    private void adjustRows() {
        gm.updateGraphics();
        gm.useFont(font);

        int fullWidth = gm.stringWidth(rawText);

        int rowCount = Math.round((float)fullWidth / (float)preferredWidth);
        rowCount = zeroCheck(rowCount, 1);

        rows = splitText(rowCount);
        par.setText(rows);
    }

    public int zeroCheck(int i, int replacement) {
        if (i < 1) {
            i = replacement;
        }
        return i;
    }

    String[] splitText(int rows) {
        String[] arr = new String[rows];

        int textLength = rawText.length();
        int rowLength = textLength / zeroCheck(rows, 1);

        int pointer = 0;
        for (int i = 0; i < rows; i++) {

            int lastIndex = pointer + rowLength;
            
            arr[i] = rawText.substring(pointer, ( (lastIndex > textLength) ? (rawText.length()-1) : (lastIndex) ) );
            pointer += rowLength;
        }

        return arr;
    }

    public Rectangle getBounds() {
        gm.updateGraphics();
        gm.useFont(font);
        
        Rectangle r = new Rectangle(getmaxWidth(), getTotalHeight());
        r.setLocation(getLocation());
        return r;
    }

    public void setWidth(int width) { this.preferredWidth = width; }
    public void setFont(Font font) { this.font = font; }
    public void setLocation(Point location) { this.location = location; }
    public Point getLocation() { return location; }
    public int getPreferredWidth() { return preferredWidth; }
    public int getRowCount() { return rows.length; }

    public int getmaxWidth() {
        int i = 0;

        for (String s : rows) {
            int w = gm.stringWidth(s);
            if (w > i) { i = w; }
        }

        return i;
    }

    public int getTotalHeight() {
        return getRowCount() * gm.fontHeight();
    }

    public void useFont(Graphics2D g) {
        if (font != null) {
            g.setFont(font);
        }
    }

    public void setText(String text) {
        this.rawText = text;
        adjustRows();
    }

    @Override
    public void paintComponent(Graphics2D g) {
        useFont(g);
        par.paint(getLocation(), g);
    }

    public void paint(Graphics2D g, Point location) {
        setLocation(location);
        paintComponent(g);

    }

}