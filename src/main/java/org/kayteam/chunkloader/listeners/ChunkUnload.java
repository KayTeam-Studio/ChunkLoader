package org.kayteam.chunkloader.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

public class ChunkUnload implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUnload(ChunkUnloadEvent event){
        ChunkManager chunkManager = ChunkLoader.getChunkManager();
        if(chunkManager.getChunkStringList().contains(chunkManager.formatStringChunk(event.getChunk()))){
            if(chunkManager.isChunkLoad()){
                chunkManager.loadChunk(event.getChunk());
            }
        }
    }
}
