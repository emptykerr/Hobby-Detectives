import java.util.*;

public class Guess {
    CharacterCard character;
    WeaponCard weapon;
    EstateCard estate;
    List<Card> guessCards;
    /**
     * Guess consists of one character, one weapon and one estate
     *
     * */
    public Guess(CharacterCard character, WeaponCard weapon, EstateCard estate){
        guessCards = new ArrayList<>();
        this.character = character;
        this.weapon = weapon;
        this.estate = estate;
        guessCards.add(character);
        guessCards.add(weapon);
        guessCards.add(estate);
    }

    public CharacterCard getCharacter(){ return character; }
    public WeaponCard getWeapon(){ return weapon; }
    public EstateCard getEstate(){ return estate; }
    public List<Card> getCards(){ return guessCards; }

    /**
     * equals method to see if players solve attempt is correct
     * @param other is solution
     * */
    public boolean equals(Guess other){
        if (this == other){
            return true;
        } else if (character == other.getCharacter() && weapon == other.getWeapon() && estate == other.getEstate()) {
            return true;
        }
        else {
            return false;
        }
    }
}
