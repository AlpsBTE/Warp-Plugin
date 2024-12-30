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
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CMD_HomeList implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (!(sender instanceof Player p)) return true;

        if (!p.hasPermission("alpsbte.home")) {
            p.sendMessage(Utils.getErrorMessageFormat("No permission!"));
            return true;
        }

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
        return true;
    }
}
