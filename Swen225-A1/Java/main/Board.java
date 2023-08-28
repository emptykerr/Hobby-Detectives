package main;

/**
 * The board contains the squares that make up the board.
 * It takes care of accessing, altering and drawing all the squares that make up the board
 */
public class Board {
	private static int length = 24;
	private static Square[][] board = new Square[length][length];

	/**
	 * Adds squares to the board
	 *
	 * @param s
	 * @param x
	 * @param y
	 */
	public static void addSquare(Square s, int x, int y) {
		board[y][x] = s;
	}

	/**
	 * Returns a square from the board
	 *
	 * @param x
	 * @param y
	 * @return
	 */

	public static Square getSquare(int x, int y) {
		return board[y][x];
	}

	/**
	 * Gets the size of the board
	 *
	 * @return length
	 */

	public static int getLength() {
		return length;
	}

	/**
	 * Returns the entire board
	 *
	 * @return
	 */

	public static Square[][] getBoard() {
		return board;
	}

//	/**
//	 * The text display in the console, will display each board square, players, and
//	 * estates
//	 */
//	public static void drawToScreen() {
//		for (int y = 0; y < Board.getLength(); y++) {
//			for (int x = 0; x < Board.getLength(); x++) {
//				Board.getSquare(x, y).draw();
//				System.out.print(" ");
//				System.out.print(" ");
//			}
//			System.out.print("\n");
//		}
//	}

	/**
	 * For testing purposes. Creates a testing board from string input
	 */
	public static void createTestBoard(String input){
		System.out.println(input.length());
		int x = 0; 
		int y = 0;
		for (int i = 0; i < input.length(); i++){
			if (x > 23) {x = 0;}
			if (input.charAt(i) == '\n'){
				y++;
			}
			if (input.charAt(i) == '.'){
				addSquare(new Square(x, y), x, y);
				x++;
			}
			if (input.charAt(i) == 'X'){
				Square s = new Square(x, y);
				s.setBlocked(true);
				addSquare(s, x, y);
				x++;
			}
		}
	}

	public static void createTestBoard(String input, Estate e){
		int x = 0; 
		int y = 0;
		for (int i = 0; i < input.length(); i++){
			if (x > 23) {x = 0;}
			if (input.charAt(i) == '\n'){
				y++;
			}
			if (input.charAt(i) == '.'){
				addSquare(new Square(x, y), x, y);
				x++;
			}
			if (input.charAt(i) == 'X'){
				Square s = new Square(x, y);
				s.setBlocked(true);
				addSquare(s, x, y);
				x++;
			}
			if (input.charAt(i) == 'E'){
				Square s = new Square(x, y);
				s.setEstate(e);
				addSquare(s, x, y);
				x++;
			}
			
			
		}
	}
}
