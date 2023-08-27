package main;

/**
 * Reprersents a character card in the game
 *
 * @param name
 */
public record CharacterCard(String name) implements Card {
    @Override
    public String getCardName() { return name(); }
}
