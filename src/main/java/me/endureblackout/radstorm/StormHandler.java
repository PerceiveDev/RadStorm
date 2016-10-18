/**
 * 
 */
package me.endureblackout.radstorm;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Rayzr
 *
 */
public class StormHandler extends BukkitRunnable {

    private boolean        active = false;
    private RadStorm       plugin;

    // Just a little variable to make sure you don't have extra runnables still
    // waiting to run
    private BukkitRunnable lastStopper;

    public StormHandler(RadStorm plugin) {
        this.plugin = plugin;
        // Repeat every 5 seconds
        runTaskTimer(plugin, 100, 100);
    }

    public void run() {
        if (!active) {
            return;
        }

        for (World world : Bukkit.getWorlds()) {
            if (!plugin.getEnabledWorlds().contains(world.getName())) {
                continue;
            }

            for (Player player : world.getPlayers()) {
                int highY = findRoof(world, player);
                int currentY = player.getLocation().getBlockY();

                int difference;
                difference = highY - currentY;

                if (difference < 4) {
                    player.damage(plugin.getConfig().getDouble("storm.damage"));
                }
            }
        }
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     * @return Whether or not the state actually changed. Returns false if (for
     *         example) it's already active and you try to set it to active.
     */
    public boolean setActive(boolean active) {
        if (this.active == active) {
            return false;
        }
        this.active = active;
        updateAll();
        return true;
    }

    private void updateAll() {

        if (active) {
            Bukkit.broadcastMessage(plugin.getMessage("storm-start"));
            stopLater();
        } else {
            Bukkit.broadcastMessage(plugin.getMessage("storm-stop"));
            lastStopper = null;
        }

        // Make sure all the worlds are in the right state
        for (World world : Bukkit.getWorlds()) {
            if (!plugin.getEnabledWorlds().contains(world.getName())) {
                continue;
            }
            if (active != world.hasStorm()) {
                world.setStorm(active);
            }
        }

    }

    private void stopLater() {

        // The next 3 lines are to prevent a storm getting cancelled repeatedly
        if (lastStopper != null) {
            lastStopper.cancel();
        }

        lastStopper = new BukkitRunnable() {
            public void run() {
                setActive(false);
            }
        };
        lastStopper.runTaskLater(plugin, (long) (plugin.getConfig().getDouble("storm.time") * 20.0));

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
