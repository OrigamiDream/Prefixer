package io.github.smartsteves.prefixer.event;

import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.data.DataContainer;
import io.github.smartsteves.prefixer.data.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by JUN on 2016-02-13.
 * Handle event that player chat.
 */
public class PlayerChatEventHandler implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (DataContainer.getInstance().getData(player) == null) {
            Config.getInstance().getLocalizer().sendMessageLocalize(player, "chat.notReadYet");
            return;
        }
        PlayerData data = DataContainer.getInstance().getData(player);
        String message = event.getMessage();
        if (player.hasPermission("prefixer.colorchat")) {
            message = ChatColor.translateAlternateColorCodes('&', message);
        }
        event.setFormat(Config.getInstance().getLocalizer().localize("chat.format", Config.getInstance().getPermissionPrefix().isUse() ? Config.getInstance().getPermissionPrefix().getPrefix(event.getPlayer()) : data.getRefinedForcePrefix(), data.getRefinedUserPrefix(), player.getDisplayName(), message));
    }
}
