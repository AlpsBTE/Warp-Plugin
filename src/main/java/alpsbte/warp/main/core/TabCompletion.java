package alpsbte.warp.main.core;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String @NotNull [] args) {
        if (!(sender instanceof Player player)) return null;

        final List<String> completions = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("warp") && args.length <= 1) {
            StringUtil.copyPartialMatches(args[0], Main.getWarpTabCompletionList(), completions);
            Collections.sort(completions);
        } else if (command.getName().equalsIgnoreCase("home") && args.length <= 1) {
            List<String> homes = new ArrayList<>();
            Home.getHomeList(player.getUniqueId().toString()).forEach(home -> homes.add(home.getName()));
            StringUtil.copyPartialMatches(args[0], homes, completions);
        }
        return completions;
    }
}
