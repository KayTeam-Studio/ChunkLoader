package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;

public class Command_Help {

    public void chunkHelp(Player player){
        ChunkLoader.messages.sendMessage(player, "chunkloader.help");
    }
}
