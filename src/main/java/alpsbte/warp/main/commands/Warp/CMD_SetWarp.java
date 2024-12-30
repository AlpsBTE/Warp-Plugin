package alpsbte.warp.main.commands.Warp;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CMD_SetWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("setwarp")) {
            if (sender instanceof Player p) {

                if (p.hasPermission("alpsbte.moderator")) {
                    if (args.length == 1) {
                        if (!Warp.exists(args[0])) {
                            Warp.addWarp(args[0], p.getLocation());
                            Main.getWarpTabCompletionList().add(args[0]);
                            p.sendMessage(Utils.getInfoMessageFormat("Successfully added warp with name " + args[0]));
                        } else {
                            p.sendMessage(Utils.getErrorMessageFormat("Warp already exists!"));
                        }
                    } else {
                        p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /setwarp <name>"));
                    }
                } else {
                    p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
                }
            } else {
                FileConfiguration config = Main.getPlugin().config;
                Bukkit.getLogger().log(Level.SEVERE, config.getString("messages.explain_to_console_why_you_cant_warp_a_console"));
            }
        }
        return true;
    }
}
