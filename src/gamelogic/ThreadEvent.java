package gamelogic;

public class ThreadEvent {

    public ThreadEvent(Runnable r) {
        start(r);
    }

    void start(Runnable r) {
        new Thread(r).start();
    }

}