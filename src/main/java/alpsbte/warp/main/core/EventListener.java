package alpsbte.warp.main.core;

import alpsbte.warp.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if interacted block is gold pressure plate
        if (event.getAction() == Action.PHYSICAL) {
            if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {
                Location blockLocation = event.getClickedBlock().getLocation();

                //Check Location
                if(Main.getWarpPlateList().size() != 0) {
                    for (Location location : Main.getWarpPlateList().keySet()) {
                        if (Math.floor(blockLocation.getBlockX()) == Math.floor(location.getBlockX()) &&
                            Math.floor(blockLocation.getBlockY()) == Math.floor(location.getBlockY()) &&
                            Math.floor(blockLocation.getBlockZ()) == Math.floor(location.getBlockZ())) {
                            event.getPlayer().performCommand("warp " + Main.getWarpPlateList().get(location));
                            break;
                        }
                    }
                }
            }
        }
    }
}
