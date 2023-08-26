package test;

import static org.junit.Assert.*;

import org.junit.Test;
import main.*;

public class EstateTest {
    @Test
    public void squarePartOfEstateReturnsCorrectValues(){
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
        
        assertEquals(null, testEstate.squarePartOfEstate(Board.getSquare(15, 15)));
        assertEquals(testEstate, testEstate.squarePartOfEstate(Board.getSquare(2, 2)));
    }
}
