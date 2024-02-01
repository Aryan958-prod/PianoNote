import model.PianoGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static model.PianoGame.NOTES;
import static org.junit.jupiter.api.Assertions.*;

public class PianoGameTest {
    private PianoGame testPianoGame;

    @BeforeEach
    void runBefore() {
        testPianoGame = new PianoGame();
    }

    @Test
    void testAddSinglePlayer() {
        testPianoGame.addPlayer("Player 1");
        assertEquals(1, testPianoGame.getNumberOfPlayers());
    }

    @Test
    void testAddMultiplePlayers() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.addPlayer("Player 3");
        assertEquals(3, testPianoGame.getNumberOfPlayers());
    }

    @Test
    void testGetRandomNote() {
        String randomNote = testPianoGame.getRandomNote();
        assertTrue(NOTES.contains(randomNote));
    }

    @Test
    void testNewRound() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        assertEquals("Player 1", testPianoGame.getCurrentPlayerName());
    }

    @Test
    void testNextPlayer() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        testPianoGame.nextPlayer();
        assertEquals("Player 2", testPianoGame.getCurrentPlayerName());
    }

    @Test
    void testNextRound() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        testPianoGame.nextPlayer();
        assertEquals("Player 2", testPianoGame.getCurrentPlayerName());
        testPianoGame.nextRound();
        assertEquals("Player 1", testPianoGame.getCurrentPlayerName());
    }

    @Test
    void testNextPlayerWhenLastPlayer() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        testPianoGame.nextPlayer();
        testPianoGame.nextPlayer();
        assertEquals("Player 2", testPianoGame.getCurrentPlayerName());
    }

    @Test
    void testVerifyAnswerCorrect() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        testPianoGame.getRandomNote();
        assertTrue(testPianoGame.verifyAnswer(testPianoGame.getCurrentNote()));
    }

    @Test
    void testVerifyAnswerIncorrect() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        testPianoGame.getRandomNote();
        List<String> notes = new ArrayList<>(List.copyOf(NOTES));
        notes.remove(testPianoGame.getCurrentNote());
        Collections.shuffle(notes);
        String incorrectAnswer = notes.get(0);
        assertFalse(testPianoGame.verifyAnswer(incorrectAnswer));
    }

    @Test
    void testCurrentPlayerScores() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        assertEquals(0, testPianoGame.getCurrentPlayerScore(0));
        testPianoGame.currentPlayerScores();
        assertEquals(1, testPianoGame.getCurrentPlayerScore(0));
    }

    @Test
    void testGetCurrentPlayerName() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        testPianoGame.nextRound();
        assertEquals("Player 1", testPianoGame.getCurrentPlayerName());
        testPianoGame.nextPlayer();
        assertEquals("Player 2", testPianoGame.getCurrentPlayerName());
    }

    @Test
    void testGetPlayerName() {
        testPianoGame.addPlayer("Player 1");
        testPianoGame.addPlayer("Player 2");
        assertEquals("Player 1", testPianoGame.getPlayerName(0));
        assertEquals("Player 2", testPianoGame.getPlayerName(1));
    }
}