package org.kayteam.chunkloader.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

public class Command_On {
    private ChunkLoader plugin;

    public Command_On(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public void enableChunkLoad(Player player){
        FileConfiguration messages = this.plugin.messages.getFile();
        FileConfiguration config = plugin.config.getFile();
        if(config.getBoolean("enable-load-on-start")){
            Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.enable-already"));
        }else{
            config.set("enable-load-on-start",true);
            Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.enable"));
            plugin.enableChunkLoad();
            plugin.config.saveFile();
        }
    }
}
