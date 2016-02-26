package io.github.smartsteves.prefixer.config;

import com.google.gson.*;
import io.github.smartsteves.prefixer.Prefixer;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.logging.Level;

/**
 * Created by JUN on 2016-01-29.
 */

/**
 * This class is localizing and get Config from config.json
 * Basic config.json file is in resources.
 * HashMap Key is save like "language.finish"
 * To get config/localize message, use localize(String message/or Config. String... args)
 */
public class Config {
    private static volatile Config instance;
    private  Localizer localizer;
    private  PermissionPrefix permissionPrefix;
    private  CommandDataContainer commandDataContainer;
    private Config() {
        File file = new File(Prefixer.dataFolder + File.separator + "config.json");
        JsonParser parser = new JsonParser();
        try {
            JsonObject object =  parser.parse(new InputStreamReader(new FileInputStream(file))).getAsJsonObject();
            JsonObject localizeObject = object.get("Localize").getAsJsonObject();
            localizer = new Localizer(localizeObject){};
            JsonElement permissionPrefixObject= object.get("ForcePrefix").getAsJsonObject();
            Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();
            permissionPrefix = gson.fromJson(permissionPrefixObject,PermissionPrefix.class);
            JsonObject commandDataObject = object.get("CommandData").getAsJsonObject();
            commandDataContainer = new CommandDataContainer(commandDataObject){};

        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot find config.json");
        } catch (JsonIOException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error occured while reading config.json");
        } catch (JsonSyntaxException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Error occured while reading config.json");
        }

    }
    public static Config getInstance() {
        if (instance == null) {
            synchronized (Config.class) {
                if (instance == null) {
                    instance = new Config();
                }
            }
        }
        return instance;
    }

    public Localizer getLocalizer(){
        if(localizer==null) System.err.println("ERR");
        return localizer;
    }

    public PermissionPrefix getPermissionPrefix(){
        return permissionPrefix;
    }

    public CommandDataContainer getCommandDataContainer(){ return commandDataContainer;}
}
