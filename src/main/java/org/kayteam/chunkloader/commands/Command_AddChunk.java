package org.kayteam.chunkloader.commands;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

import java.util.List;

public class Command_AddChunk implements CommandExecutor {
    private ChunkLoader plugin;

    public Command_AddChunk(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            FileConfiguration data = plugin.data.getFile();
            FileConfiguration messages = plugin.messages.getFile();
            Location playerLocation = player.getLocation();
            Chunk chunkLocation = playerLocation.getChunk();
            String chunkCoords = "X: "+chunkLocation.getX()+"; Z:"+chunkLocation.getZ();
            String chunkFormated = plugin.formatChunkString(chunkLocation);

            if(!data.getStringList("chunks-list").contains(chunkFormated)){
                List<String> chunkList = data.getStringList("chunks-list");
                chunkList.add(chunkFormated);
                data.set("chunks-list", chunkList);
                plugin.getChunkList().add(chunkFormated);
                plugin.data.saveFile();

                chunkLocation.setForceLoaded(true);

                plugin.loadChunks();

                Send.playerMessage(player, plugin.prefix+messages.getString("addchunk.correct")
                        .replaceAll("%chunk_coords%",chunkCoords));
            }else{
                Send.playerMessage(player, plugin.prefix+messages.getString("addchunk.exist")
                        .replaceAll("%chunk_coords%",chunkCoords));
            }
        }
        return false;
    }
}
