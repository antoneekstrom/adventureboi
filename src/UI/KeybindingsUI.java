package UI;

import java.awt.Color;

public class KeybindingsUI extends GUI {

    public KeybindingsUI() {
        super("Keybindings");
    }

    public void start() {
        //back
        addBackButton();

        //title
        addObject(new UIText(getName(), "Keybindings", true) {{
            setFontSize(80);
            get().y = 200;
            textColor(Color.orange);
        }});
    }

}
