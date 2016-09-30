package me.endureblackout.radstorm;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RadStorm extends JavaPlugin {
	public void onEnable() {
		
		File file = new File(getDataFolder(), "config.yml");
		if(!(file.exists())) {
			try {
				saveConfig();
				setupConfig(getConfig());
				getConfig().options().copyDefaults(true);
				saveConfig();
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		Bukkit.getServer().getPluginManager().registerEvents(new StormHandler(this), this);
		
		getCommand("radstorm").setExecutor(new CommandHandler(this));
		
		new BukkitRunnable() {
			public void run() {
				if(CommandHandler.enabled == 0) {
					for(World world : Bukkit.getServer().getWorlds()) {
						world.setStorm(true);
						Bukkit.getServer().broadcastMessage(ChatColor.RED + "[RS] RadStorm has been initiated. Take cover!");
					}
				}
			}
		}.runTaskTimer(this, this.getConfig().getInt("RadStorm Time")*20, this.getConfig().getInt("RadStorm Time")*20);
	}
	
	private void setupConfig(FileConfiguration config) throws IOException {
		if (!new File(getDataFolder(), "RESET.FILE").exists()) {
			new File(getDataFolder(), "RESET.FILE").createNewFile();
			getConfig().set("RadStorm Time", 420);
			getConfig().set("RadStorm Damage", 2.0);
			getConfig().set("RadStorm Between", 1800);
		}
	}
}
