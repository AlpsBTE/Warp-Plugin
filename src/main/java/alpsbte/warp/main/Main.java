package alpsbte.warp.main;

import alpsbte.warp.main.commands.Home.CMD_DelHome;
import alpsbte.warp.main.commands.Home.CMD_Home;
import alpsbte.warp.main.commands.Home.CMD_HomeList;
import alpsbte.warp.main.commands.Home.CMD_SetHome;
import alpsbte.warp.main.commands.Warp.*;
import alpsbte.warp.main.core.EventListener;
import alpsbte.warp.main.core.TabCompletion;
import alpsbte.warp.main.core.database.DatabaseConnection;
import alpsbte.warp.main.core.system.Warp;
import eu.decentsoftware.holograms.api.DHAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static net.kyori.adventure.text.Component.text;

public final class Main extends JavaPlugin {
    private static Main plugin;
    public YamlConfiguration config;
    private static HashMap<Location, String> warpPlateList;
    private static List<String> warpTabCompletionList;

    @Override
    public void onEnable() {
        plugin = this;

        if (!Bukkit.getPluginManager().isPluginEnabled("DecentHolograms")) {
            getLogger().severe("DecentHolograms is not installed or not enabled!");
            getLogger().severe("This plugin will be disabled!");
            this.setEnabled(false);
            return;
        }

        // Register Commands
        getCommand("warp").setExecutor(new CMD_Warp());
        getCommand("setwarp").setExecutor(new CMD_SetWarp());
        getCommand("delwarp").setExecutor(new CMD_DelWarp());
        getCommand("setwarpplate").setExecutor(new CMD_SetWarpPlate());
        getCommand("delwarpplate").setExecutor(new CMD_DelWarpPlate());
        getCommand("home").setExecutor(new CMD_Home());
        getCommand("sethome").setExecutor(new CMD_SetHome());
        getCommand("delhome").setExecutor(new CMD_DelHome());
        getCommand("homes").setExecutor(new CMD_HomeList());

        // Register Tab Completion
        getCommand("warp").setTabCompleter(new TabCompletion());

        // Register Event Listeners
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        createConfig("config.yml");

        // Initialize database connection
        try {
            DatabaseConnection.InitializeDatabase();
            Bukkit.getConsoleSender().sendMessage("Successfully initialized database connection.");
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("Could not initialize database connection.");
            Main.getPlugin().getComponentLogger().error(text("An error occurred while initializing a database connection!"), e);

            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        warpPlateList = Warp.getWarpPlates();
        warpTabCompletionList = Warp.getTabCompletionList();

        setHolograms();

        Main.getPlugin().getComponentLogger().info("AlpsBTE Warp Plugin loaded!");
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

    public void setHolograms() {
        for (Location key : warpPlateList.keySet()) {
            String value = warpPlateList.get(key);
            Location hologramLocation = new Location(
                    key.getWorld(),
                    Math.floor(key.getX()) + 0.5,
                    Math.floor(key.getY()) + 1.5,
                    Math.floor(key.getZ()) + 0.5);

            DHAPI.createHologram("warp_" + value.replaceAll("[^a-zA-Z0-9_-]", ""),
                    hologramLocation, false,
                    Collections.singletonList(
                            "§a§l" + value.toUpperCase()
                    ));
        }
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static HashMap<Location, String> getWarpPlateList() { return warpPlateList; }
    public static List<String> getWarpTabCompletionList() { return warpTabCompletionList; }
}
