package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RadStorm extends JavaPlugin {
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new StormHandler(this), this);
		
		getCommand("radstorm").setExecutor(new CommandHandler(this));
	}
}
