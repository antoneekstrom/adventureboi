package adventuregame;

import java.util.HashMap;

public class Items {
	
	private static HashMap<String, String[]> descriptions = new HashMap<String, String[]>() {
		private static final long serialVersionUID = 1L;
	{
		put("donut", new String[] {"Epicly yummy."});
		put("shroom", new String[] {"Very nice mushroom."});
		put("angryshroom", new String[] {"Very angery boi.","What is this? Another", "line?!"});
		put("tallmush", new String[] {"Tallest boi in the entire 7th dimension."});
		put("explosion", new String[] {"How did this get here?"});
		put("lowercaseb", new String[] {"Epic lowercase b."});
		put("uppercaseb", new String[] {"Incredibly epic uppercase b."});
	}};
	
	private static HashMap<String, String> effects = new HashMap<String, String>() {{
		put("donut", "+20 max hp");
		put("shroom", "+20 max energy");
		put("angryshroom", "+5 damage");
		put("tallmush", "+0.2 energy regen");
		put("explosion", "+1 explosion");
		put("lowercaseb", "+1 b");
		put("uppercaseb", "+1 B");
	}};
	
	private static HashMap<String, String> tags = new HashMap<String, String>() {{
		put("donut", "statup");
		put("shroom", "statup");
		put("angryshroom", "statup");
		put("tallmush", "statup");
		put("explosion", "misc");
		put("lowercaseb", "statup");
		put("uppercaseb", "statup");
	}};

	public static String[] getDescription(String item) {
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
