package adventuregame;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import worlds.World;

public class Object {

	//objects
	private Main frame;
	private World world;
	private Rectangle objectRect;
	private Color COLOR;
	
	//space
	private int cx, cy;
	private int x, y;
	private int width, height;
	private int GRAVITY = 10;
	private boolean hasGravity = false;
	private boolean hasCollision = false;
	
	//velocity
	private int frameCounter = 0;
	private int yVelocity = 1;
	private int firstY;
	public int VELOCITYREFRESH = 4;
	
	public Object(Main f, World w) {
		frame = f;
		setWorld(w);
		objectRect = new Rectangle(getX(), getY());
	}
	
	public void setLocation(int nx, int ny) {
		setX(nx);
		setY(ny);
		objectRect.setLocation(nx, ny);
	}
	
	public void setLocationCenter() {
		x = 600 - width;
		y = 400 - height;
	}
	
	public void update() {
		setLocation(x, y);
		updateObjectRect();
		gravity();
		//checkWorldBounds();
	}
	
	public void checkWorldBounds() {
		if (!world.getBounds().contains(objectRect)) {
			hasGravity = false;
			
			//setLocation(getX(), (int) world.getBounds().getMaxY() - getHeight());
		}
		/*if (world.getBounds().contains(objectRect)) {
			hasGravity = true;
		}*/
	}

	public void checkCollision(Object o) {
		if (objectRect.intersects(o.getObjectRect())) {
			o.hasGravity = false;
		} else if (!objectRect.intersects(o.getObjectRect())) {
			o.hasGravity = true;
		}
	}
	
	public void gravity() {
		if (hasGravity == true) {
			y = y + (1 * GRAVITY);
		}
	}
	
	public void calculateYVelocity() {
		if (frameCounter == 0) {
			firstY = y;
			
		}
		if (frameCounter < VELOCITYREFRESH) {
			frameCounter++;
			
		}
		if (frameCounter == VELOCITYREFRESH) {
			frameCounter = 0;
			yVelocity = (firstY - y) / VELOCITYREFRESH;
		}
	}
	
	public int getFrameCounter() {
		return frameCounter;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}
	
	public void updateObjectRect() {
		objectRect.setLocation(getX(), getY());
	}
	
	public Point getLocation() {
		Point p = new Point(getX(), getY());
		return p;
	}
	
	public int getX() {
		return x;
	}

	public Main getFrame() {
		return this.frame;
	}
	
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public void setWidth(int w) {
		this.width = w;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public void setHeight(int h) {
		this.height = h;
	}
	
	public void setSize(int w, int h) {
		setWidth(w);
		setHeight(h);
		objectRect.setSize(w, h);
	}
	
	public int getGRAVITY() {
		return GRAVITY;
	}
	
	public void setGRAVITY(int g) {
		GRAVITY = g;
	}
	
	public boolean hasGravity() {
		return hasGravity;
	}
	
	public void setGravity(boolean g) {
		hasGravity = g;
	}

	public Rectangle getWorldBounds() {
		return getWorld().getBounds();
	}
	
	public Rectangle getObjectRect() {
		return objectRect;
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Color getCOLOR() {
		return COLOR;
	}

	public void setCOLOR(Color color) {
		this.COLOR = color;
	}

	public boolean hasCollision() {
		return hasCollision;
	}

	public void setCollision(boolean hasCollision) {
		this.hasCollision = hasCollision;
	}

	public int getCx() {
		return cx;
	}

	public void setCx(int cx) {
		this.cx = cx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

}





