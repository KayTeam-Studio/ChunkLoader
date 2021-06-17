package org.kayteam.chunkloader.commands;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

import java.util.ArrayList;
import java.util.List;

public class Command_List {
    private ChunkLoader plugin;

    public Command_List(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public void chunkList(Player player){
        FileConfiguration messages = plugin.messages.getFile();
        FileConfiguration data = plugin.data.getFile();

        if(data.getStringList("chunks-list")!=null){
            List<String> chunkList = new ArrayList<String>();
            for(String key : data.getStringList("chunks-list")){
                int chunkLocationX = Integer.parseInt(plugin.formatChunk(key)[0]);
                int chunkLocationZ = Integer.parseInt(plugin.formatChunk(key)[1]);
                String chunkLocationWorld = plugin.formatChunk(key)[2];

                String chunkCoords = "X: "+chunkLocationX+"; Z: "+chunkLocationZ;

                chunkList.add(messages.getString("chunkloader.list")
                        .replaceAll("%chunk_coords%",chunkCoords)
                        .replaceAll("%chunk_world%",chunkLocationWorld));
            }
            for (int i = 0; i < chunkList.size(); i++) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', " &7(&l"+(i+1)+"&7)"+chunkList.get(i)));
            }
        }else{
            Send.playerMessage(player, plugin.prefix+messages.getString("chunkloader.list-null"));
        }
    }
}
