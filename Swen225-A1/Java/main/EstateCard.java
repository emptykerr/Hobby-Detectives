package main;

public record EstateCard(String name) implements Card {

    @Override
    public String getCardName() {
        return name();
    }

}
