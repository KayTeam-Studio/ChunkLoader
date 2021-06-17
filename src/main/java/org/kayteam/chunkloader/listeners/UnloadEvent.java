package org.kayteam.chunkloader.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.kayteam.chunkloader.main.ChunkLoader;

public class UnloadEvent implements Listener {

    ChunkLoader plugin = ChunkLoader.getChunkLoader();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUnload(ChunkUnloadEvent event){
        if(plugin.getChunkList().contains(plugin.formatChunkString(event.getChunk()))){
            if(plugin.isChunkLoadEnable()){
                plugin.loadChunk(event.getChunk());
            }
        }
    }
}
