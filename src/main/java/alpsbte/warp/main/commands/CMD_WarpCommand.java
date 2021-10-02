package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CMD_WarpCommand implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("warp")) {
            Player p = (Player) sender;
            if (p.hasPermission("warp")) {
                YamlConfiguration customConfig = Main.getPlugin().customConfig;
                for (String i: customConfig.getKeys(false)) {
                    if (args[0].equalsIgnoreCase((String) customConfig.get(i+".name"))) {
                        System.out.println(customConfig.get(i+".x"));
                        System.out.println(customConfig.get(i+".y"));
                        System.out.println(customConfig.get(i+".z"));
                    }

                }
            }
        }
        return false;
    }
}
