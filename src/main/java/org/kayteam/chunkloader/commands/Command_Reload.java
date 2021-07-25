package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

public class Command_Reload {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public void commandReload(Player player){
        plugin.data.reloadFile();
        plugin.messages.reloadFile();

        Send.playerMessage(player,plugin.prefix+plugin.messages.getFile().getString("chunkloader.reload"));
    }
}
