package adventuregame;

import java.awt.Point;

public class GlobalData {

	private static Point relativeMouse = new Point();
	
	public static void setRelativeMouse(Point p) {
		relativeMouse = p;
	}
	
	public static Point getRelativeMouse() {
		return relativeMouse;
	}
	
}
