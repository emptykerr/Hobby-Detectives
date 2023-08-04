import java.util.ArrayList;
import java.util.List;

public class Estate {
    String name;
    Weapon weapon;
    int x;
    int y;
    int width;
    int height;
    List<Character> characterList;
    /**
     * Estate can have up to one weapon and up to four characters, characters will be stored
     * within a list. Also has dimensions and coordinates
     * -Mathias
     * */

    public Estate(String name, int x, int y, int w, int h){
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public void addWeapon(Weapon w){
        weapon = w;
    }

    public Weapon removeWeapon(){

        return weapon;
    }
    public String getName(){
        return name;
    }

    public Estate squarePartOfEstate(Square s){
        if (s.getX() >= x && s.getX() <  x + width && s.getY() >= y && s.getY() < y + height){
            return this;
        }
        return null;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }
}
