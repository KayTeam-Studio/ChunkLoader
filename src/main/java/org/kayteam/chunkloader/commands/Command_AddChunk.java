package org.kayteam.chunkloader.commands;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.main.MiPlugin;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.List;

public class Command_AddChunk implements CommandExecutor {

    private final MiPlugin plugin;

    public Command_AddChunk(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if(sender instanceof Player){
            ChunkManager chunkManager = plugin.getChunkManager();
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.addchunk")){
                plugin.messages.sendMessage(player, "command.no-permissions");
                return false;
            }
            Yaml data = plugin.data;
            Location playerLocation = player.getLocation();
            Chunk chunkLocation = playerLocation.getChunk();
            String chunkCoords = "X: "+chunkLocation.getX()+"; Z: "+chunkLocation.getZ();
            String chunkFormated = chunkManager.formatStringChunk(chunkLocation);

            if(!data.getStringList("chunks-list").contains(chunkFormated)){
                List<String> chunkList = data.getStringList("chunks-list");
                chunkList.add(chunkFormated);
                data.set("chunks-list", chunkList);
                plugin.data.saveFileConfiguration();
                chunkManager.getChunkStringList().add(chunkFormated);
                chunkManager.getChunkList().add(player.getLocation().getChunk());

                try{
                    chunkLocation.setForceLoaded(true);
                }catch (NoSuchMethodError e){
                    chunkLocation.load();
                }

                plugin.messages.sendMessage(player, "addchunk.correct", new String[][]{
                        {"%chunk_coords%", chunkCoords}
                });
            }else{
                plugin.messages.sendMessage(player, "addchunk.exist", new String[][]{
                        {"%chunk_coords%", chunkCoords}
                });
            }
        }
        return false;
    }
}
