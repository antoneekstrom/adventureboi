package adventuregame;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;

public class GlobalData {

	private static Point relativeMouse = new Point();
	
	public static void setRelativeMouse(Point p) {
		relativeMouse = p;
	}
	
	public static Point getRelativeMouse() {
		return relativeMouse;
	}

	public static Font getStandardFont() {
		return new Font("Comic Sans MS", 20 ,40);
	}
	
	public static Point getMouse() {
		return MouseInfo.getPointerInfo().getLocation();
	}

    public static Dimension getScreenDim() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
	
}
