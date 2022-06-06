package org.kayteam.chunkloader.commands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.main.ChunkLoader;
import org.kayteam.chunkloader.main.ChunkManager;

public class Command_AddChunkRegion implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(sender instanceof Player){
            try {
            ChunkManager chunkManager = ChunkLoader.getChunkManager();
            Player player = (Player) sender;
            if(!player.hasPermission("chunkloader.addchunkregion")){
                ChunkLoader.messages.sendMessage(player, "command.no-permission");
                return false;
            }
            BukkitPlayer bukkitPlayer = BukkitAdapter.adapt(player);
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(bukkitPlayer);
            final Region selection = session.getSelection(bukkitPlayer.getWorld());
            
            } catch (IncompleteRegionException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
