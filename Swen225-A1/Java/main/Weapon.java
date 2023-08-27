package main;

/**
 * Represnts a weapon in the game. Weapons can be contained in an estate and used in guesses
 */
public class Weapon {
    String name;
    Estate estate;

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

    /**
     * Returns the name of the weapon
     *
     * @return weapon name
     */
    public String getName() {
        return name;
    }
}
