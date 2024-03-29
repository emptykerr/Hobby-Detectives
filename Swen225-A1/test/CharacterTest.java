package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.*;

public class CharacterTest {
    @Test
    public void allowedMovement(){
        Board.createTestBoard(
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................");
        Square s = Board.getSquare(12, 12);
        main.Character character = new main.Character(s, HobbyDetectives.PlayerName.Bert, "\033[1;31m");
        character.move(Board.getSquare(6, 6));

        assertEquals(Board.getSquare(6, 6), character.getSquare());
    }

    @Test 
    public void forbiddenMovement(){
        Board.createTestBoard(
                "XXXX....................\n" + //
                "X.......................\n" + //
                "X.......................\n" + //
                "X.......................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................");
        Square s = Board.getSquare(1, 1);
        main.Character character = new main.Character(s, HobbyDetectives.PlayerName.Bert, "\033[1;31m");
        character.move(Board.getSquare(0, 1));
        assertEquals(Board.getSquare(1, 1), character.getSquare());
        character.move(Board.getSquare(24, 1));
        assertEquals(Board.getSquare(1, 1), character.getSquare());
    }

    @Test 
    public void stepWorks(){
        Board.createTestBoard(
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................");
        Square s = Board.getSquare(1, 1);
        main.Character character = new main.Character(s, HobbyDetectives.PlayerName.Bert, "\033[1;31m");
        character.step("U");
        assertEquals(Board.getSquare(1, 0), character.getSquare());
        character.step("D");
        assertEquals(Board.getSquare(1, 1), character.getSquare());
        character.step("R");
        assertEquals(Board.getSquare(2, 1), character.getSquare());
        character.step("R");
        character.step("D");
        character.step("L");
        assertEquals(Board.getSquare(2, 2), character.getSquare());
    }

    @Test 
    public void steppingOntoPreviouslyVistedSquareIsNotAllowed(){
        Board.createTestBoard(
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................");
        Square s = Board.getSquare(1, 1);
        main.Character character = new main.Character(s, HobbyDetectives.PlayerName.Bert, "\033[1;31m");
        character.step("U");
        character.step("D");
        character.step("D");
        character.step("U");
        assertEquals(Board.getSquare(1, 2), character.getSquare());
    }

    @Test 
    public void teleportingCharacterIntoEstateWorks(){
        Estate testEstate = new Estate("Test Estate", 0, 0, 5, 5);
        Board.createTestBoard(
                "EEEEE...................\n" + //
                "EEEEE...................\n" + //
                "EEEEE...................\n" + //
                "EEEEE...................\n" + //
                "EEEEE...................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................\n" + //
                "........................", testEstate);
        testEstate.addDoor(4, 1, "Right");
        Square s = Board.getSquare(7, 7);
        main.Character character = new main.Character(s, HobbyDetectives.PlayerName.Bert, "\033[1;31m");
        assertEquals(null, character.getSquare().getEstate());
        character.moveCharacterIntoEstate(testEstate);
        assertEquals(testEstate, character.getSquare().getEstate());
    }
}
