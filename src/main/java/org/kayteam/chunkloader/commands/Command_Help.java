package org.kayteam.chunkloader.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

import java.util.List;

public class Command_Help {
    private ChunkLoader plugin;

    public Command_Help(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public void chunkHelp(Player player){
        FileConfiguration messages = plugin.messages.getFile();
        List<String> helpList = messages.getStringList("chunkloader.help");
        Send.playerList(player,helpList);
    }
}
