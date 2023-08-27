package main;

/**
 * Reprersents a weapon card in the game
 *
 * @param name
 */
public record WeaponCard(String name) implements Card {
    @Override
    public String getCardName() {
        return name;
    }
}
