package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CMD_WarpCommand implements CommandExecutor {
// TODO Add args check
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("warp")) {
            YamlConfiguration warpList = Main.getPlugin().warpList;
            YamlConfiguration config = Main.getPlugin().config;
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("alpsbte.warp")) {
                    if (args.length == 1) {

                    for (String i : warpList.getKeys(false)) {
                        if (args[0].equalsIgnoreCase(warpList.getString(i + ".name"))) {
                            World world = Bukkit.getWorld(warpList.getString(i + ".world"));
                            Double x = (Double) warpList.get(i + ".x");
                            Double y = (Double) warpList.get(i + ".y");
                            Double z = (Double) warpList.get(i + ".z");
                            String yaw = warpList.getString(i + ".yaw"); // pls don't ask why. It works
                            String pitch = warpList.getString(i + ".pitch");
                            Location loc = new Location(world, x, y, z, Float.parseFloat(yaw), Float.parseFloat(pitch));
                            p.teleport(loc);
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDEREYE_LAUNCH, 5, 1);
                            p.sendMessage(config.getString("messages.warp_success"));
                            return true;
                        }
                    }
                    p.sendMessage(config.getString("messages.warp_error_not_found"));
                } else {
                        p.sendMessage(config.getString("messages.warp_error_usage"));
                    }
                } else {
                    p.sendMessage(config.getString("messages.no_permission"));
                }
            }  else {
                Bukkit.getLogger().log(Level.SEVERE, config.getString("messages.explain_to_console_why_you_cant_warp_a_console"));
            }
        }
        return false;
    }
}
