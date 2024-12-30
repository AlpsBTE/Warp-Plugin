package alpsbte.warp.main.commands.Warp;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_SetWarp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.moderator")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /setwarp <name>"));
            return true;
        }

        if (Warp.exists(args[0])) {
            p.sendMessage(Utils.getErrorMessageFormat("Warp already exists!"));
            return true;
        }

        Warp.addWarp(args[0], p.getLocation());
        Main.getWarpTabCompletionList().add(args[0]);
        p.sendMessage(Utils.getInfoMessageFormat("Successfully added warp with name " + args[0]));
        return true;
    }
}
