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
import java.util.logging.Level;


public class CMD_DelWarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("delwarp")) {
            FileConfiguration warpList = Main.getPlugin().warpList;
            FileConfiguration config = Main.getPlugin().config;
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission(config.getString("permission.warpedit_permission"))) {
                    if (args.length == 1) {
                        for (String i : warpList.getKeys(false)) {
                            if (warpList.getString(i + ".name").equalsIgnoreCase(args[0])) {
                                warpList.set(i, null);
                                try {
                                    warpList.save(Main.getPlugin().getDataFolder() + File.separator + "warps.yml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                p.sendMessage(config.getString("messages.delwarp_success"));
                                return true;
                            }
                        }
                        p.sendMessage(config.getString("messages.delwarp_error_warp_not_exists"));

                    } else {
                        p.sendMessage(config.getString("messages.delwarp_error_usage"));
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


