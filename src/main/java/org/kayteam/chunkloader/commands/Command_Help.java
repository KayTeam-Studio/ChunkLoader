package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.util.Send;

import java.util.List;

public class Command_Help {
    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public void chunkHelp(Player player){
        List<String> helpList = plugin.messages.getStringList("chunkloader.help");
        Send.playerList(player,helpList);
    }
}
