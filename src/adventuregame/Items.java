package adventuregame;

import java.util.HashMap;

public class Items {
	
	private static HashMap<String, String> descriptions = new HashMap<String, String>() {{
		put("donut", "Epicly yummy.");
		put("shroom", "Very nice mushroom.");
		put("angryshroom", "Very angery boi.");
		put("tallmush", "Tallest boi in the entire 7th dimension.");
		put("explosion", "How did this get here?");
	}};
	
	private static HashMap<String, String> effects = new HashMap<String, String>() {{
		put("donut", "+20 max hp");
		put("shroom", "+20 max energy");
		put("angryshroom", "+5 base damage (does not work)");
		put("tallmush", "+0.2 energy regen");
		put("explosion", "+1 explosion");
	}};

	private static HashMap<String, String> tags = new HashMap<String, String>() {{
		put("donut", "statup");
		put("shroom", "statup");
		put("angryshroom", "statup");
		put("tallmush", "statup");
	}};
	
	public static String getDescription(String item) {
		return descriptions.get(item);
	}

	public static String getSortingTag(String item) {
		return tags.get(item);
	}
	
	public static String getEffect(String item) {
		return effects.get(item);
	}
	
	public static void test() {
	}
}
