package model;

import org.json.JSONObject;
import persistance.Writable;

// Represents a player in the game.
// A player has a name and a score.
// A player can increase their score by one.
// A player can get their name and score.
// A player can set their score.
// A player can be written to JSON.

public class Player implements Writable {
    private String name;
    private int score;
    private EventLog eventLog;

    // EFFECTS: Constructs a player with the given name and a score of 0.
    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.eventLog = EventLog.getInstance();
    }

    // MODIFIES: this
    // EFFECTS: Increases the score of the player by one.
    public void increaseScore() {
        score++;
        eventLog.logEvent(new Event(this + " scored!"));
    }

    // EFFECTS: Returns the name of the player.
    public String getName() {
        return name;
    }

    // EFFECTS: Returns the score of the player.
    public int getScore() {
        return score;
    }


    // MODIFIES: this
    // EFFECTS: Sets the score of the player to the given score.
    public void setScore(int score) {
        this.score = score;
        eventLog.logEvent(new Event(this + "'s score set to " + score));
    }

    @Override

    // EFFECTS: Returns the player as a JSON object.
    //         The JSON object contains the name and score of the player.
    //        Example: {"name":"John","score":0}
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("score", score);
        eventLog.logEvent(new Event( this + " written to JSON."));
        return json;
    }
}