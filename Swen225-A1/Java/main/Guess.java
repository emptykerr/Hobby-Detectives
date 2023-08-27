package main;

import java.util.*;

/**
 * Represents a guess made up of character, weapon and estate
 */
public class Guess {
    CharacterCard character;
    WeaponCard weapon;
    EstateCard estate;
    List<Card> guessCards;

    /**
     * Guess consists of one character, one weapon and one estate
     */
    public Guess(CharacterCard character, WeaponCard weapon, EstateCard estate) {
        guessCards = new ArrayList<>();
        this.character = character;
        this.weapon = weapon;
        this.estate = estate;
        guessCards.add(character);
        guessCards.add(weapon);
        guessCards.add(estate);
    }

    /**
     * Returns the character from a given guess
     *
     * @return
     */
    public CharacterCard getCharacter() {
        return character;
    }

    /**
     * Returns the weapon from a given guess
     *
     * @return
     */
    public WeaponCard getWeapon() {
        return weapon;
    }

    /**
     * Returns the character from a given guess
     *
     * @return
     */
    public EstateCard getEstate() {
        return estate;
    }

    /**
     * Returns the list of cards from a given guess
     *
     * @return
     */
    public List<Card> getCards() {
        return guessCards;
    }

    /**
     * equals method to see if players solve attempt is correct
     *
     * @param other is solution
     */
    public boolean equals(Guess other) {
        if (this == other) {
            return true;
        } else if (character == other.getCharacter() && weapon == other.getWeapon() && estate == other.getEstate()) {
            return true;
        } else {
            return false;
        }
    }
}
