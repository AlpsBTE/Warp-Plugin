package alpsbte.warp.main.commands.Warp;

import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_Warp implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.warp")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /warp <name>"));
            return true;
        }

        if (!Warp.exists(args[0])) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not find the specified warp!"));
            return true;
        }

        // Get warp
        Warp warp = new Warp(args[0]);

        Location location = warp.getLocation();
        if (location.getWorld() == null) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not teleport to this warp! This server is currently unavailable!"));
            return true;
        }

        // Teleport
        p.teleport(warp.getLocation());
        p.sendMessage(Utils.getInfoMessageFormat("Welcome to " + warp.getName()));
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1, 1);
        p.sendTitle("§5§l" + warp.getName(), "§7Welcome!", 5, 40, 10);
        return true;
    }
}
