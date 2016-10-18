package me.endureblackout.radstorm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandHandler implements CommandExecutor {

    RadStorm          plugin;

    public static int enabled;

    public CommandHandler(RadStorm instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("radstorm.use")) {
            sender.sendMessage(plugin.getMessage("no-permission"));
            return true;
        }

        if (args.length < 1) {
            showUsage(sender);
            return true;
        }

        String arg = args[0].toLowerCase();

        if (arg.equals("start")) {
            if (!plugin.getStormHandler().setActive(true)) {
                sender.sendMessage(plugin.getMessage("already-active"));
            }
        } else if (arg.equals("end")) {
            if (!plugin.getStormHandler().setActive(false)) {
                sender.sendMessage(plugin.getMessage("not-active"));
            }
        } else if (arg.equals("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(plugin.getMessage("reloaded"));
        } else {
            showUsage(sender);
        }

        return true;
    }

    /**
     * @param sender
     */
    private void showUsage(CommandSender sender) {
        sender.sendMessage(plugin.getMessage("usage"));
    }

}
