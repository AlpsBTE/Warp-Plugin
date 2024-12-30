package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_DelHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.home")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /delhome <name>"));
            return true;
        }

        if (!Home.exists(args[0], p.getUniqueId().toString())) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not find home " + args[0] + "!"));
            return true;
        }

        Home.removeHome(p.getUniqueId().toString(), args[0]);
        p.sendMessage(Utils.getInfoMessageFormat("Successfully removed home " + args[0] + "!"));
        return true;
    }
}
