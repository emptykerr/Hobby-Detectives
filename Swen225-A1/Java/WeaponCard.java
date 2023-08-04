public record WeaponCard (String name) implements Card{
    @Override
    public String getCardName() { return name; }
}
