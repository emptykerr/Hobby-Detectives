public class Character {
    private HobbyDetectives.PlayerName name;
    private Square square;

    private String colour;

    public Character(Square square, HobbyDetectives.PlayerName name, String colour){
        this.name = name;
        this.square = square;
        this.colour = colour;
        square.addCharacter(this);
    }

    /**
     * Move character to suggested square
     * - Alex
     */
    public boolean move(Square squareToMove){
        if (squareToMove.getCharacter() != null || squareToMove.isBlocked()){
            return false;
        }

        squareToMove.addCharacter(this);
        square.removeCharacter();
        square = squareToMove;
        return true;
    }

    /**
     * Steps the character (up, down, left or right) depending on string parameter.
     * - Alex
     */
    public Square step(String direction){
        Square newSquare = switch (direction) {
            case "U" -> {
                if (square.getY() != 0) {
                    yield Board.getSquare(square.getX(), square.getY() - 1);
                }
                yield null;
            }
            case "R" -> {
                if (square.getX() != Board.getLength() - 1) {
                    yield Board.getSquare(square.getX() + 1, square.getY());
                }
                yield null;
            }
            case "D" -> {
                if (square.getY() != Board.getLength() - 1) {
                    yield Board.getSquare(square.getX(), square.getY() + 1);
                }
                yield null;
            }
            case "L" -> {
                if (square.getX() != 0) {
                    yield Board.getSquare(square.getX() - 1, square.getY());
                }
                yield null;
            }
            default -> {
                yield null;
            }
        };

        if (newSquare != null) {
            if (!this.move(newSquare)) {
                return null;
            }
        }
        return newSquare;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public String getColour() { return colour;}

    public HobbyDetectives.PlayerName getName() { return name; }
}
