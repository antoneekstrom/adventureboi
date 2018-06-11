package UI;

import java.awt.Color;

public class SettingsUI extends GUI {

    public SettingsUI() {
        super("Settings");
    }

    public void start() {
        //back
        addBackButton();

        //title
        addObject(new UIText(getName(), "Settings", true) {{
            setFontSize(80);
            get().y = 200;
            textColor(Color.orange);
        }});

        //keybindings button
        addObject(new UIButton(getName(), "Keybindings", true) {{
            this.get().y = 600;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
            get().width = 500;
            setBackgroundPadding(40);
        }});
    }
}
