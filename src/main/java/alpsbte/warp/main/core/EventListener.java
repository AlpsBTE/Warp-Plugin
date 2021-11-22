package alpsbte.warp.main.core;

import alpsbte.warp.main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Check if interacted block is gold pressure plate
        if (event.getClickedBlock().getType() == Material.GOLD_PLATE) {
            Location blockLocation = event.getClickedBlock().getLocation();

            //Check Location
            if(Main.getWarpPlateList().size() != 0) {
                for (Location location : Main.getWarpPlateList().keySet()) {
                    if (blockLocation.distance(location) < 1.0) {
                        event.getPlayer().sendMessage("It works!");
                        break;
                    }
                }
            }
        }
    }
}
