package io.github.smartsteves.prefixer.command.command.Prefix;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import io.github.smartsteves.prefixer.config.Config;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-07.
 * Command for prefix
 */
public class Prefix implements CommandExecutor {

    public String getName() {
        return "Prefix";
    }

    public boolean onConsoleCommand(String[] args) {
        Config.getInstance().getLocalizer().log(Level.INFO, "Command.CannotUseInConsole");
        return true;
    }

    public boolean onPlayerCommand(String[] args, Player player) {
        return false;
    }
}
