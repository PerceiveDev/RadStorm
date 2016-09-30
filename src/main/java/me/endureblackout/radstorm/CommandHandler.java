package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {
	
	RadStorm plugin;
	
	public static int enabled;
	
	public CommandHandler(RadStorm instance) {
		this.plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			Player p = (Player) sender;
			
			if(cmd.getName().equals("radstorm") && p.hasPermission("radstorm.use")) {
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("start")) {
						if(!(enabled == 1)) {
							enabled = 1;
							p.sendMessage(ChatColor.GREEN + "[RS] RadStorm initiated!");
							Bukkit.getServer().broadcastMessage(ChatColor.RED + "[RS] RadStorm has been initiated. Take cover!");
							
							if(p.getWorld().hasStorm()) {
								p.getWorld().setStorm(false);
								p.getWorld().setStorm(true);
							}else if(!(p.getWorld().hasStorm())) {
								p.getWorld().setStorm(false);
								p.getWorld().setStorm(true);
							}
						} else {
							p.sendMessage(ChatColor.RED + "[RS] RadStorm already enabled!");
						}
					} else if(args[0].equalsIgnoreCase("end")) {
						if(enabled == 1) {
							enabled = 0;
							p.getWorld().setStorm(false);
							p.sendMessage(ChatColor.GREEN + "[RS] RadStorm ended!");
							Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[RS] RadStorm has ended. You may come out of shelter.");
						} else {
							p.sendMessage(ChatColor.RED + "[RS] RadStorm already ended!");
						}
					}
				}
			}
		}
		return true;
	}

}
