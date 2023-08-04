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
            if (blocked && isEstateBorder()){
                System.out.print("X");
            } else if(isEstateBorder() && estate !=null) {
                System.out.print(HobbyDetectives.GREEN_BOLD + "/");
            } else {
                if (estate != null) {
                    if(isEstateTitle()){
                        System.out.print(HobbyDetectives.CYAN_BOLD + estate.getName().charAt(0));
                    } else {
                            System.out.print(" ");

                    }
                } else {
                    System.out.print("_");
                }
            }
        }
    }



    private boolean isEstateTitle(){
        int estateCornerX = estate.getX() + estate.width -2;
        int estateCornerY = estate.getY() + estate.height -2;
        int squareX = this.getX();
        int squareY = this.getY();
        if((squareX == estateCornerX && squareY == estateCornerY) || (squareX == estateCornerX -1 && squareY == estateCornerY)){
            return true;
        }
        return false;
    }

    private boolean isEstateBorder() {
        // Check if the square is on the perimeter of the estate
        if (estate != null) {
            int squareX = this.getX();
            int squareY = this.getY();
            int estateX = estate.getX();
            int estateY = estate.getY();
            int estateWidth = estate.width;
            int estateHeight = estate.height;

            // Check if the square is on the perimeter of the estate
            if ((squareX == estateX || squareX == estateX + estateWidth - 1) ||
                    (squareY == estateY || squareY == estateY + estateHeight - 1)) {
                return true;
            }
        }
        return false;
    }

    public Estate getEstate() {
        return estate;
    }
}
