package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CMD_SetHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("sethome")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("alpsbte.home")) {
                    if (args.length == 1) {
                        if (!Home.exists(args[0],p.getUniqueId().toString())) {
                            // Check if home limit has been reached
                            if (Home.homeCount(p.getUniqueId().toString()) >= Main.getPlugin().getConfig().getInt("homes.limit")) {
                                p.sendMessage(Utils.getErrorMessageFormat("You already hit your homes limit! Try removing some first."));
                                return true;
                            }

                            // Add home
                            Home.addHome(p.getUniqueId().toString(), args[0], p.getLocation());
                            p.sendMessage(Utils.getInfoMessageFormat("Successfully added home with name " + args[0]));
                        } else {
                            p.sendMessage(Utils.getErrorMessageFormat("This home already exists!"));
                        }
                    } else {
                        p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /sethome <name>"));
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
