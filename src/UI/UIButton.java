package UI;

public class UIButton extends UIObject {

    public UIButton(String parentname, String text, boolean centered) {
        super();
        setParentName(parentname);
        setBackgroundColor(getParent().getUIBackgroundColor());
        setText(text);
        autoAdjustBackground(true);
        hoverColorChange(getBackgroundColor().brighter());
        if (centered) {
            centerInParentX(true);
            centerTextY(true);
            centerTextX(true);
        }
    }
}
