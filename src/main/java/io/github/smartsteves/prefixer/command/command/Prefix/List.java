package io.github.smartsteves.prefixer.command.command.Prefix;

import io.github.smartsteves.prefixer.command.CommandExecutor;
import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.data.DataContainer;
import io.github.smartsteves.prefixer.data.PlayerData;
import org.bukkit.entity.Player;

import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-13.
 * Command for List
 */
public class List implements CommandExecutor {
    public String getName() {
        return "List";
    }

    public boolean onConsoleCommand(String[] args) {
        Config.getInstance().getLocalizer().log(Level.INFO, "Command.CannotUseInConsole");
        return true;
    }

    public boolean onPlayerCommand(String[] args, Player player) {
        if (args.length > 1) return false;
        PlayerData data = DataContainer.getInstance().getData(player);
        java.util.Set<String> set = data.getPrefixList();
        String[] array = new String[100];
        set.toArray(array);
        if (args.length == 0) {
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "command.listTop");
            for (int i = 0; i < 4; i++) {
                if (i >= array.length) break;
                player.sendMessage(array[i]);
            }
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "command.listBottom");
            return true;
        }
        int index;
        try {
            index = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            return false;
        }
        if (index * 5 - 5 >= array.length) {
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "command.ListArrayOutOfBound");
            return true;
        }
        Config.getInstance().getLocalizer().sendMessageLocalize(player, "command.listTop");
        for (int i = index * 5 - 5; i <= index * 5 - 1; i++) {
            if (i >= array.length) break;
            player.sendMessage(array[i]);
        }
        Config.getInstance().getLocalizer().sendMessageLocalize(player, "command.listBottom");
        return true;
    }
}
