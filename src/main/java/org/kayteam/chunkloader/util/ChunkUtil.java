package org.kayteam.chunkloader.util;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class ChunkUtil {

    public static Chunk toChunk(String chunkString) {
        String[] splitted = chunkString.split(";");
        int x = Integer.parseInt(splitted[0]);
        int z = Integer.parseInt(splitted[1]);
        String worldName = splitted[2];
        World world = Bukkit.getWorld(worldName);
        assert world != null;
        return world.getChunkAt(x, z);
    }

    public static List<String> toStringList(List<Chunk> chunks) {
        List<String> result = new ArrayList<>();
        for (Chunk chunk : chunks) {
            result.add(toString(chunk));
        }
        return result;
    }

    public static String toString(Chunk chunk) {
        return chunk.getX() + ";" + chunk.getZ() + ";" + chunk.getWorld().getName();
    }
}
