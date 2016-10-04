package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class StormHandler implements Listener {
	
	RadStorm plugin;
	
	public StormHandler(RadStorm instance) {
		this.plugin = instance;
	}

	@EventHandler
	public void onWeatherChange(final WeatherChangeEvent e) {
		
		new BukkitRunnable() {
			public void run() {
				World world = e.getWorld();
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
					World world = e.getWorld();

					if(!(world.hasStorm())) {
						cancel();
						world.setStorm(true);
					}
					
					if(world.hasStorm()) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							int highY = world.getHighestBlockYAt(p.getLocation());
							int currentY = p.getLocation().getBlockY();
							
							int difference;
							difference = currentY - highY;
							
							if(!(CommandHandler.enabled == 1)) {
								cancel();
							} else if(!(difference <= 4)){	
								p.damage(plugin.getConfig().getDouble("RadStorm Damage"));
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
