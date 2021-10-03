package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;

import static java.lang.Integer.parseInt;

public class CMD_SetWarpCommand implements CommandExecutor {

    /*

    TODO check if Warp exisits
    TODO check for args

     */

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setwarp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("alpsbte.moderator")) {
                    if (args[0] != null && args[1] != null) {


                    }

                    FileConfiguration warpList = Main.getPlugin().warpList;
                    List<Integer> ids = new ArrayList<Integer>();

                    for (String i : warpList.getKeys(false)) {
                        ids.add(parseInt(i.split("_")[1]));
                    }

                    String newId = "warp_0";
                    if (warpList.getKeys(false).size() != 0) {
                        newId = "warp_" + (Collections.max(ids, null) + 1);
                    }

                    warpList.set(newId, "Pater");
                    warpList.createSection(newId);
                    warpList.set(newId + ".name", args[0]);
                    warpList.set(newId + ".x", p.getLocation().getX());
                    warpList.set(newId + ".y", p.getLocation().getY());
                    warpList.set(newId + ".z", p.getLocation().getZ());
                    warpList.set(newId + ".yaw", p.getLocation().getYaw());
                    warpList.set(newId + ".pitch", p.getLocation().getPitch());
                    warpList.set(newId + ".world", p.getLocation().getWorld().getName());
                    warpList.set(newId + ".country", args[1]);
                    warpList.set(newId + ".by", String.valueOf(p.getUniqueId()));
                    p.sendMessage("Warp wurde erstellt"); // TODO Change

                    try {
                        warpList.save(Main.getPlugin().getDataFolder() + File.separator + "warps.yml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Bukkit.getLogger().log(Level.SEVERE, "Leon... einfach nei"); // TODO Change
            }
        }
        return false;
    }
}
