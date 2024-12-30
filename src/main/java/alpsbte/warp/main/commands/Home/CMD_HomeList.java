package alpsbte.warp.main.commands.Home;

import alpsbte.warp.main.core.system.Home;
import alpsbte.warp.main.utils.Utils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CMD_HomeList implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("homes")) {
            if (sender instanceof Player p) {
                if (p.hasPermission("alpsbte.home")) {
                    List<Home> homeList = Home.getHomeList(p.getUniqueId().toString());
                    if (homeList.isEmpty()) {
                        p.sendMessage(Utils.getErrorMessageFormat("You dont have have any homes! Set some with /sethome <name>"));
                        return true;
                    }

                    p.sendMessage("§8--------------------------");
                    for (Home home : homeList) {
                        TextComponent tc = new TextComponent();
                        tc.setText(Utils.getInfoMessageFormat(home.getName()));
                        tc.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND,"/home " + home.getName()));
                        tc.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Click to teleport...").create()));
                        p.spigot().sendMessage(tc);
                    }
                    p.sendMessage("§8--------------------------");
                } else {
                    p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
                }
            }
        }
        return true;
    }
}
