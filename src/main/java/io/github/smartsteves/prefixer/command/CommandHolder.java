package io.github.smartsteves.prefixer.command;

import io.github.smartsteves.prefixer.config.CommandData;
import io.github.smartsteves.prefixer.config.Config;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JUN on 2016-02-07.
 * Class that hold CommandExecutor, NEVER TOUCH
 */
public class CommandHolder {
    private HashMap<String, CommandHolder> child;
    private CommandExecutor executor;
    private CommandData commandData;
    private String code;

    public CommandHolder() {
        child = new HashMap<>();
        code = "";
    }

    public CommandHolder(CommandExecutor executor, String code) {
        child = new HashMap<>();
        this.executor = executor;
        commandData = Config.getInstance().getCommandDataContainer().getData(getCode(code, executor.getName()));
        this.code = getCode(code, executor.getName());
    }

    public void registerSubCommand(CommandExecutor subCommandExecutor) {
        child.put(Config.getInstance().getCommandDataContainer().getData(getCode(code, subCommandExecutor.getName())).getName(), new CommandHolder(subCommandExecutor, code));
    }

    public CommandHolder getSubCommandHolder(String name) {
        for (Map.Entry<String, CommandHolder> entry : child.entrySet()) {
            if (entry.getValue().getCodeName().equalsIgnoreCase(name.toLowerCase())) {
                return entry.getValue();
            }
        }
        return null;
    }

    private String getCodeName() {
        return executor.getName();
    }

    public boolean execute(String[] args, CommandSender sender) { //return false if it can't attemp command at first
        if (commandData == null) {
            if (args.length != 0 && child.containsKey(args[0])) {
                String[] args2 = new String[args.length - 1];
                System.arraycopy(args, 1, args2, 0, args.length - 1);
                child.get(args[0]).execute(args2, sender);
                return true;
            }
            return false;
        }
        if (!sender.hasPermission(commandData.getPermission())) {
            Config.getInstance().getLocalizer().sendMessageLocalize(sender, "command.NoPermission");
        } else {
            if (args.length != 0 && child.containsKey(args[0])) {
                String[] args2 = new String[args.length - 1];
                System.arraycopy(args, 1, args2, 0, args.length - 1);
                child.get(args[0]).execute(args2, sender);
            } else {
                if (sender instanceof ConsoleCommandSender) {
                    if (!executor.onConsoleCommand(args))
                        sender.sendMessage(commandData.getUsage());
                } else {
                    if (!executor.onPlayerCommand(args, (Player) sender))
                        sender.sendMessage(commandData.getUsage());
                }
            }
        }
        return true;
    }

    public String getUsage() {
        return commandData.getUsage();
    }

    private String getCode(String thisCode, String nextCode) {
        return thisCode + (thisCode.isEmpty() ? "" : ".") + nextCode;
    }
}
