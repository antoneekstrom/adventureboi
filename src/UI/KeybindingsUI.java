package UI;

public class KeybindingsUI extends GUI {

    public KeybindingsUI() {
        super("Keybindings");
    }

    public void start() {
        //nav
        incognito(true);

        //back
        addBackButton();

        //title
        addTitle("Keybindings");

        //addbuttton
        addBackButton();
    }

}
