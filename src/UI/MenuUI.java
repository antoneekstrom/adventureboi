package UI;

import java.awt.Color;

public class MenuUI extends GUI {

    public MenuUI() {
        super("Menu");
    }

    public void start() {
        setVisible(true);
        showOutline(true);
        //title
        addObject(new UIText(getName(), "Adventureboi", true) {{
            this.textColor(Color.orange);
            this.get().y = 200;
            this.setFontSize(80);
        }});

        //custom levels button
        addObject(new UIButton(getName(), "Custom Levels", true) {{
            this.get().y = 450;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
            get().width = 500;
            setBackgroundPadding(40);
        }});
        
        //quit button
        addObject(new UIButton(getName(), "Quit Game", true) {{
            this.get().y = 750;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
            get().width = 500;
            setBackgroundPadding(40);
        }});

        //settings button
        addObject(new UIButton(getName(), "Settings", true) {{
            this.get().y = 600;
            setFontSize(40);
            autoAdjustBackgroundWidth(false);
            get().width = 500;
            setBackgroundPadding(40);
        }});
    }
}
