package org.kayteam.chunkloader.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.main.ChunkLoader;

public class Command_RemoveChunk implements CommandExecutor {

    private final ChunkLoader plugin;

    public Command_RemoveChunk(ChunkLoader plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.removechunk ")){
                plugin.messages.sendMessage(player, "command.no-permissions");
                return false;
            }
            plugin.getChunkManager().deleteChunk(player.getLocation().getChunk(), player);
        }
        return false;
    }
}
