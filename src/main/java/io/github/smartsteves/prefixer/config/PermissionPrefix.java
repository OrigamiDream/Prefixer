package io.github.smartsteves.prefixer.config;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;

/**
 * Created by JUN on 2016-02-07.\
 * Contain PermissionPrefix : Bleeding
 */
public class PermissionPrefix {
    private Boolean usePermissionPrefix;
    private List<Map.Entry<String, String>> data;

    PermissionPrefix() {
    }

    public boolean isUse() {
        return usePermissionPrefix;
    }

    public String getPrefix(Player p) {
        for (Map.Entry<String, String> entry : data) {
            if (p.hasPermission(entry.getKey())) return entry.getValue();
        }
        return "";
    }
}
