package io.github.smartsteves.prefixer.command.command.PrefixManage;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;

/**
 * Created by JUN on 2016-02-07.
 */
public class PrefixManage implements CommandExecutor{
    public String getName(){return "PrefixManage";}
    public boolean onConsoleCommand(String[] args){
        return false;
    }
    public boolean onPlayerCommand(String[] args, Player player){
        return false;
    }
}
