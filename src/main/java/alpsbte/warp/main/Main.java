package alpsbte.warp.main;

import alpsbte.warp.main.commands.CMD_SetWarpCommand;
import alpsbte.warp.main.commands.CMD_WarpCommand;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Main extends JavaPlugin {



    private File customConfigFile;
    public YamlConfiguration customConfig;


    private static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getCommand("warp").setExecutor(new CMD_WarpCommand());
        getCommand("setwarp").setExecutor(new CMD_SetWarpCommand());
        System.out.println("Plugin loaded");
        createCustomConfig("config.yml");
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        for (String i: customConfig.getKeys(false)) {
            System.out.println(customConfig.get(i+".name"));
            System.out.println(customConfig.get(i+".name"));

        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    private void createCustomConfig(String s) {
        customConfigFile = new File(getDataFolder(), "warps.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("warps.yml", false);
        }

        customConfig = new YamlConfiguration();
        try {
            customConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static Main getPlugin() {
        return plugin;
    }
}
