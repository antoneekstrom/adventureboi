package UI;

import java.awt.Graphics;

public class UIAlert extends UIObject {

    private String text;
    private UIButton yesButton, noButton;

    private String yesButtonText = "OK";
    private String noButtonText = "extremely no tbh";
    private int buttonOffset = 45;

    public void setButtonText(String text) {yesButtonText = text;}

    public UIAlert(String message, String parentname) {
        super();
        setParentName(parentname);
        text = message;
        start();
    }

    public void start() {
        getParent().applyGeneralStyle(this);
        centerInParentX(true);
        centerTextX(true);
        centerTextY(true);
        setBackgroundColor(getParent().getUIBackgroundColor());
        setText(text);
        setTag("alert");
        get().y = getParent().yCenter(get().height) - 100;
        autoAdjustBackground(true);

        //yes
        yesButton = new UIButton(getParentName(), yesButtonText, false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                onClose();
                getParent().removeAlerts();
            }
            {
                this.get().setLocation(getParent().xCenter(this.get().width) + buttonOffset, 550);
                this.setTag("alert");
            }
        };

        //no
        noButton = new UIButton(getParentName(), noButtonText, false) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                getParent().removeAlerts();
            }
            {
                this.get().setLocation(getParent().xCenter(this.get().width) - buttonOffset - this.get().width, 550);
                this.setTag("alert");
            }
        };

        getParent().applyGeneralStyle(yesButton);
        getParent().addObject(yesButton);
        getParent().applyGeneralStyle(noButton);
        getParent().addObject(noButton);
    }

    public void onClose() {

    }

    public void update() {
        super.update();
        //yes
        yesButton.setText(yesButtonText);
        yesButton.get().setLocation(getParent().xCenter(yesButton.get().width) + buttonOffset + yesButton.get().width / 2, 550);
        //no
        noButton.get().setLocation(getParent().xCenter(noButton.get().width) - buttonOffset - noButton.get().width / 2, 550);
        noButton.setText(noButtonText);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

}
