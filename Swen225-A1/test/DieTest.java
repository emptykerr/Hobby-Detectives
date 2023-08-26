package test;
import org.junit.jupiter.api.Test;

public class DieTest {
    @Test
    public void dieGivesNumberBetweenOneAndSix(){
        int res = main.Die.roll();
        assert(res >= 1 && res <=6 );
    }
}
