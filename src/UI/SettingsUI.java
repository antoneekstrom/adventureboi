package UI;

public class SettingsUI extends GUI {

    public SettingsUI() {
        super("Settings");
        setGuidelineSpacing(150);
        setGuidelineY1(200);
    }

    public void start() {
        //back
        addBackButton();

        //title
        addTitle("Settings");
        getObjectByText("Settings").get().y = getGuidelineY1() - 50;

        //keybindings button
        addMenuButton("Keybindings", getGuidelineY1());

        //volume slider
        UISlider volumeSlider = new UISlider(getName(), 100) {{
            centerInParentX(true);
            get().setSize(getObjectByText("Keybindings").get().getSize());
            get().height = 100;
            handle().get().setSize(50, 25);
            setBackgroundColor(getUIBackgroundColor());
            hasBorder(true);
            setBorderThickness(getBorderThickness());
            setBorderColor(getUITextColor());
            get().setLocation(get().x, getGuidelineY1());
        }};
        addObject(volumeSlider);
    }
}
