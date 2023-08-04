public class Weapon {
    String name;
    Estate estate;
    /**
     * Weapon has only a name
     * - Mathias
     * */
    public Weapon(String name){
        this.name = name;
    }

    public void addEstate(Estate e){
        estate = e;
    }
    public String getName(){
        return name;
    }
}
