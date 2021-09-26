package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.main.ChunkLoader;

public class Command_Help {

    private final ChunkLoader plugin;

    public Command_Help(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public void chunkHelp(Player player){
        plugin.messages.sendMessage(player, "chunkloader.help");
    }
}
