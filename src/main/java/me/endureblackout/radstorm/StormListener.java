package me.endureblackout.radstorm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class StormListener implements Listener {

    private RadStorm plugin;

    public StormListener(RadStorm instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent e) {
        if (!plugin.getEnabledWorlds().contains(e.getWorld().getName())) {
            // This isn't an enabled world
            return;
        }

        boolean active = plugin.getStormHandler().isActive();

        if (active != e.toWeatherState()) {
            e.setCancelled(true);
            e.getWorld().setStorm(active);
        }
    }

}
