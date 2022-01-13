package alpsbte.warp.main.core;

import alpsbte.warp.main.Main;
import alpsbte.warp.main.core.system.Home;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> suggestionList = new ArrayList<>();
        Player p;
        if (commandSender instanceof Player) {
            p = (Player)commandSender;
        } else { return null; }

        if (command.getName().equals("warp") && strings.length == 1) {
            suggestionList = Main.getWarpTabCompletionList();
            StringUtil.copyPartialMatches(strings[0], suggestionList, suggestionList);
            Collections.sort(suggestionList);
        } else if (command.getName().equals("home") && strings.length <= 1) {
            for (Home home : Home.getHomeList(p.getUniqueId().toString())) {
                suggestionList.add(home.getName());
            }
        }
        return suggestionList;
    }
}
