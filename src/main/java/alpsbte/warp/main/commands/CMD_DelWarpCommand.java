package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static java.lang.Integer.parseInt;


public class CMD_DelWarpCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("delwarp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                FileConfiguration warpList = Main.getPlugin().warpList;
                FileConfiguration config = Main.getPlugin().config;

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
                                p.sendMessage(config.getString("messages.setwarp_dupe")); // TODO
                                return true;
                            }
                        }
                        p.sendMessage(config.getString("messages.delwarp_error_warp_not_exists"));

                    } else {
                        p.sendMessage(config.getString("messages.delwarp_usage"));
                    }
                } else {
                    p.sendMessage(config.getString("message.no_permission"));
                }

            }
        }
        return false;
    }

}


