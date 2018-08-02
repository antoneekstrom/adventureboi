package data;

import objects.Enemy;
import objects.GameObject;

public class EnemyData extends ObjectData {

    private static final long serialVersionUID = 1L;

    int level;

	public EnemyData(Enemy object) {
        super(object);
        level = object.level();
    }

    @Override
    public GameObject clone() {
        Enemy enemy = createEnemy();
        enemy.level(level);
        return clone(enemy);
    }

    Enemy createEnemy() {
        try {
            return (Enemy) Class.forName(this.className()).newInstance();
        }
        catch (Exception e) {e.printStackTrace();}
        return new Enemy(0, 0, 0, 0, "0");
    }
}