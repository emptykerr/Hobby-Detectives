public record CharacterCard(String name) implements Card {
    @Override
    public String getCardName() {
        return name();
    }
}
