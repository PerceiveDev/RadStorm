package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

        Runnables.later(new BukkitRunnable() {
            public void run() {
                World world = e.getWorld();
                if (CommandHandler.enabled == 1) {
                    world.setStorm(false);
                    CommandHandler.enabled = 0;
                    Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "[RS] RadStorm has ended. You may come out of shelter.");
                }
            }
        }, plugin.getConfig().getInt("RadStorm Time") * 20);

        if (CommandHandler.enabled == 1) {
            Runnables.repeat(new BukkitRunnable() {
                public void run() {
                    World world = e.getWorld();

                    if (!(world.hasStorm())) {
                        cancel();
                        world.setStorm(true);
                    }

                    if (world.hasStorm()) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            int highY = findRoof(world, p);
                            int currentY = p.getLocation().getBlockY();

                            int difference;
                            difference = highY - currentY;
                            


                            if (!(CommandHandler.enabled == 1)) {
                                cancel();
                            } else if (!(difference >= 4)) {
                                p.damage(plugin.getConfig().getDouble("RadStorm Damage"));
                            }
                        }
                    }
                }
            }, 5, 5);
        } else {
            return;
        }

    }

    private int findRoof(World world, Player player) {
        Location loc = player.getLocation();
        for (int y = world.getMaxHeight(); y >= 1; y--) {
            if (world.getBlockAt(loc.getBlockX(), y, loc.getBlockZ()).getType().isSolid()) {
                return y;
            }
        }
        return 0;
    }

}
