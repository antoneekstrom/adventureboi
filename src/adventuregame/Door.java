package adventuregame;

import worlds.World;

public class Door extends RectangleObject {

	private Player player;
	private World world;
	@SuppressWarnings("unused")
	private Main frame;
	
	public Door(Main f, World w, Player p) {
		super(f, w);
		player = p;
		world = w;
		frame = f;
	}
	
	public void checkEnter() {
		if (player.getObjectRect().intersects(getObjectRect()) && player.direction == "down") {
			world.next();
		}
	}
	
	public void update() {
		setLocation(getX(), getY());
		updateObjectRect();
		gravity();
		checkEnter();
	}
	
}
