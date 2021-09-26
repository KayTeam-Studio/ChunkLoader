package org.kayteam.chunkloader.commands;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;

import java.util.List;

public class Command_TP {
    private final ChunkLoader plugin;

    public Command_TP(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public void chunkTeleport(Player player, int chunkIndex){
        List<Chunk> chunkList = plugin.getChunkManager().getChunkList();
            try{
                Chunk chunk = chunkList.get(chunkIndex);
                double chunkLocationX = chunk.getX()*16+8;
                double chunkLocationZ = chunk.getZ()*16+8;
                World chunkLocationWorld = chunk.getWorld();

                String chunkCoords = "X: "+chunk.getX()+"; Z:"+chunk.getZ();

                Location chunkLocation = new Location(
                        chunkLocationWorld,chunkLocationX,chunkLocationWorld
                        .getHighestBlockYAt((int) chunkLocationX, (int) chunkLocationZ)+1,chunkLocationZ);

                plugin.messages.sendMessage(player, "chunkloader.teleport", new String[][]{
                        {"%chunk_coords%",chunkCoords},
                        {"%chunk_world%",chunkLocationWorld.getName()}
                });

                player.teleport(chunkLocation);
            }catch (Exception e){
                plugin.messages.sendMessage(player, "chunkloader.teleport-invalid");
            }
    }
}
