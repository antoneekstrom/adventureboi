package objects;

public interface EnemyMold {

    public void collide(NewObject collision);

    public void die();

    public void contactDamage(NewObject col);

    public void drop();

    public void startAI();

    public void startAnimator();

    public void startCore();

}