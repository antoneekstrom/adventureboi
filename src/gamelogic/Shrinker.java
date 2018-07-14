package gamelogic;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import objects.NewObject;

public class Shrinker implements ActionListener {

    NewObject object;

    int shrinkSpeed;
    float shrinkAmount;
    boolean expand;
    Dimension finalSize;
    Dimension originalSize;
    boolean negativeShrink = false;

    public Shrinker(NewObject object, int shrinkSpeed, float shrinkAmount) {
        this.object = object;
        this.shrinkSpeed = shrinkSpeed;
        this.shrinkAmount = shrinkAmount;
        expand = false;
        setGoalSize();
    }

    public Shrinker(NewObject object, int shrinkSpeed, Dimension originalSize) {
        this.object = object;
        this.shrinkSpeed = shrinkSpeed;
        this.finalSize = originalSize;
        expand = true;
    }

    public void expand() {
        int width = object.get().width;
        int height = object.get().height;

        boolean w = false, h = false;

        if (finalSize.width > object.get().width) {
            if (width + shrinkSpeed <= finalSize.width) {

                object.get().width += shrinkSpeed;
                object.get().x -= shrinkSpeed / 2;
            }
            else {
                w = true;
            }
            if (height + shrinkSpeed <= finalSize.height) {

                object.get().height += shrinkSpeed;
                object.get().y -= shrinkSpeed / 2;
            }
            else {
                h = true;
            }
        }
        else {
            if (width - shrinkSpeed >= finalSize.width) {

                object.get().width -= shrinkSpeed;
                object.get().x += shrinkSpeed / 2;
            }
            else {
                w = true;
            }
            if (height - shrinkSpeed >= finalSize.height) {

                object.get().height -= shrinkSpeed;
                object.get().y += shrinkSpeed / 2;
            }
            else {
                h = true;
            }
        }

        if (w && h) {
            object.stopShrinker();
        }
    }

    private void setGoalSize() {
        int width = object.get().width;
        int height = object.get().height;

        int gw = width - (int)(width * shrinkAmount), gh = height - (int)(height * shrinkAmount);

        if (gw < 0 || gh < 0) {
            negativeShrink = true;
        }

        originalSize = new Dimension(width, height);
        finalSize = new Dimension(gw, gh);

        if (negativeShrink) {
            finalSize.width = -finalSize.width + originalSize.width;
            finalSize.height = -finalSize.height + originalSize.height;
        }
    }

    public void shrink() {
        int width = object.get().width;
        int height = object.get().height;

        boolean w = false, h = false;

        if (!negativeShrink) {
            if (width - shrinkSpeed >= finalSize.width && width - shrinkSpeed >= 0) {

                object.get().width -= shrinkSpeed;
                object.get().x += shrinkSpeed / 2;
            }
            else {
                w = true;
            }
            if (height - shrinkSpeed >= finalSize.height && height - shrinkSpeed >= 0) {

                object.get().height -= shrinkSpeed;
                object.get().y += shrinkSpeed / 2;
            }
            else {
                h = true;
            }
        }
        else {
            if (width + shrinkSpeed <= finalSize.width) {

                object.get().width += shrinkSpeed;
                object.get().x -= shrinkSpeed / 2;
            }
            else {
                w = true;
            }
            if (height + shrinkSpeed <= finalSize.height) {

                object.get().height += shrinkSpeed;
                object.get().y -= shrinkSpeed / 2;
            }
            else {
                h = true;
            }
        }

        if (w && h) {object.beenShrunked = true; object.stopShrinker(); negativeShrink = false;}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
        if (expand) {expand();} else {shrink();}
	}
    
}