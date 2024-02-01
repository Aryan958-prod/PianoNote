package persistance;

import model.PianoGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;



public class JsonReaderTest extends JsonTest {

    
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            PianoGame pg = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyPianoGame() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyPianoGame.json");
        try {
            PianoGame pg = reader.read();
            assertEquals(0, pg.getNumberOfPlayers());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralPianoGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralPianoGame.json");
        try {
            PianoGame pg = reader.read();
            assertEquals(2, pg.getNumberOfPlayers());
            assertEquals("Player 1", pg.getPlayer(0).getName());
            assertEquals("Player 2", pg.getPlayer(1).getName());
            assertEquals(0, pg.getPlayer(0).getScore());
            assertEquals(0, pg.getPlayer(1).getScore());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }



}

