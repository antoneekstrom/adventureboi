package UI;

public class UIButton extends UIObject {

    public UIButton(String parentname, String text, boolean centered) {
        super();
        setParentName(parentname);
        setBackgroundColor(getParent().getUIBackgroundColor());
        setText(text);
        autoAdjustBackground(true);
        hoverColorChange(getParent().BACKGROUND_HOVER_COLOR);
        setHoverTextColor(getParent().TEXT_HOVER_COLOR);
        if (centered) {
            centerInParentX(true);
            centerTextY(true);
            centerTextX(true);
        }
    }

    @Override
    public void leftMouseReleased() {
        super.leftMouseReleased();
        doTask();
    }
}
