package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.chunkloader.util.Send;

public class Command_On {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public void enableChunkLoad(Player player){
        ChunkManager chunkManager = plugin.getChunkManager();
        if(plugin.config.getBoolean("chunk-load")){
            Send.playerMessage(player,plugin.prefix+plugin.messages.getString("chunkloader.already-enabled"));
        }else{
            plugin.config.set("chunk-load",true);
            Send.playerMessage(player,plugin.prefix+plugin.messages.getString("chunkloader.enabled"));
            chunkManager.enableChunkLoad();
        }
    }
}
