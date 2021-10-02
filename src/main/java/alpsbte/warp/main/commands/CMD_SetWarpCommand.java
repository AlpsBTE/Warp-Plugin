package alpsbte.warp.main.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_SetWarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("setwarp")) {
            Player p = (Player) sender;
            if (p.hasPermission("alpsbte.moderator")) {
                p.sendMessage("You are better then the rest of the world");
            }
        }

        return false;
    }
}
