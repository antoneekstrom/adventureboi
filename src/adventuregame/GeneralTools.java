package adventuregame;

import java.awt.Toolkit;
import java.awt.Dimension;

public class GeneralTools {

    public static Dimension getScreenDim() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

}