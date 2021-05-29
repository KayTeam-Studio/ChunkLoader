package org.kayteam.chunkloader.commands;

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
            World chunkLocationWorld = chunkLocation.getWorld();
            int chunkLocationX = chunkLocation.getX();
            int chunkLocationZ = chunkLocation.getZ();
            String chunkLocationPath = chunkLocationWorld.getName()+chunkLocationX+chunkLocationZ;
            String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;

            if(data.getString(chunkLocationPath+".world")==null){
                data.set(chunkLocationPath+".x",chunkLocationX);
                data.set(chunkLocationPath+".z",chunkLocationZ);
                data.set(chunkLocationPath+".world",chunkLocationWorld.getName());
                plugin.data.saveFile();

                plugin.loadChunks(plugin);

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
