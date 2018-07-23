package objects;

public interface EnemyMold {

    public void collide(NewObject collision);

    public void die();

    public void playerContact(NewPlayer col);

    public void drop();

    public void startAI();

    public void startAnimator();

    public void startCore();

}