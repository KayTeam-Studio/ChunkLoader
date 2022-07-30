package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.ChunkLoader;

public class Command_Reload {

    public void commandReload(Player player){
        ChunkLoader.data.reloadYamlFile();
        ChunkLoader.messages.reloadYamlFile();
        ChunkLoader.config.reloadYamlFile();
        ChunkLoader.messages.sendMessage(player, "chunkloader.reload");
    }
}
