package gamelogic;

import objects.GameObject;

public class RandomEvent {

    float chance;
    ActionEvent action;
    GameObject object;
    private String name;

    public RandomEvent(ActionEvent action, GameObject object, float chance, String name) {
        this.action = action;
        this.chance = chance;
        this.object = object;
        this.name = name;
    }

    public void run() {
        if (Math.random() < chance) {
            action.run(object);
        }
    }

    public String getName() {
        return name;
    }
}