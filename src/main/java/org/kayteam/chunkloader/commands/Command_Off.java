package org.kayteam.chunkloader.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;
import org.kayteam.chunkloader.util.Send;

public class Command_Off {

    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public void disableChunkLoad(Player player){
        ChunkManager chunkManager = plugin.getChunkManager();
        FileConfiguration messages = this.plugin.messages.getFile();
        FileConfiguration config = this.plugin.config.getFile();
        if(config.getBoolean("chunk-load")){
            Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.disabled"));
            chunkManager.disableChunkLoad();
        }else{
            Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.already-disabled"));
        }
    }
}
