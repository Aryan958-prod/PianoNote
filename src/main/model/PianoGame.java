package model;

import org.json.JSONObject;
import org.json.JSONArray;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



// Represents a game of piano note challenge.

public class PianoGame implements Writable {
    private List<Player> players = new ArrayList<>();
    private Player currentPlayer;
    public static final List<String> NOTES = new ArrayList<>(Arrays.asList("C", "D", "E", "F", "G", "A", "B"));
    private String currentNote;

    private static EventLog eventLog;


    public PianoGame() {
        eventLog = EventLog.getInstance();
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    // EFFECTS: Constructs a PianoGame with no players.
    public void addPlayer(String name) {
        Player player = new Player(name);
        players.add(player);
    }

    // MODIFIES: this
    // EFFECTS: Increases the score of the current player by one.
    public void currentPlayerScores() {

        currentPlayer.increaseScore();
        eventLog.logEvent(new Event(this + " scored!"));
    }

    // EFFECTS: Returns a random note from the list of notes.
    public String getRandomNote() {
        int randomIndex = (int) (Math.random() * NOTES.size());
        currentNote = NOTES.get(randomIndex);
        eventLog.logEvent(new Event("New note: " + currentNote));
        return currentNote;
    }

    // MODIFIES: this
    // EFFECTS: Sets the current player to the first player in the list of players.
    public void nextRound() {
        currentPlayer = players.get(0);
    }

    // MODIFIES: this
    // EFFECTS: Sets the current player to the next player in the list of players.
    public void nextPlayer() {
        int currentPlayerIndex = players.indexOf(currentPlayer);
        if (currentPlayerIndex < players.size() - 1) {
            currentPlayer = players.get(currentPlayerIndex + 1);
        }
    }

    // EFFECTS: Returns true if the given answer is the same as the current note.
    public boolean verifyAnswer(String answer) {
        return answer.toUpperCase().trim().equals(currentNote);
    }

    // EFFECTS: Returns the name of the current player.
    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    // EFFECTS: Returns the current note.
    public String getCurrentNote() {
        return currentNote;
    }

    // EFFECTS: Returns the score of the current player.
    public int getCurrentPlayerScore(int index) {
        return players.get(index).getScore();
    }

    public String getPlayerName(int index) {
        return players.get(index).getName();
    }



    // EFFECTS: Returns the number of players in the game.
    public int getNumberOfPlayers() {
        return players.size();
    }
    public Player getPlayer(int i) {
        return players.get(i);
    }

    @Override

    // EFFECTS: Returns the piano game as a JSON object.
    //        The JSON object contains the players in the game.
    //       Example: {"players":[{"name":"John","score":0},{"name":"Jane","score":0}]}
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("players", playersToJson());
        eventLog.logEvent(new Event("Game saved"));
        return json;
    }

    // EFFECTS: Returns players in this piano game as a JSON array
    //        Each player is represented by their own JSON object
    //      Example: [{"name":"John","score":0},{"name":"Jane","score":0}]
    private JSONArray playersToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Player p : players) {
            jsonArray.put(p.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: Prints all the logged events when the user quits the application.
    public static void printLoggedEvents() {
        for (Event event : eventLog) {
            System.out.println(event);
        }
    }
}