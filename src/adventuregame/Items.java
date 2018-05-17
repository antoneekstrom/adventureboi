package adventuregame;

import java.util.HashMap;

public class Items {
	
	private static HashMap<String, String> descriptions = new HashMap<String, String>() {{
		put("donut", "Epicly yummy. +20 max hp");
		put("shroom", "Very nice mushroom. +20 max energy");
	}};
	
	public static String getDescription(String item) {
		return descriptions.get(item);
	}
}
