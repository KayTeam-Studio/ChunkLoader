package org.kayteam.chunkloader.commands;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

public class Command_Off {
    private ChunkLoader plugin;

    public Command_Off(ChunkLoader plugin){
        this.plugin = plugin;
    }

    public void disableChunkLoad(Player player){

        FileConfiguration messages = this.plugin.messages.getFile();
        FileConfiguration config = this.plugin.config.getFile();
        if(config.getBoolean("enable-load-on-start")){
            Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.disable"));

            plugin.disableChunkLoad();

            plugin.config.getFile().set("enable-load-on-start",false);
            plugin.config.saveFile();
        }else{

            Send.playerMessage(player,plugin.prefix+messages.getString("chunkloader.disable-already"));
        }
    }
}
