package io.github.smartsteves.prefixer.config;

/**
 * Created by JUN on 2016-02-07.\
 * This class contain command data that read from config.json
 */
public class CommandData {
    private String usage;
    private String permission;
    private String name;

    public CommandData(String usage, String permission, String name) {
        this.usage = usage;
        this.permission = permission;
        this.name = name;
    }

    public String getUsage() {
        return usage;
    }

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }
}
