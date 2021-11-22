package alpsbte.warp.main.commands;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Warp;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.logging.Level;

public class CMD_Warp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("warp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("alpsbte.warp")) {
                    if (args.length == 1) {
                        if (Warp.exists(args[0])) {
                            // Get warp
                            Warp warp = new Warp(args[0]);

                            // Set Location
                            Location warpLocation = new Location(
                                    warp.getWorld(),
                                    warp.getX(),
                                    warp.getY(),
                                    warp.getZ(),
                                    warp.getYaw(),
                                    warp.getPitch());

                            // Teleport
                            p.teleport(warpLocation);
                            p.sendMessage(Utils.getInfoMessageFormat("Welcome to " + warp.getName()));
                            p.playSound(p.getLocation(),Sound.ENTITY_ENDERMEN_TELEPORT, SoundCategory.MASTER, 1,1);
                            p.sendTitle("§5§l" + warp.getName(), "§7Welcome!", 5, 40, 10);
                        } else {
                            p.sendMessage(Utils.getErrorMessageFormat("Could not find the specified warp!"));
                        }
                    } else {
                        p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /warp <name>"));
                    }
                } else {
                    p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
                }
            }  else {
                // fine i'll leave this here @Hohi...
                YamlConfiguration config = Main.getPlugin().config;
                Bukkit.getLogger().log(Level.SEVERE, config.getString("messages.explain_to_console_why_you_cant_warp_a_console"));
            }
        }
        return true;
    }
}
