package io.github.smartsteves.prefixer.command.command.Prefix;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.data.DataContainer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-07.
 */
public class UnUse implements CommandExecutor{
    public String getName(){return "UnUse";}
    public boolean onConsoleCommand(String[] args){
        Config.getInstance().getLocalizer().log(Level.INFO,"Command.CannotUseInConsole");
        return true;
    }
    public boolean onPlayerCommand(String[] args, Player player){
        if(DataContainer.getInstance().getData(player).removeUserPrefix()){
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "Command.UnusePrefix");
        }
        else{
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "Command.NoUsingPrefix");
        }
        return true;
    }
}
