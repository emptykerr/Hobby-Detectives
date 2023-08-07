public class Board {
    private static int length = 24;
    private static Square[][] board = new Square[length][length];

    /**
     * Adds squares to the board
     * @param s
     * @param x
     * @param y
     */
    public static void addSquare(Square s, int x, int y){
        board[y][x] = s;
    }

    /**
     * Returns a square from the board
     * @param x
     * @param y
     * @return
     */

    public static Square getSquare(int x, int y){
        return board[y][x];
    }

    /**
     * Gets the size of the board
     * @return length
     */

    public static int getLength(){
        return length;
    }

    /**
     * Returns the entire board
     * @return
     */

    public static Square[][] getBoard(){
        return board;
    }

    /**
     * The text display in the console, will display each board square, players, and estates
     */
    public void drawToScreen(){
        for(int y = 0; y < Board.getLength(); y++) {
            for(int x = 0; x < Board.getLength(); x++){
                Board.getSquare(x, y).draw();
                System.out.print(" ");
                System.out.print(HobbyDetectives.RESET + " ");
            }
            System.out.print("\n");
        }
    }
}
