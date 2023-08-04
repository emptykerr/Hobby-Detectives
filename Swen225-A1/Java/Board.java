public class Board {
    private static int length = 24;
    private static Square[][] board = new Square[length][length];

    public static void addSquare(Square s, int x, int y){
        board[y][x] = s;
    }

    public static Square getSquare(int x, int y){
        return board[y][x];
    }

    public static int getLength(){
        return length;
    }

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
                System.out.print(HobbyDetectives.RESET + " ");
            }
            System.out.print("\n");
        }
    }
}
