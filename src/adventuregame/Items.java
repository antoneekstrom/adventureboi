package adventuregame;

import java.util.HashMap;

public class Items {
	
	private static HashMap<String, String> descriptions = new HashMap<String, String>() {{
		put("donut", "Epicly yummy.");
		put("shroom", "Very nice mushroom.");
	}};
	
	private static HashMap<String, String> effects = new HashMap<String, String>() {{
		put("donut", "+20 max hp");
		put("shroom", "+20 max energy");
	}};
	
	public static String getDescription(String item) {
		return descriptions.get(item);
	}
	
	public static String getEffect(String item) {
		return effects.get(item);
	}
}
