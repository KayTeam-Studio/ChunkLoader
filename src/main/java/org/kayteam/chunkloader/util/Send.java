package org.kayteam.chunkloader.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;

import java.util.List;

public class Send {

    private static ChunkLoader plugin;
    public Send(ChunkLoader plugin) {
        this.plugin = plugin;
    }
    public static void playerMessage(Player player, String message){
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',message));
    }

    public static void insufficientArgs(Player player){
        FileConfiguration messages = plugin.messages.getFile();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',messages.getString("insufficientArgs")));
    }

    public static void Console(String message) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void playerList(Player player, List<String> message) {
        for (int i = 0; i < message.size(); i++) {
            String messageSend = ChatColor.translateAlternateColorCodes('&',message.get(i));
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageSend));
        }
    }
}
