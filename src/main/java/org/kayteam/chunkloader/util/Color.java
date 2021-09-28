package org.kayteam.chunkloader.util;

import org.bukkit.ChatColor;
import org.kayteam.chunkloader.main.ChunkLoader;

import java.util.ArrayList;
import java.util.List;

public class Color {
    private ChunkLoader plugin;
    public void Color(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public static String convert(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }

    public static List<String> convert(List<String> message){
        List<String> messageConverted = new ArrayList<>();
        for(String line : message){
            messageConverted.add(convert(line));
        }
        return messageConverted;
    }
}
