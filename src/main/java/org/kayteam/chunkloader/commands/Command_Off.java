package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.MiPlugin;
import org.kayteam.chunkloader.main.ChunkManager;

public class Command_Off {

    private final MiPlugin plugin;

    public Command_Off(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public void disableChunkLoad(Player player){
        ChunkManager chunkManager = plugin.getChunkManager();
        if(plugin.config.getBoolean("chunk-load")){
            plugin.messages.sendMessage(player, "chunkloader.disabled");
            chunkManager.disableChunkLoad();
        }else{
            plugin.messages.sendMessage(player, "chunkloader.already-disabled");
        }
    }
}
