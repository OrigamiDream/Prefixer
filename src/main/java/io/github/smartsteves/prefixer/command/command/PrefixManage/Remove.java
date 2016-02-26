package io.github.smartsteves.prefixer.command.command.PrefixManage;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.config.Localizer;
import io.github.smartsteves.prefixer.data.DataContainer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by JUN on 2016-02-07.
 * Command for Remove
 */
public class Remove implements CommandExecutor {
    public String getName() {
        return "Remove";
    }

    public boolean onConsoleCommand(String[] args) {
        return execute(Bukkit.getConsoleSender(), args);
    }

    public boolean onPlayerCommand(String[] args, Player player) {
        return execute(player, args);
    }

    private boolean execute(CommandSender sender, String[] args) {
        Localizer localizer = Config.getInstance().getLocalizer();
        if (args.length != 2) return false;
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            localizer.sendMessageLocalize(sender, "Command.PlayerNotFound", args[0]);
        } else {
            if (DataContainer.getInstance().getData(target).removePrefix(args[1])) {
                localizer.sendMessageLocalize(sender, "Command.RemoveOtherPrefix", args[0], args[1]);
                localizer.sendMessageLocalize(target, "Command.ForcelyRemovePrefix", args[1]);
            } else {
                localizer.sendMessageLocalize(sender, "Command.NoHavePrefix", args[0], args[1]);
            }
        }
        return true;
    }
}
