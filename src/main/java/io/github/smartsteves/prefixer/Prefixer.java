package io.github.smartsteves.prefixer;

import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.data.DataContainer;
import io.github.smartsteves.prefixer.event.CommandEventHandler;
import io.github.smartsteves.prefixer.event.JoinLeaveEventHandler;
import io.github.smartsteves.prefixer.event.PlayerChatEventHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by JUN on 2016-01-29.
 */
public class Prefixer extends JavaPlugin {
    public static String dataFolder;
    private CommandEventHandler commandHandler;
    private JoinLeaveEventHandler joinLeaveEventHandler;
    private PlayerChatEventHandler playerChatEventHandler;

    @Override
    public void onEnable() {
        dataFolder = getDataFolder().getAbsolutePath();
        File config = new File(getDataFolder().getAbsolutePath() + "/config.json");
        if(!config.exists()){
            Bukkit.getLogger().log(Level.SEVERE,"Cannot find config.json");

        }
        Config.getInstance().getLocalizer().log(Level.INFO, "localize.finish");
        DataContainer.getInstance();
        commandHandler = new CommandEventHandler();
        joinLeaveEventHandler = new JoinLeaveEventHandler();
        playerChatEventHandler = new PlayerChatEventHandler();
        Bukkit.getServer().getPluginManager().registerEvents(commandHandler,this);
        Bukkit.getServer().getPluginManager().registerEvents(joinLeaveEventHandler,this);
        Bukkit.getServer().getPluginManager().registerEvents(playerChatEventHandler,this);
    }

    @Override
    public void onDisable() {
        DataContainer dataContainer = DataContainer.getInstance();
        for(Player player:Bukkit.getOnlinePlayers()){
            dataContainer.removeData(player);
        }
    }
}
