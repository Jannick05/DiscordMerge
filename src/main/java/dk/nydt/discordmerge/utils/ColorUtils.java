package dk.nydt.discordmerge.utils;

import org.bukkit.ChatColor;

public class ColorUtils {
    public static String getColor(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
