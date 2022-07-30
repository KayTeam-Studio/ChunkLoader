package org.kayteam.chunkloader.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.chunkloader.chunk.ChunkManager;

import java.util.ArrayList;
import java.util.List;

public class Command_List {

    public void chunkList(Player player){
        ChunkManager chunkManager = ChunkLoader.getChunkManager();

        List<String> chunkList = new ArrayList<>();
        for(Chunk chunk : ChunkLoader.getChunkManager().getChunkList()){
            int chunkLocationX = chunk.getX();
            int chunkLocationZ = chunk.getZ();
            String chunkLocationWorld = chunk.getWorld().getName();

            String chunkCoords = "X: "+chunkLocationX+"; Z: "+chunkLocationZ;

            chunkList.add(ChunkLoader.messages.getString("chunkloader.list", new String[][]{
                    {"%chunk_coords%", chunkCoords},
                    {"%chunk_world%", chunkLocationWorld}
                })
            );
        }
        for (int i = 0; i < chunkList.size(); i++) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7(&l"+(i)+"&7)"+chunkList.get(i)));
        }
    }
}
