package UI;

public class OptionsUI extends GUI {

    public OptionsUI() {
        super("Options");
        setGuidelineSpacing(150);
        setGuidelineY1(200);
    }

    public void start() {
        addTitle("Options");
        getObjectByText("Options").get().y = getGuidelineY1() - 50;
        addMenuButton("Resume", getGuidelineY1());
        addMenuButton("Save", getGuidelineY1());
        addMenuButton("Settings", getGuidelineY1());
        addMenuButton("Menu", getGuidelineY1());
    }

}
