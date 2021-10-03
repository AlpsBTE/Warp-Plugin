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


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Main.getPlugin().config;
        FileConfiguration warpList = Main.getPlugin().warpList;
        if (command.getName().equalsIgnoreCase("setwarp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission(config.getString("permission.warpedit_permission"))) {
                    if (args.length == 2) {

                        List<Integer> ids = new ArrayList<Integer>();

                        for (String i : warpList.getKeys(false)) {
                            ids.add(parseInt(i.split("_")[1]));

                            if (warpList.get(i + ".name").toString().equalsIgnoreCase(args[0])) {
                                p.sendMessage(config.getString("messages.setwarp_error_warp_exists"));
                                return true;

                            }
                        }

                        String newId = "warp_0";
                        if (warpList.getKeys(false).size() != 0) {
                            newId = "warp_" + (Collections.max(ids, null) + 1);
                        }

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
                        try {
                            warpList.save(Main.getPlugin().getDataFolder() + File.separator + "warps.yml");
                            p.sendMessage(config.getString("messages.setwarp_success"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }



                    } else {
                        p.sendMessage(config.getString("messages.setwarp_error_usage"));
                    }
                } else {
                    p.sendMessage(config.getString("messages.no_permission"));
                }
            } else {
                Bukkit.getLogger().log(Level.SEVERE, config.getString("messages.explain_to_console_why_you_cant_warp_a_console"));
            }
        }
        return false;
    }
}
