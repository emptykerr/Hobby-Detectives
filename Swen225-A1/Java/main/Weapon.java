package main;

public class Weapon {
    String name;
    Estate estate;

    /**
     * Weapon has only a name
     * - Mathias
     */
    public Weapon(String name) {
        this.name = name;
    }

    /**
     * Each estate contains one weapon
     *
     * @param e
     */
    public void addEstate(Estate e) {
        estate = e;
    }

    public String getName() {
        return name;
    }
}
