package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
	public void onWeatherChange(WeatherChangeEvent e) {
		final World world = e.getWorld();
		
		if(CommandHandler.enabled == 1) {
			new BukkitRunnable() {
				public void run() {
					if(world.hasStorm()) {
						for(Player p : Bukkit.getOnlinePlayers()) {
							int highY = world.getHighestBlockYAt(p.getLocation());
							
							
							if(!(CommandHandler.enabled == 1)) {
								cancel();
							} else {	
								if(!(highY - p.getLocation().getBlockY() >= 4) && p.getGameMode() == GameMode.SURVIVAL) {
									p.damage(2.0);
								}
							}
						}
					}
				}
			}.runTaskTimer(plugin, 5*20, 5*20);
		}
	}
}
