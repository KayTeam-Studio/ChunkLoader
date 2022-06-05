package org.kayteam.chunkloader.util;

import org.bukkit.ChatColor;
import org.kayteam.chunkloader.main.MiPlugin;

import java.util.ArrayList;
import java.util.List;

public class Color {
    private MiPlugin plugin;
    public void Color(MiPlugin plugin){
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
