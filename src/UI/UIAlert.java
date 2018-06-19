package UI;

public class UIAlert extends UIObject {

    private String text;

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

        UIButton b = new UIButton(getParentName(), "OK", true) {
            @Override
            public void leftMouseReleased() {
                super.leftMouseReleased();
                getParent().removeAlerts();
            }
            {
                this.get().y = (int) get().getMaxY() + 100;
            }
        };
        setTag("alert");
        getParent().applyGeneralStyle(b);
        getParent().addObject(b);
    }

}
