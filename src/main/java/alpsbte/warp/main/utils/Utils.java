package alpsbte.warp.main.utils;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utils {
    // Head Database API
    public static HeadDatabaseAPI headDatabaseAPI;

    public static ItemStack getItemHead(String headID) {
        return headDatabaseAPI != null && headID != null ? headDatabaseAPI.getItemHead(headID) : new ItemBuilder(Material.SKELETON_SKULL, 1, (byte) 3).build();
    }

    // Player Messages
    private static final String messagePrefix = "\uE13B §7§l» ";

    public static String getInfoMessageFormat(String info) {
        return "\uE13B §8» §a" + info;
    }

    public static String getErrorMessageFormat(String error) {
        return "\uE13A §8» §c" + error;
    }
}
