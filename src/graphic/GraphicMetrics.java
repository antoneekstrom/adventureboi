package graphic;

import java.awt.Font;
import java.awt.Graphics2D;

import adventuregame.GameEnvironment;

public class GraphicMetrics {

    private Graphics2D g;
    Font font;

    public GraphicMetrics() {
        updateGraphics();
        useFont(font);
    }

    public int stringWidth(String s) {
        updateGraphics();
        return g.getFontMetrics().stringWidth(s);
    }

    public int fontHeight() {
        return g.getFontMetrics().getHeight();
    }

    public void useFont() {
        g.setFont(font);
    }

    public void useFont(Font font) {
        if (font != null) {
            setFont(font);
            g.setFont(font);
        }
    }

    public void setFont(Font font) { this.font = font; }
    public Graphics2D getGraphics() { return g; }

    /** Resupply this {@code GraphicMetrics} object with a newly fetched {@link Graphics2D} object from {@link GameEnvironment}. */
    public void updateGraphics() {
        g = GameEnvironment.getGraphics2D();
        useFont();
    }

}