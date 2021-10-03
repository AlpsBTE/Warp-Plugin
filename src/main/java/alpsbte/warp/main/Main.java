package alpsbte.warp.main;

import alpsbte.warp.main.commands.CMD_SetWarpCommand;
import alpsbte.warp.main.commands.CMD_WarpCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {



    private File createWarpList;
    public YamlConfiguration warpList;

    private File createConfig;
    public YamlConfiguration config;


    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        getCommand("warp").setExecutor(new CMD_WarpCommand());
        getCommand("setwarp").setExecutor(new CMD_SetWarpCommand());
        System.out.println("Plugin loaded");
        createWarpList("warps.yml");
        createConfig("config.yml");
        try {
            warpList.load(createWarpList);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
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
            config.load(createWarpList);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Main getPlugin() {
        return plugin;
    }
}
