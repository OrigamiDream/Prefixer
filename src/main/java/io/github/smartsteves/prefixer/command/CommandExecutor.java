package io.github.smartsteves.prefixer.command;

import org.bukkit.entity.Player;

/**
 * Created by JUN on 2016-02-07.
 * interface for holding command executing class
 */
public interface CommandExecutor {
    boolean onPlayerCommand(String[] args, Player player);

    boolean onConsoleCommand(String[] args);

    String getName();
}
