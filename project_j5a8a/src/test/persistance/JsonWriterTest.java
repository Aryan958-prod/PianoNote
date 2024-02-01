package persistance;

import model.PianoGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            PianoGame pg = new PianoGame();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyPianoGame() {
        try {
            PianoGame pg = new PianoGame();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyPianoGame.json");
            writer.open();
            writer.write(pg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyPianoGame.json");
            pg = reader.read();
            assertEquals(0, pg.getNumberOfPlayers());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralPianoGame() {
        try {
            PianoGame pg = new PianoGame();
            pg.addPlayer("Player 1");
            pg.addPlayer("Player 2");
            pg.getPlayer(0).increaseScore();
            pg.getPlayer(1).increaseScore();
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralPianoGame.json");
            writer.open();
            writer.write(pg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralPianoGame.json");
            pg = reader.read();
            assertEquals(2, pg.getNumberOfPlayers());
            checkPlayer("Player 1", 1, pg.getPlayer(0));
            checkPlayer("Player 2", 1, pg.getPlayer(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}


