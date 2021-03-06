package io.github.smartsteves.prefixer.command.command.Prefix;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.data.DataContainer;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-07.
 * Command for remove
 */
public class Remove implements CommandExecutor {
    public String getName() {
        return "Remove";
    }

    public boolean onConsoleCommand(String[] args) {
        Config.getInstance().getLocalizer().log(Level.INFO, "Command.CannotUseInConsole");
        return true;
    }

    public boolean onPlayerCommand(String[] args, Player player) {
        if (args.length != 1) return false;
        if (DataContainer.getInstance().getData(player).removePrefix(args[0])) {
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "Command.Remove", args[0]);
        } else {
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "Command.NoSuchPrefix", args[0]);
        }
        return true;
    }
}
