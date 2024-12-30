package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_SetHome implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.home")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /sethome <name>"));
            return true;
        }

        if (Home.exists(args[0],p.getUniqueId().toString())) {
            p.sendMessage(Utils.getErrorMessageFormat("This home already exists!"));
            return true;
        }

        // Check if home limit has been reached
        if (Home.homeCount(p.getUniqueId().toString()) >= Main.getPlugin().getConfig().getInt("homes.limit")) {
            p.sendMessage(Utils.getErrorMessageFormat("You already hit your homes limit! Try removing some first."));
            return true;
        }

        // Add home
        Home.addHome(p.getUniqueId().toString(), args[0], p.getLocation());
        p.sendMessage(Utils.getInfoMessageFormat("Successfully added home with name " + args[0]));
        return true;
    }
}
