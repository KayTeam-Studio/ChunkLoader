package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

public class Command_Off {

    private final ChunkLoader plugin;

    public Command_Off(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public void disableChunkLoad(Player player){
        ChunkManager chunkManager = plugin.getChunkManager();
        if(plugin.config.getBoolean("chunk-load")){
            plugin.messages.sendMessage(player, "chunkloader.disabled");
            chunkManager.disableChunkLoad();
        }else{
            plugin.messages.sendMessage(player, "chunkloader.already-disabled");
        }
    }
}
