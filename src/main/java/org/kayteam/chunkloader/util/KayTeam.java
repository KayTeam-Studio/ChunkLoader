package org.kayteam.chunkloader.util;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class KayTeam {

    public static void sendBrandMessage(JavaPlugin javaPlugin, String state){
        List<String> message = new ArrayList<>();
        message.add("");
        message.add("&f>>");
        message.add("&f>> &6" + javaPlugin.getDescription().getName()+" "+state);
        message.add("&f>> &6Version &f" + javaPlugin.getDescription().getVersion());
        message.add("&f>>");
        message.add("&f>> &6Developed by KayTeam © 2020 - " + Calendar.getInstance().get(Calendar.YEAR) + ". All rights reserved.");
        message.add("&f>> &f" + javaPlugin.getDescription().getWebsite());
        message.add("&f>>");
        message.add("");
        for(String line : message){
            javaPlugin.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', line));
        }
    }

}