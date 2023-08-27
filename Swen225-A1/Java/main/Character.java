package main;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * An instance of Character represnts an idividual character on the HobbyDetectives board
 * A character can move around on the board and each Player has a Character
 */
public class Character {
    private HobbyDetectives.PlayerName name;
    private Square square;
    private Color colour;
    private Set<Square> visitedSquares = new HashSet<>();

    public Character(Square square, HobbyDetectives.PlayerName name, Color colour) {
        this.name = name;
        this.square = square;
        this.colour = colour;

        square.addCharacter(this);
    }

    /**
     * Move character to suggested square.
     *
     * @param squareToMove the square to move to
     * @return True if successful false if not
     */
    public boolean move(Square squareToMove) {
        if (squareToMove.getEstate() != null && !squareToMove.isBlocked()) {
            this.square.removeCharacter();
            setSquare(findEmptySquarePlayer(squareToMove));
            this.square.addCharacter(this);
            return true;
        }
        // instead of setting it to x+1 y+1 check if a character is there, then add x+2/y+2 in a 2x2 square
        //until there is room.

        if (squareToMove.getCharacter() != null || squareToMove.isBlocked() || visitedSquares.contains(squareToMove)) {
            if (visitedSquares.contains(squareToMove)) {
                System.out.println("You have already visited this square");
            }

            return false;
        }

        visitedSquares.add(squareToMove); // Mark the square as visited
        squareToMove.addCharacter(this);
        square.removeCharacter();
        square = squareToMove;
        return true;
    }

    /**
     * Used to teleport the character into a given estate
     * Moves the character into the estate.
     *
     * @param e the estate to teleport character into
     */
    public void moveCharacterIntoEstate(Estate e) {
        //find the first door in the estate - this returns a valid square in the estate
        Square doorSquare = e.doors.entrySet().iterator().next().getKey();
        //use this square to find an empty square in the estate
        move(doorSquare);
    }

    /**
     * Finds an empty square in the top 2x2 squares of an estate
     * to display the characters initial.
     *
     * @param square
     * @return a viable square
     */
    public Square findEmptySquarePlayer(Square square) {
        int x = square.getEstate().getX() + 1; //plus one to avoid the border 'X's
        int y = square.getEstate().getY() + 1; //plus one to avoid the border 'X's
        for (int row = x; row < x + 2; row++) {
            for (int col = y; col < y + 2; col++) {
                //if there is an empty square in the top 2x2 squares of an estate, return that square
                if (Board.getSquare(row, col).getCharacter() == null) {
                    return Board.getSquare(row, col);
                }
            }
        }
        return null;
    }


    /**
     * Steps the character (up, down, left or right) depending on string parameter.
     *
     * @param direction which direction to step
     * @return the square that has been stepped to. Will be null if the step is not allowed
     */
    public Square step(String direction) {
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

    /**
     * Clears the list of visited squares so the player
     * can retrack their steps.
     */
    public void startNewRound() {
        visitedSquares.clear();
    }

    /**
     * Gets the current square the character is on
     *
     * @return square
     */
    public Square getSquare() {
        return square;
    }


    /**
     * Sets the characters current square
     *
     * @param square
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * Gets the colour of the characters text
     *
     * @return the colour of the character
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Returns the enum equivalent name from HobbyDetectives
     *
     * @return
     */
    public HobbyDetectives.PlayerName getName() {
        return name;
    }
}
