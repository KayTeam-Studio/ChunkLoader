package org.kayteam.chunkloader.commands;

import org.bukkit.entity.Player;
import org.kayteam.chunkloader.ChunkLoader;

public class Command_Reload {

    public void commandReload(Player player) {
        ChunkLoader.data.reload();
        ChunkLoader.messages.reload();
        ChunkLoader.config.reload();
        ChunkLoader.messages.sendMessage(player, "chunkloader.reload");
    }
}
