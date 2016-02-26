package io.github.smartsteves.prefixer.event;

import io.github.smartsteves.prefixer.command.CommandHolder;
import io.github.smartsteves.prefixer.command.command.Prefix.*;
import io.github.smartsteves.prefixer.command.command.PrefixManage.Give;
import io.github.smartsteves.prefixer.command.command.PrefixManage.PrefixManage;
import io.github.smartsteves.prefixer.command.command.PrefixManage.SetForcePrefix;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

/**
 * Created by JUN on 2016-02-10.
 * Handle commandEvent.
 */
public class CommandEventHandler implements Listener {
    CommandHolder holder;

    public CommandEventHandler() {
        holder = new CommandHolder();
        holder.registerSubCommand(new Prefix());
        holder.registerSubCommand(new PrefixManage());
        CommandHolder prefixCommandHolder = holder.getSubCommandHolder("Prefix");
        CommandHolder prefixManageCommandHolder = holder.getSubCommandHolder("PrefixManage");
        prefixCommandHolder.registerSubCommand(new Remove());
        prefixCommandHolder.registerSubCommand(new UnUse());
        prefixCommandHolder.registerSubCommand(new Set());
        prefixCommandHolder.registerSubCommand(new List());
        prefixManageCommandHolder.registerSubCommand(new Give());
        prefixManageCommandHolder.registerSubCommand(new io.github.smartsteves.prefixer.command.command.PrefixManage.Remove());
        prefixManageCommandHolder.registerSubCommand(new io.github.smartsteves.prefixer.command.command.PrefixManage.Set());
        prefixManageCommandHolder.registerSubCommand(new SetForcePrefix());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onConsoleCommand(ServerCommandEvent event) {
        boolean result = executeCommand(event.getSender(), event.getCommand().split(" "));
        event.setCancelled(result);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        boolean result = executeCommand(event.getPlayer(), event.getMessage().substring(1, event.getMessage().length()).split(" "));
        event.setCancelled(result);
    }

    public boolean executeCommand(CommandSender sender, String[] args) {
        return holder.execute(args, sender);
    }
}