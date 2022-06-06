package org.kayteam.chunkloader.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Color {

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
