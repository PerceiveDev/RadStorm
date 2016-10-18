package me.endureblackout.radstorm;

import java.io.File;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RadStorm extends JavaPlugin {

    private StormHandler stormHandler;

    public void onEnable() {

        Runnables.init(this);

        if (!getFile("config.yml").exists()) {
            saveResource("config.yml", true);
        }

        stormHandler = new StormHandler(this);

        new BukkitRunnable() {
            public void run() {
                stormHandler.setActive(true);
            }
        }.runTaskTimer(this, (long) (getConfig().getDouble("RadStorm Between") * 20.0), (long) (getConfig().getInt("RadStorm Between") * 20.0));

        Bukkit.getServer().getPluginManager().registerEvents(new StormListener(this), this);

        getCommand("radstorm").setExecutor(new CommandHandler(this));

    }

    /**
     * @param path the path to the file
     * @return A file relative to the plugin's data folder
     */
    public File getFile(String path) {
        return new File(getDataFolder(), path);
    }

    @Override
    public void onDisable() {
        Runnables.deinit();
    }

    /**
     * 
     * @param name
     * @return
     */
    public String getMessage(String name) {
        // An ugly line, but it does the job
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.prefix") + getConfig().getString("messages." + name));
    }

    /**
     * @return the storm handler instance
     */
    public StormHandler getStormHandler() {
        return stormHandler;
    }

    /**
     * 
     */
    public List<String> getEnabledWorlds() {
        return getConfig().getStringList("worlds");
    }

}
