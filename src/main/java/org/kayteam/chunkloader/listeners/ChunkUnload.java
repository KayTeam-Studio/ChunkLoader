package org.kayteam.chunkloader.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

public class ChunkUnload implements Listener {

    private final ChunkLoader plugin;

    public ChunkUnload(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onUnload(ChunkUnloadEvent event){
        String[] chunk = new String[3];
        chunk[0] = String.valueOf(event.getChunk().getX());
        chunk[1] = String.valueOf(event.getChunk().getZ());
        chunk[2] = event.getChunk().getWorld().getName();
        ChunkManager chunkManager = plugin.getChunkManager();
        if(chunkManager.getChunkListStringSplit().contains(chunk)){
            if(chunkManager.isChunkLoad()){
                chunkManager.loadChunk(event.getChunk());
            }
        }
    }
}
