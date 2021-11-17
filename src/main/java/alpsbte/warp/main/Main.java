package alpsbte.warp.main;

import alpsbte.warp.main.commands.CMD_DelWarpCommand;
import alpsbte.warp.main.commands.CMD_SetWarpCommand;
import alpsbte.warp.main.commands.CMD_WarpCommand;
import alpsbte.warp.main.core.DatabaseConnection;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public final class Main extends JavaPlugin {



    private File createWarpList;
    public YamlConfiguration warpList;

    private File createConfig;
    public YamlConfiguration config;


    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;

        // Set Commands
        getCommand("warp").setExecutor(new CMD_WarpCommand());
        getCommand("setwarp").setExecutor(new CMD_SetWarpCommand());
        getCommand("delwarp").setExecutor(new CMD_DelWarpCommand());

        createWarpList("warps.yml");
        createConfig("config.yml");
        try {
            warpList.load(createWarpList);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

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

        Bukkit.getLogger().log(Level.INFO,"AlpsBTE Warp Plugin loaded!");
    }

    private void createWarpList(String s) {
        createWarpList = new File(getDataFolder(), "warps.yml");
        if (!createWarpList.exists()) {
            createWarpList.getParentFile().mkdirs();
            saveResource("warps.yml", false);
        }

        warpList = new YamlConfiguration();
        try {
            warpList.load(createWarpList);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createConfig(String s) {
        createConfig = new File(getDataFolder(), "config.yml");
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
}
