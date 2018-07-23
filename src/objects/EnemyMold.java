package objects;

public interface EnemyMold {

    public void collide(GameObject collision);

    public void die();

    public void playerContact(Player col);

    public void drop();

    public void startAI();

    public void startAnimator();

    public void startCore();

}