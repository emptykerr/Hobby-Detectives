package main;

/**
 * Represnts a weapon in the game. Weapons can be contained in an estate and used in guesses
 */
public class Weapon {
    String name;
    Estate estate;

    public Weapon(String name) {
        this.name = name;
        this.estate = null;
    }

    /**
     * Set the estate for this weapon;
     */
    public void setEstate(Estate e) {
        estate = e;
    }

    /**
     * Get the estate for this weapon
     * @return
     */
    public Estate getEstate() {
        return estate;
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
