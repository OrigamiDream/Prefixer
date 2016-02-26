package io.github.smartsteves.prefixer.event;

import io.github.smartsteves.prefixer.config.Config;
import io.github.smartsteves.prefixer.data.DataContainer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.logging.Level;

/**
 * Created by JUN on 2016-02-10.
 */
public class JoinLeaveEventHandler implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        boolean result = DataContainer.getInstance().addData(event.getPlayer());
        if(!result){
            Config.getInstance().getLocalizer().sendMessageLocalize(event.getPlayer(),"dataContainer.errorOccur");
            Config.getInstance().getLocalizer().log(Level.WARNING,"data.IOExceptionRead",event.getPlayer().getDisplayName());
        }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        boolean result = DataContainer.getInstance().removeData(event.getPlayer());
        if(!result){
            Config.getInstance().getLocalizer().log(Level.WARNING,"data.IOExceptionWrite",event.getPlayer().getDisplayName());
        }
    }
}
