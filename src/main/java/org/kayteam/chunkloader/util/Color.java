package org.kayteam.chunkloader.util;

import org.bukkit.ChatColor;
import org.kayteam.chunkloader.main.ChunkLoader;

public class Color {
    private ChunkLoader plugin;
    public void Color(ChunkLoader plugin){
        this.plugin = plugin;
    }
    public static String convert(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}
