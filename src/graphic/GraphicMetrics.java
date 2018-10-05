package graphic;

import java.awt.Font;
import java.awt.Graphics2D;

import adventuregame.GameEnvironment;

public class GraphicMetrics {

    Graphics2D g;

    public GraphicMetrics() {
        updateGraphics();
    }

    public int stringWidth(String s) {
        updateGraphics();
        return g.getFontMetrics().stringWidth(s);
    }

    public int fontHeight() {
        return g.getFontMetrics().getHeight();
    }

    public void useFont(Font font) {
        if (font != null) {
            g.setFont(font);
        }
    }

    /** Resupply this {@code GraphicMetrics} object with a newly fetched {@link Graphics2D} object from {@link GameEnvironment}. */
    public void updateGraphics() {
        g = GameEnvironment.getGraphics2D();
    }

}