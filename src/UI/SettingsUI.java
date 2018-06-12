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
        addTitle("Settings");

        //keybindings button
        addObject(new UIButton(getName(), "Keybindings", true) {{
            this.get().y = 450;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
            get().width = 500;
            setBackgroundPadding(40);
        }});
    }
}
