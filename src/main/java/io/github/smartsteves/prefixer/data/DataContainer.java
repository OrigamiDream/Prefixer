package io.github.smartsteves.prefixer.data;

import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.config.Localizer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by JUN on 2016-02-05.
 */
public class DataContainer {
    private  static DataContainer INSTANCE;
    private HashMap<UUID,PlayerData> data;
    private DataContainer(){
        data = new HashMap<UUID,PlayerData>();
        for(Player player: Bukkit.getOnlinePlayers()){
            PlayerData playerData = PlayerData.newPlayerData(player.getUniqueId());
            if(playerData == null){
                Config.getInstance().getLocalizer().sendMessageLocalize(player, "dataContainer.errorOccur",player.getDisplayName());
            }
            else {
                data.put(player.getUniqueId(), playerData);
            }
        }
    }
    public static DataContainer getInstance(){
        if(INSTANCE==null){
            synchronized(DataContainer.class){
                if(INSTANCE==null){
                    INSTANCE = new DataContainer();
                }
            }
        }
        return INSTANCE;
    }
    public PlayerData getData(Player p){
        return data.containsKey(p.getUniqueId())?data.get(p.getUniqueId()):null;
    }
    public boolean addData(Player p){
        PlayerData playerData = PlayerData.newPlayerData(p.getUniqueId());
        if(playerData == null){
            return false;
        }
        else{
            data.put(p.getUniqueId(),playerData);
        }
        return true;
    }
    public boolean saveData(Player p){
        if(!data.containsKey(p.getUniqueId())){
            return false;
        }
        else{
            data.get(p.getUniqueId()).write();
        }
        return true;
    }
    public boolean removeData(Player p){
        boolean result = saveData(p);
        if(result){
            data.remove(p.getUniqueId());
        }
        return result;
    }
}
