/**
 * Square consists of up to one character, up to four walls (bools) and up to one estate
 * - Mathias
 * */
public class Square{
    private Character character;
    private int x;
    private int y;
    private Estate estate;
    private boolean blocked = false;

    public Square(int x, int y){
        this.x = x;
        this.y = y;
        estate = null;
    }

    public void setEstate(Estate e) {
        estate = e;
    }

    public void setBlocked(Boolean b){
        blocked = b;
    }


    public boolean isBlocked(){ return blocked; }

    public void addCharacter(Character c){
        character = c;
    }

    public Character removeCharacter(){
        Character toRemove = character;
        character = null;
        return toRemove;
    }

    public Character getCharacter(){ return character; }
    public int getX(){ return x; }
    public int getY(){ return y; }

    public void draw() {
        if (character != null){
            System.out.print(character.getColour() + character.getName().toString().charAt(0));
        } else {
            if (blocked){
                System.out.print("X");
            } else {
                if (estate != null) {
                    System.out.print("O");
                } else {
                    System.out.print("_");
                }
            }
        }
    }

    public Estate getEstate() {
        return estate;
    }
}
