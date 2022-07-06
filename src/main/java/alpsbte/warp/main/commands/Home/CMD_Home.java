package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.utils.Utils;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_Home implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("home")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;

                if (p.hasPermission("alpsbte.home")) {
                    if (args.length == 1) {
                        if (Home.exists(args[0],p.getUniqueId().toString())) {
                            // Get home
                            Home home = new Home(args[0],p.getUniqueId().toString());

                            // Teleport
                            p.teleport(home.getLocation());
                            p.sendMessage(Utils.getInfoMessageFormat("Teleported to " + home.getName()));
                            p.playSound(p.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.MASTER, 1,1);
                        } else {
                            p.sendMessage(Utils.getErrorMessageFormat("Could not find the specified home!"));
                        }
                    } else {
                        p.sendMessage(Utils.getErrorMessageFormat("Incorrect input! Try /home <name>"));
                    }
                } else {
                    p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
                }
            }
        }
        return true;
    }
}
