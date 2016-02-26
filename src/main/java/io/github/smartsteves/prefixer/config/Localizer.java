package io.github.smartsteves.prefixer.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-07.
 */
public abstract class Localizer {
    private final HashMap<String,String> data = new HashMap<>();

    public Localizer(JsonObject object){
        readLocalizeData("",object);
    }
    public String localize(String message, String... args) {
        if (!data.containsKey(message.toLowerCase())) {
            if (message.equalsIgnoreCase("localize.noSuchMessage")) {
                return "Cannot find Language.NoSuchMessage in file language.json";
            }
            return localize("localize.noSuchMessage", message);
        }
        String target = data.get(message.toLowerCase());
        for (int i = 0; i < args.length; i++) {
            target = target.replace("{" + i + "}", args[i]);
        }
        return target;
    }
    private void readLocalizeData(String parent, JsonObject object) { //reading json and add data in hashmap.
        for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
            JsonElement element = entry.getValue();
            if (element.isJsonObject()) {
                readLocalizeData(parent.equals("") ? entry.getKey() : parent + "." + entry.getKey(), element.getAsJsonObject());
            } else {
                data.put((parent.equals("") ? entry.getKey() : parent + "." + entry.getKey()).toLowerCase(), element.getAsString());
            }
        }
    }
    public void log(Level level, String message, String... args) {
        Bukkit.getLogger().log(level, localize(message, args));
    }
    public void sendMessageLocalize(CommandSender sender,  String message, String... args){
        sender.sendMessage(localize(message,args));
    }

}
