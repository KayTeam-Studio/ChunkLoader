package org.kayteam.chunkloader.commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

import java.util.ArrayList;
import java.util.List;

public class Command_List {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public void chunkList(Player player){
        ChunkManager chunkManager = plugin.getChunkManager();
        FileConfiguration messages = plugin.messages.getFile();
        FileConfiguration data = plugin.data.getFile();

        data.getStringList("chunks-list");
        List<String> chunkList = new ArrayList<String>();
        for(String key : data.getStringList("chunks-list")){
            int chunkLocationX = Integer.parseInt(chunkManager.formatChunk(key)[0]);
            int chunkLocationZ = Integer.parseInt(chunkManager.formatChunk(key)[1]);
            String chunkLocationWorld = chunkManager.formatChunk(key)[2];

            String chunkCoords = "X: "+chunkLocationX+"; Z: "+chunkLocationZ;

            chunkList.add(messages.getString("chunkloader.list")
                    .replaceAll("%chunk_coords%",chunkCoords)
                    .replaceAll("%chunk_world%",chunkLocationWorld));
        }
        for (int i = 0; i < chunkList.size(); i++) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7(&l"+(i+1)+"&7)"+chunkList.get(i)));
        }
    }
}
