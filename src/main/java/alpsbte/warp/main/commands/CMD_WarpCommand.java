package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
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
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("warp")) {
                    YamlConfiguration warpList = Main.getPlugin().warpList;

                    for (String i : warpList.getKeys(false)) {
                        if (args[0].equalsIgnoreCase(warpList.getString(i + ".name"))) {
                            World world = Bukkit.getWorld(warpList.getString(i + ".world"));
                            Double x = (Double) warpList.get(i + ".x");
                            Double y = (Double) warpList.get(i + ".y");
                            Double z = (Double) warpList.get(i + ".z");
                            Double yaw = (Double) warpList.get(i + ".yaw");
                            Double pitch = (Double) warpList.get(i + ".pitch");
                            Location loc = new Location(world, x, y, z, yaw.floatValue(), pitch.floatValue());
                            p.teleport(loc);
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 5, 2);
                            return true;
                        }
                    }
                    p.sendMessage("nüd gfunde"); // TODO change
                } else {
                    p.sendMessage("§cBrooo du hesch kei recht alte, gang hei gö brüele"); // TODO change
                }
            }
        }
        return false;
    }
}
