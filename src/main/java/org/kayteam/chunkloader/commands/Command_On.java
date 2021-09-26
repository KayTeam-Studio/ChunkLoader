package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

public class Command_On {

    private final ChunkLoader plugin;

    public Command_On(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public void enableChunkLoad(Player player){
        ChunkManager chunkManager = plugin.getChunkManager();
        if(plugin.config.getBoolean("chunk-load")){
            plugin.messages.sendMessage(player, "chunkloader.already-enabled");
        }else{
            plugin.config.set("chunk-load",true);
            plugin.messages.sendMessage(player, "chunkloader.enabled");
            chunkManager.enableChunkLoad();
        }
    }
}
