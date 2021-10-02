package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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
                    p.sendMessage(String.valueOf(customConfig.get(i+".name")));
                    if (args[0].equalsIgnoreCase(String.valueOf(customConfig.get(i+".name")))) {
                        World world = Bukkit.getWorld(String.valueOf(customConfig.get(i+".x")));
                        Double x = (Double) customConfig.get(i+".x");
                        Double y = (Double) customConfig.get(i+".y");
                        Double z = (Double) customConfig.get(i+".z");
                        Location loc = new Location(world, x, y, z);
                        p.teleport(loc);


                    }

                }
            }
        }
        return false;
    }
}
