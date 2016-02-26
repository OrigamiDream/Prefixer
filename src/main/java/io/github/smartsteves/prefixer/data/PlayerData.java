package io.github.smartsteves.prefixer.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.smartsteves.prefixer.Prefixer;
import io.github.smartsteves.prefixer.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class PlayerData {
    private String name = "";
    private String uuid = "";
    private String forcePrefix = "", userPrefix = "";
    private HashSet<String> prefixList;

    public static PlayerData newPlayerData(UUID uuid) {
        File file = new File(Prefixer.dataFolder+File.separator +"user" + File.separator+ uuid + ".json");
        Gson gson = new Gson();
        if(!file.exists()) return new PlayerData(uuid);
        try(FileReader reader = new FileReader(file)){
            return gson.fromJson(reader,PlayerData.class);
        }
        catch(IOException e){
            Config.getInstance().getLocalizer().log(Level.SEVERE, "data.IOExceptionRead", Bukkit.getPlayer(uuid).getDisplayName());
            e.printStackTrace();
        }
        return new PlayerData(uuid);
    }

    public PlayerData(UUID uuid){
        name = Bukkit.getPlayer(uuid).getDisplayName();
        this.uuid = uuid.toString();
        forcePrefix = userPrefix = "";
        prefixList = new HashSet<>();
    }

    public boolean write(){
        File file = new File(Prefixer.dataFolder+File.separator +"user" + File.separator+ uuid + ".json");
        try {
            File folder = new File(Prefixer.dataFolder+File.separator +"user");
            if(!folder.exists())folder.mkdir();
            if (!file.exists()) file.createNewFile();
        }
        catch(Exception e){

        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter writer = new FileWriter(file)){
            gson.toJson(this,writer);
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    public Set<String> getPrefixList(){ return prefixList;}
    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public String getForcePrefix() {
        return forcePrefix;
    }

    public String getUserPrefix() {
        return userPrefix;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    //return false when player already have that prefix.
    public boolean addPrefix(String prefix) {
        if (prefixList.contains(prefix)) {
            return false;
        }
        prefixList.add(prefix);
        return true;
    }

    //return false when player doesn't have that prefix.
    public boolean removePrefix(String prefix) {
        if (!prefixList.contains(prefix)) return false;
        prefixList.remove(prefix);
        Player player = getPlayer();
        if (userPrefix!=null && userPrefix.equals(prefix)) {
            userPrefix = null;
        }
        return true;
    }

    //return true when player doesn't have that prefix
    public boolean setUserPrefix(String prefix, boolean force){
        if(force){
            userPrefix = prefix;
            return true;
        }
        if(hasPrefix(prefix)){
            userPrefix = prefix;
            return true;
        }
        return false;
    }

    //return false when player doesn't use user prefix
    public boolean removeUserPrefix(){
        if(isUserPrefixEmpty()) return false;
        userPrefix = "";
        return true;
    }

    public void setForcePrefix(String prefix){
        forcePrefix = prefix;
    }

    //return false when player doens't use force prefix
    public boolean removeForcePrefix(String prefix){ //return false if player didn't have ForcePrefix
        if(isForcePrefixEmpty()) return false;
        forcePrefix = "";
        return true;
    }

    public boolean hasPrefix(String prefix){
        return prefixList.contains(prefix);
    }

    public boolean isUserPrefixEmpty(){
        return userPrefix==null||userPrefix.isEmpty();
    }

    public boolean isForcePrefixEmpty(){
        return forcePrefix==null||forcePrefix.isEmpty();
    }

    public String getRefinedUserPrefix(){
        return isUserPrefixEmpty()?"":ChatColor.RESET + ChatColor.getLastColors(userPrefix.substring(0,2)) + "[" + userPrefix + "]" + ChatColor.RESET;
    }
    public String getRefinedForcePrefix(){
        return isForcePrefixEmpty()?"":ChatColor.RESET + ChatColor.getLastColors(forcePrefix.substring(0,2)) + "[" + forcePrefix + "]" + ChatColor.RESET;
    }

}
