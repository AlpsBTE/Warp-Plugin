package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_Home implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.home")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /home <name>"));
            return true;
        }

        if (!Home.exists(args[0], p.getUniqueId().toString())) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not find the specified home!"));
            return true;
        }

        // Get home
        Home home = new Home(args[0], p.getUniqueId().toString());
        if (home.getLocation().getWorld() == null) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not teleport to this home! This server is currently unavailable!"));
            return true;
        }

        // Teleport
        p.teleport(home.getLocation());
        p.sendMessage(Utils.getInfoMessageFormat("Teleported to " + home.getName()));
        p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1, 1);
        return true;
    }
}
