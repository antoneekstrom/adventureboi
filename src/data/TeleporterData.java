package data;

import java.awt.Point;

import objects.GameObject;
import objects.Teleporter;

public class TeleporterData extends ObjectData {

    private static final long serialVersionUID = 1L;

    Point destination;

    public TeleporterData(Teleporter object) {
        super(object);
        destination = object.getDestination();
    }

    @Override
    public GameObject clone(GameObject object) {
        Teleporter t = (Teleporter) super.clone(object);
        t.setDestination(destination);
        return t;
    }

}