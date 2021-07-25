package org.kayteam.chunkloader.commands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

public class Command_RemoveChunk implements CommandExecutor {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.removechunk ")){
                Send.playerMessage(player, plugin.messages.getFile().getString("command.no-permissions"));
                return false;
            }
            FileConfiguration data = plugin.data.getFile();
            FileConfiguration messages = plugin.messages.getFile();
            Location playerLocation = player.getLocation();
            Chunk chunkLocation = playerLocation.getChunk();
            World chunkLocationWorld = chunkLocation.getWorld();
            int chunkLocationX = chunkLocation.getX();
            int chunkLocationZ = chunkLocation.getZ();

            String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;
            String dataChunkPath = chunkLocationWorld.getName()+chunkLocationX+chunkLocationZ;

            if(data.getConfigurationSection(dataChunkPath)!=null){
                data.set(dataChunkPath,null);
                plugin.data.saveFile();


                Bukkit.getWorld(chunkLocationWorld.getName()).setChunkForceLoaded(chunkLocationX,chunkLocationZ,false);

                Send.playerMessage(player,plugin.prefix+messages.getString("removechunk.correct")
                        .replaceAll("%chunk_coords%",chunkCoords));
            }else{

                Send.playerMessage(player,plugin.prefix+messages.getString("removechunk.inexist")
                        .replaceAll("%chunk_coords%",chunkCoords));
            }
        }
        return false;
    }
}
