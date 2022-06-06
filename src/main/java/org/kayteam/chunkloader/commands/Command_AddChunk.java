package org.kayteam.chunkloader.commands;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.api.simple.yaml.SimpleYaml;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.chunkloader.util.PermissionChecker;

public class Command_AddChunk implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(PermissionChecker.check(player, "chunkloader.addchunk")){
                ChunkManager chunkManager = ChunkLoader.getChunkManager();
                SimpleYaml data = ChunkLoader.data;
                Location playerLocation = player.getLocation();
                Chunk chunkLocation = playerLocation.getChunk();
                String chunkCoords = "X: "+chunkLocation.getX()+"; Z: "+chunkLocation.getZ();
                String chunkFormated = chunkManager.formatStringChunk(chunkLocation);

                if(!data.getStringList("chunks-list").contains(chunkFormated)){
                    chunkManager.addChunk(chunkLocation);

                    ChunkLoader.messages.sendMessage(player, "addchunk.correct", new String[][]{
                            {"%chunk_coords%", chunkCoords}
                    });
                }else{
                    ChunkLoader.messages.sendMessage(player, "addchunk.exist", new String[][]{
                            {"%chunk_coords%", chunkCoords}
                    });
                }
            }
        }
        return false;
    }
}
