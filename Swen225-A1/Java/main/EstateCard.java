package main;

/**
 * Reprersents an estate card in the game
 *
 * @param name
 */
public record EstateCard(String name) implements Card {
    @Override
    public String getCardName() {
        return name();
    }
}
