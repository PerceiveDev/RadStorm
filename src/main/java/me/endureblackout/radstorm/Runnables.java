/**
 * 
 */
package me.endureblackout.radstorm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * @author Rayzr
 *
 */
public class Runnables {

    private static RadStorm         plugin;
    private static List<BukkitTask> laters;
    private static List<BukkitTask> repeats;

    public static void init(RadStorm plugin) {
        Runnables.plugin = plugin;
        laters = new ArrayList<BukkitTask>();
        repeats = new ArrayList<BukkitTask>();
    }

    public static void deinit() {
        cancelAll();
        Runnables.plugin = null;
        repeats = null;
    }

    public static void cancelLaters() {
        for (BukkitTask task : laters) {
            task.cancel();
        }
    }

    public static void cancelRepeats() {
        for (BukkitTask task : repeats) {
            task.cancel();
        }
    }

    public static void cancelAll() {
        cancelLaters();
        cancelRepeats();
    }

    public static void later(BukkitRunnable runnable, double seconds) {
        if (plugin == null) {
            throw new IllegalStateException("The Runnables class hasn't been initialized yet!");
        }
        laters.add(runnable.runTaskLater(plugin, (long) (seconds * 20.0)));
    }

    public static void repeat(BukkitRunnable runnable, double delay, double seconds) {
        if (plugin == null) {
            throw new IllegalStateException("The Runnables class hasn't been initialized yet!");
        }
        repeats.add(runnable.runTaskTimer(plugin, (long) (delay * 20.0), (long) (seconds * 20.0)));
    }

}
