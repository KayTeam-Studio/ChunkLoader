package org.kayteam.chunkloader.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

import java.util.List;

public class Command_TP {
    private ChunkLoader plugin;

    public Command_TP(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public void chunkTeleport(Player player, String chunkNumber){
        FileConfiguration data = plugin.data.getFile();
        List<String> chunkList = data.getStringList("chunks-list");
        FileConfiguration messages = plugin.messages.getFile();
            try{
                int chunkListNumber = Integer.valueOf(chunkNumber)-1;
                String chunk = chunkList.get(chunkListNumber);
                double chunkLocationX = Integer.parseInt(plugin.formatChunk(chunk)[0])*16+8;
                double chunkLocationZ = Integer.parseInt(plugin.formatChunk(chunk)[1])*16+8;
                World chunkLocationWorld = plugin.getServer().getWorld(plugin.formatChunk(chunk)[2]);

                String chunkCoords = "X: "+data.getInt(chunkList.get(chunkListNumber)+".x")+
                        "; Z:"+data.getInt(chunkList.get(chunkListNumber)+".z");

                Location chunkLocation = new Location(chunkLocationWorld,chunkLocationX,
                        chunkLocationWorld.getHighestBlockYAt(data.getInt(chunkList.get(chunkListNumber)+".x")*16,
                                data.getInt(chunkList.get(chunkListNumber)+".z")*16),
                        chunkLocationZ);

                Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.teleport")
                        .replaceAll("%chunk_coords%",chunkCoords)
                        .replaceAll("%chunk_world%",chunkLocationWorld.getName()));

                player.teleport(chunkLocation);
            }catch (Exception e){
                Send.playerMessage(player, plugin.prefix+messages.getString("chunkloader.teleport-invalid"));
            }
    }
}
