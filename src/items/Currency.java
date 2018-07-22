package items;

public interface Currency {
    /** Set the value of this currency. Use preset static values from Currency interface. */
    void setValue(double value);
    double getValue();
    void setSize();

    int SMALL = 1;
    int SMALL2 = 2;
    int MEDIUM1 = 5;
    int MEDIUM2 = 10;
    int LARGE = 25;
    int HUGE = 50;
}