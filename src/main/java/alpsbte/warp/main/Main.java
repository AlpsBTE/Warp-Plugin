package alpsbte.warp.main;

import alpsbte.warp.main.commands.CMD_DelWarp;
import alpsbte.warp.main.commands.CMD_SetWarp;
import alpsbte.warp.main.commands.CMD_SetWarpPlate;
import alpsbte.warp.main.commands.CMD_Warp;
import alpsbte.warp.main.core.EventListener;
import alpsbte.warp.main.core.database.DatabaseConnection;
import alpsbte.warp.main.core.system.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;

public final class Main extends JavaPlugin {
    private static Main plugin;
    public YamlConfiguration config;
    private static HashMap<Location, String> warpPlateList;

    @Override
    public void onEnable() {
        plugin = this;

        // Register Commands
        getCommand("warp").setExecutor(new CMD_Warp());
        getCommand("setwarp").setExecutor(new CMD_SetWarp());
        getCommand("delwarp").setExecutor(new CMD_DelWarp());
        getCommand("setwarpplate").setExecutor(new CMD_SetWarpPlate());

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        createConfig("config.yml");

        // Initialize database connection
        try {
            DatabaseConnection.InitializeDatabase();
            Bukkit.getConsoleSender().sendMessage("Successfully initialized database connection.");
        } catch (Exception ex) {
            Bukkit.getConsoleSender().sendMessage("Could not initialize database connection.");
            Bukkit.getLogger().log(Level.SEVERE, ex.getMessage(), ex);

            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        warpPlateList = Warp.getWarpPlates();

        Bukkit.getLogger().log(Level.INFO,"AlpsBTE Warp Plugin loaded!");
    }

    private void createConfig(String s) {
        File createConfig = new File(getDataFolder(), "config.yml");
        if (!createConfig.exists()) {
            createConfig.getParentFile().mkdirs();
            saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(createConfig);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static HashMap<Location, String> getWarpPlateList() { return warpPlateList; }
}
