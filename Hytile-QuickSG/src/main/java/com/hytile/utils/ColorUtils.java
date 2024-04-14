package com.hytile.utils;


import org.bukkit.ChatColor;

public class ColorUtils {

    public static String translatedMessage(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
