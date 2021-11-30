package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
                            if(Main.getWarpPlateList().containsValue(warp.getName())) {
                                warp.removeWarpPlate();

                                // Set Blocks to Air
                                Block[] blocks = new Block[7];
                                for (int i = 0; i < 7; i++) {
                                    blocks[i] = p.getWorld().getBlockAt(warp.getPlateLocation().add(0,i,0));
                                    blocks[i].setType(Material.AIR);
                                }

                                //Remove Holograms
                                if (Main.getHologramList().containsKey(warp.getName())) {
                                    Main.getHologramList().get(warp.getName()).delete();
                                    Main.getHologramList().remove(warp.getName());
                                }
                            } else {
                                p.sendMessage(Utils.getErrorMessageFormat("Could not find warp plate for warp " + warp.getName()));
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
