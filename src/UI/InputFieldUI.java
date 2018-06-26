package UI;

import data.Players;

public class InputFieldUI extends GUI {

    private String title;

    public InputFieldUI(String name, String title) {
        super(name);
        this.title = title;
        incognito(true);
    }

    public void start() {
        setGuidelineSpacing(150);
        setGuidelineY1(150);

        addTitle(title);
        getObjectByText(title).get().y = getGuidelineY1();

        //texfield
        UIButton textfield = new UIButton(getName(), "Name", true) {
            @Override
            public void useInput() {
                selectInput(getInput());
            }
            {
                takeInput(true);
                get().width = 500;
                autoAdjustBackgroundWidth(false);
                setInputPrefix("Name");
                get().y = getGuidelineY1() + 200;
            }
        };
        applyGeneralStyle(textfield);
        addObject(textfield);
    }

    public void selectInput(String input) {
        switch (getName()) {
            case "InputField_PlayerName":
                newPlayer(input);
                break;
            
            case "InputField_RemovePlayer":
                removePlayer(input);
                break;
        }
    }

    public void newPlayer(String name) {
        PlayerSelectUI.newPlayer(name);
        UIManager.lockCurrentGUI(false);
        UIManager.enableGUI("PlayerSelect");
    }

    public void removePlayer(String name) {
        UIAlert alert = new UIAlert("Are you sure you want to delete " + name + "?", getName()) {
            @Override
            public void onClose() {
                Players.removePlayer(name);
                UIManager.lockCurrentGUI(false);
                UIManager.enableLatestGUI();
            }
            {
                setButtonText("haha nice");
            }
        };
        addObject(alert);
    }

}
