package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_DelHome implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("delhome")) {
            if (sender instanceof Player p) {

                if (p.hasPermission("alpsbte.home")) {
                    if (args.length == 1) {
                        if (Home.exists(args[0], p.getUniqueId().toString())) {
                            Home.removeHome(p.getUniqueId().toString(), args[0]);
                            p.sendMessage(Utils.getInfoMessageFormat("Successfully removed home " + args[0] + "!"));
                        } else {
                            p.sendMessage(Utils.getErrorMessageFormat("Could not find home " + args[0] + "!"));
                        }
                    } else {
                        p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /delhome <name>"));
                    }
                } else {
                    p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
                }
            }
        }
        return true;
    }
}
