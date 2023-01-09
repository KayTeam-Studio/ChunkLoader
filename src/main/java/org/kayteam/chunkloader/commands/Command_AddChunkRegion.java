package org.kayteam.chunkloader.commands;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitPlayer;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.chunkloader.chunk.ChunkManager;

public class Command_AddChunkRegion implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (sender instanceof Player) {
            try {
                ChunkManager chunkManager = ChunkLoader.getChunkManager();
                Player player = (Player) sender;
                if (!player.hasPermission("chunkloader.addchunkregion")) {
                    ChunkLoader.messages.sendMessage(player, "command.no-permission");
                    return false;
                }
                if(!chunkManager.isWorldEdit()){
                    ChunkLoader.messages.sendMessage(player, "command.worldedit-disabled");
                    return false;
                }
                BukkitPlayer bPlayer = BukkitAdapter.adapt(player);
                LocalSession session = WorldEdit.getInstance().getSessionManager().get(bPlayer);
                final Region sel = session.getSelection(bPlayer.getWorld());
                BlockVector3 max = sel.getMaximumPoint();
                BlockVector3 min = sel.getMinimumPoint();
                Location maxPoint = new Location(player.getWorld(), max.getBlockX(), max.getBlockY(), max.getBlockZ());
                Location minPoint = new Location(player.getWorld(), min.getBlockX(), min.getBlockY(), min.getBlockZ());
                final Chunk chunkMax = maxPoint.getChunk();
                final Chunk chunkMin = minPoint.getChunk();
                final int maxZ = chunkMax.getZ();
                final int maxX = chunkMax.getX();
                final int minX = chunkMin.getX();
                final int minZ = chunkMin.getZ();
                final String world = sel.getWorld().getName();
                for (int x = minX; x <= maxX; ++x) {
                    for (int z = minZ; z <= maxZ; ++z) {
                        final Chunk chunk = player.getWorld().getChunkAt(x, z);
                        if (!ChunkLoader.getChunkManager().getChunkList().contains(chunk)) {
                            ChunkLoader.getChunkManager().addChunk(chunk);
                            String chunkCoords = "X: " + chunk.getX() + "; Z: " + chunk.getZ();
                            ChunkLoader.messages.sendMessage(player, "addchunk.correct", new String[][]{
                                    {"%chunk_coords%", chunkCoords}
                            });
                        }
                    }
                }
                ChunkLoader.messages.sendMessage(sender, "chunkloader.region-added");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
