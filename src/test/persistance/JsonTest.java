package persistance;

import model.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPlayer(String name, int score, Player player) {
        assertEquals(name, player.getName());
        assertEquals(score, player.getScore());
    }
}