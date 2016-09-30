package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.ChatColor;

public class StormHandler implements Listener {
	
	RadStorm plugin;
	
	public StormHandler(RadStorm instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		final World world = e.getWorld();
		
		new BukkitRunnable() {
			public void run() {
				if(CommandHandler.enabled == 1) {
					world.setStorm(false);
					CommandHandler.enabled = 0;
					Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[RS] RadStorm has ended. You may come out of shelter.");
				}
			}
		}.runTaskLater(plugin, plugin.getConfig().getInt("RadStorm Time")*20);
		
		if(CommandHandler.enabled == 1) {
			new BukkitRunnable() {
				public void run() {
					
					if(!(world.hasStorm())) {
						cancel();
						world.setStorm(true);
					}
					
					if(world.hasStorm()) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							int highY = world.getHighestBlockYAt(p.getLocation());
							
							
							if(!(CommandHandler.enabled == 1)) {
								cancel();
							} else {	
								if(!(highY - p.getLocation().getBlockY() >= 4) && p.getGameMode() == GameMode.SURVIVAL) {
									p.damage(plugin.getConfig().getDouble("RadStorm Damage"));
								}
							}
						}
					}
				}
			}.runTaskTimer(plugin, 5*20, 5*20);
		} else { 
			return;
		}
	}
}
