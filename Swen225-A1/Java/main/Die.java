package main;

public class Die {
    /**
     * @roll to get a value from 1 to 6 meaning how many squares the character can move
     * - Mathias
     */
    public static int roll() {
        return (int) (Math.random() * 6) + 1;

    }
}
