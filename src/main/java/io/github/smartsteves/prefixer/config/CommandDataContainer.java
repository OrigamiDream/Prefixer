package io.github.smartsteves.prefixer.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JUN on 2016-02-07.
 * Class for executing command. NEVER TOUCH.
 */
public abstract class CommandDataContainer {
    private HashMap<String, CommandData> data = new HashMap<>();

    public CommandDataContainer(JsonObject object) {
        readData("", object);
    }

    private void readData(String parent, JsonObject object) {
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            if (!entry.getValue().isJsonObject()) continue;
            JsonObject element = entry.getValue().getAsJsonObject();
            if (element.has("Usage") && element.has("Permission") && element.has("Name")) {
                String usage = element.get("Usage").getAsString();
                String permission = element.get("Permission").getAsString();
                String name = element.get("Name").getAsString();
                data.put((parent.isEmpty() ? entry.getKey() : parent + "." + entry.getKey()).toLowerCase(), new CommandData(usage, permission, name));
            }
            if (element.isJsonObject())
                readData(parent.isEmpty() ? entry.getKey() : parent + "." + entry.getKey(), element);
        }
    }

    public CommandData getData(String code) {
        return data.containsKey(code.toLowerCase()) ? data.get(code.toLowerCase()) : null;
    }
}
