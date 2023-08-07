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

    /**
     * Sets whether the square has a state
     * @param e
     */
    public void setEstate(Estate e) {
        estate = e;
    }

    /**
     * Sets whether the square can be moved onto by a player.
     * @param b
     */
    public void setBlocked(Boolean b){
        blocked = b;
    }

    /**
     * Returns whether the square is accessible by a player
     * @return boolean blocked
     */
    public boolean isBlocked(){ return blocked; }

    /**
     * Adds a character to a square
     * Used for drawing the characters initials on the board
     * @param c
     */
    public void addCharacter(Character c){
        character = c;
    }

    /**
     * Removes a character from a square
     * Used for removing a players intial from a square
     * @return
     */
    public Character removeCharacter(){
        Character toRemove = character;
        character = null;
        return toRemove;
    }

    /**
     * Returns the character on the square
     * @return the character on the current square
     */
    public Character getCharacter(){ return character; }

    /**
     * Returns the column of the square
     * @return x
     */
    public int getX(){ return x; }

    /**
     * Returns the row of the square
     * @return y
     */
    public int getY(){ return y; }

    /**
     * Draws the characters, estates, doors, or blocked areas squares on the screen as a text display
     */
    public void draw() {
        if (character != null){
                System.out.print(character.getColour() + character.getName().toString().charAt(0));
        } else {
            if (blocked && isEstateBorder()){
                System.out.print("X");
            } else if (blocked && estate == null){
                System.out.print("X");

            }else if(isEstateBorder() && estate !=null) {
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


    /**
     * Figures out where the estate title should be drawn within an estate
     * Consists of two initials
     * @return whether the square is a title square or not
     */
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

    /**
     * Figures out if the current square is on the border of an estate
     * for drawing.
     * @return whether the square is a perimeter estate square
     */

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

    /**
     * Returns the estate of the square
     * @return estate
     */

    public Estate getEstate() {
        return estate;
    }
}
