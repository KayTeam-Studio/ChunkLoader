package org.kayteam.chunkloader.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.chunkloader.util.Send;

import java.util.List;

public class Command_TP {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public void chunkTeleport(Player player, String chunkNumber){
        ChunkManager chunkManager = plugin.getChunkManager();
        List<String> chunkList = plugin.data.getStringList("chunks-list");
            try{
                int chunkListNumber = Integer.valueOf(chunkNumber)-1;
                String chunk = chunkList.get(chunkListNumber);
                double chunkLocationX = Integer.parseInt(chunkManager.formatChunk(chunk)[0])*16+8;
                Double chunkLocationX_Integer = chunkLocationX;
                double chunkLocationZ = Integer.parseInt(chunkManager.formatChunk(chunk)[1])*16+8;
                Double chunkLocationZ_Integer = chunkLocationZ;
                World chunkLocationWorld = plugin.getServer().getWorld(chunkManager.formatChunk(chunk)[2]);

                String chunkCoords = "X: "+plugin.data.getInt(chunkList.get(chunkListNumber)+".x")+
                        "; Z:"+plugin.data.getInt(chunkList.get(chunkListNumber)+".z");

                Location chunkLocation = new Location(chunkLocationWorld,chunkLocationX,
                        chunkLocationWorld.getHighestBlockYAt(chunkLocationX_Integer.intValue(), chunkLocationZ_Integer.intValue())+1,
                        chunkLocationZ);

                Send.playerMessage(player,plugin.prefix+plugin.messages.getString("chunkloader.teleport")
                        .replaceAll("%chunk_coords%",chunkCoords)
                        .replaceAll("%chunk_world%",chunkLocationWorld.getName()));

                player.teleport(chunkLocation);
            }catch (Exception e){
                Send.playerMessage(player, plugin.prefix+plugin.messages.getString("chunkloader.teleport-invalid"));
            }
    }
}
