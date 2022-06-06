package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

public class Command_On {

    public void enableChunkLoad(Player player){
        ChunkManager chunkManager = ChunkLoader.getChunkManager();
        if(ChunkLoader.config.getBoolean("chunk-load")){
            ChunkLoader.messages.sendMessage(player, "chunkloader.already-enabled");
        }else{
            ChunkLoader.config.set("chunk-load",true);
            ChunkLoader.messages.sendMessage(player, "chunkloader.enabled");
            chunkManager.enableChunkLoad();
        }
    }
}
