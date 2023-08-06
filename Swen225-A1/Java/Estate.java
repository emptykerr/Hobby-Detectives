import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Estate {
    String name;
    Weapon weapon;
    int x;
    int y;
    int width;
    int height;
    List<Character> characterList;

    Map<Square, String> doors = new HashMap<Square, String>();
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

    public void addDoor(int x, int y, String direction){
        Square door = Board.getSquare(x,y);
        doors.put(door, direction);
        door.setBlocked(false);
    }


    public void addWeapon(Weapon w){
        weapon = w;
    }


    public Weapon getWeapon() {
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
