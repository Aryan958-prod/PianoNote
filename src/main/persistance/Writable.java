package persistance;

import org.json.JSONObject;

// Represents a class that can be written to JSON
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
