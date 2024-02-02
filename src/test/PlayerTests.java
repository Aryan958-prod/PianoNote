import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import model.Player;

public class PlayerTests {
    Player testPlayer;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Player 1");
    }

    @Test
    void testGetName() {
        assertEquals("Player 1", testPlayer.getName());
    }

    @Test
    void testGetScore() {
        assertEquals(0, testPlayer.getScore());
    }

    @Test
    void testIncreaseScore() {
        assertEquals(0, testPlayer.getScore());
        testPlayer.increaseScore();
        assertEquals(1, testPlayer.getScore());
    }
}
