package org.kayteam.chunkloader.commands;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

public class Command_RemoveChunk implements CommandExecutor {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.removechunk ")){
                Send.playerMessage(player, plugin.messages.getString("command.no-permissions"));
                return false;
            }
            Location playerLocation = player.getLocation();
            Chunk chunkLocation = playerLocation.getChunk();
            World chunkLocationWorld = chunkLocation.getWorld();
            int chunkLocationX = chunkLocation.getX();
            int chunkLocationZ = chunkLocation.getZ();

            String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;
            String dataChunkPath = chunkLocationWorld.getName()+chunkLocationX+chunkLocationZ;

            if(plugin.data.getFileConfiguration().getConfigurationSection(dataChunkPath)!=null){
                plugin.data.set(dataChunkPath,null);
                plugin.data.saveFileConfiguration();


                Bukkit.getWorld(chunkLocationWorld.getName()).setChunkForceLoaded(chunkLocationX,chunkLocationZ,false);

                Send.playerMessage(player,plugin.prefix+plugin.messages.getString("removechunk.correct")
                        .replaceAll("%chunk_coords%",chunkCoords));
            }else{

                Send.playerMessage(player,plugin.prefix+plugin.messages.getString("removechunk.inexist")
                        .replaceAll("%chunk_coords%",chunkCoords));
            }
        }
        return false;
    }
}
