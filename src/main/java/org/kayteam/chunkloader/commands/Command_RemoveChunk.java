package org.kayteam.chunkloader.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.ChunkLoader;

public class Command_RemoveChunk implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (!player.hasPermission("chunkloader.removechunk")) {
                ChunkLoader.messages.sendMessage(player, "command.no-permissions");
                return false;
            }
            ChunkLoader.getChunkManager().deleteChunk(player.getLocation().getChunk(), player);
        }
        return false;
    }
}
