package persistance;

import model.PianoGame;
import model.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


// Represents a reader that reads piano game from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }


    // EFFECTS: reads piano game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public PianoGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePianoGame(jsonObject);

    }

    // EFFECTS: reads source file as string and returns it
    // throws IOException if an error occurs reading data from file
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);

        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses piano game from JSON object and returns it
    // throws IOException if an error occurs parsing data from file
    private PianoGame parsePianoGame(JSONObject jsonObject) {
        PianoGame pg = new PianoGame();
        addPlayers(pg, jsonObject);
        return pg;
    }

    // MODIFIES: pg
    // EFFECTS: parses players from JSON object and adds them to piano game
    private void addPlayers(PianoGame pg, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("players");
        for (Object json : jsonArray) {
            JSONObject nextPlayer = (JSONObject) json;
            addPlayer(pg, nextPlayer);
        }
    }

    // MODIFIES: pg
    // EFFECTS: parses player from JSON object and adds it to piano game
    private void addPlayer(PianoGame pg, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int score = jsonObject.getInt("score");
        Player p = new Player(name);
        p.setScore(score);
        pg.addPlayer(p);
    }


}
