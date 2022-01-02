package alpsbte.warp.main.commands.Warp;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.logging.Level;

public class CMD_DelWarpPlate implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("delwarpplate")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("alpsbte.moderator")) {
                    if (args.length == 1) {
                        if (Warp.exists(args[0])) {
                            // Remove Warp Plate from database
                            Warp warp = new Warp(args[0]);
                            if(Main.getWarpPlateList().get(warp.getPlateLocation()) != null) {
                                // Remove from plate list
                                Main.getWarpPlateList().remove(warp.getPlateLocation());


                                // Set Blocks
                                for (int i = 0; i < 7; i++) {
                                    Block block = p.getWorld().getBlockAt(warp.getPlateLocation().add(0,i,0));
                                    block.setType(Material.AIR);
                                }

                                // Get HologramLocation
                                Location hologramLocation = new Location(
                                        warp.getLocation().getWorld(),
                                        Math.floor(warp.getPlateLocation().getX()) + 0.5,
                                        Math.floor(warp.getPlateLocation().getY()) + 1.5,
                                        Math.floor(warp.getPlateLocation().getZ()) + 0.5);

                                //Remove Holograms
                                if (HologramsAPI.getHolograms(Main.getPlugin()).stream().anyMatch(h -> h.getLocation().equals(hologramLocation))) {
                                    Objects.requireNonNull(HologramsAPI.getHolograms(Main.getPlugin()).stream()
                                            .filter(h -> h.getLocation().equals(hologramLocation))
                                            .findFirst().orElse(null)).delete();
                                }

                                // Remove from database
                                warp.removeWarpPlate();

                                p.sendMessage(Utils.getInfoMessageFormat("Successfully removed warp plate for warp " + warp.getName() + "!"));
                            } else {
                                p.sendMessage(Utils.getErrorMessageFormat("Could not find warp plate for warp " + warp.getName() + "!"));
                            }
                        } else {
                            p.sendMessage(Utils.getErrorMessageFormat("Could not find warp " + args[0] + "!"));
                        }
                    } else {
                        p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /delwarpplate <name>"));
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
