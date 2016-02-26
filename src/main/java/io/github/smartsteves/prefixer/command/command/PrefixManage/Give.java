package io.github.smartsteves.prefixer.command.command.PrefixManage;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.config.Localizer;
import io.github.smartsteves.prefixer.data.DataContainer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by JUN on 2016-02-07.
 */
public class Give implements CommandExecutor {
    public String getName(){return "Give";}
    public boolean onConsoleCommand(String[] args){
        return execute(args, Bukkit.getConsoleSender());
    }
    public boolean onPlayerCommand(String[] args, Player player){
        return execute(args, player);
    }
    private boolean execute(String[] args, CommandSender sender){
        Localizer localizer = Config.getInstance().getLocalizer();
        if(args.length != 2)return false;
        Player target = Bukkit.getPlayerExact(args[0]);
        if(target==null){
            localizer.sendMessageLocalize(sender, "Command.PlayerNotFound",args[0]);
        }
        else{
            if(DataContainer.getInstance().getData(target).addPrefix(ChatColor.translateAlternateColorCodes('&',args[1]))){
                localizer.sendMessageLocalize(sender,"Command.PrefixGive",args[0],args[1]);
                localizer.sendMessageLocalize(target,"Command.PrefixGet", args[1]);
            }
            else{
                localizer.sendMessageLocalize(sender, "Command.AlreadyHave",args[0],args[1]);
            }
        }
        return true;
    }
}
