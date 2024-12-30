package alpsbte.warp.main.commands.Warp;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CMD_DelWarpPlate implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.moderator")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

        if (args.length != 1) {
            p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /delwarpplate <name>"));
            return true;
        }

        if (!Warp.exists(args[0])) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not find warp " + args[0] + "!"));
            return true;
        }

        // Remove Warp Plate from database
        Warp warp = new Warp(args[0]);
        if (Main.getWarpPlateList().get(warp.getPlateLocation()) != null) {
            p.sendMessage(Utils.getErrorMessageFormat("Could not find warp plate for warp " + warp.getName() + "!"));
            return true;
        }

        // Remove from plate list
        Main.getWarpPlateList().remove(warp.getPlateLocation());

        // Set Blocks
        for (int i = 0; i < 7; i++) {
            Block block = p.getWorld().getBlockAt(warp.getPlateLocation().add(0, i, 0));
            block.setType(Material.AIR);
        }

        // Get HologramLocation
        Location hologramLocation = new Location(
                warp.getLocation().getWorld(),
                Math.floor(warp.getPlateLocation().getX()) + 0.5,
                Math.floor(warp.getPlateLocation().getY()) + 1.5,
                Math.floor(warp.getPlateLocation().getZ()) + 0.5);

        //Remove Holograms
        Hologram holo = DHAPI.getHologram("warp_" + args[0]
                .replaceAll("[^a-zA-Z0-9_-]", ""));
        if (holo != null) holo.delete();

        // Remove from database
        warp.removeWarpPlate();

        p.sendMessage(Utils.getInfoMessageFormat("Successfully removed warp plate for warp " + warp.getName() + "!"));
        return true;
    }
}
