package org.kayteam.chunkloader.main;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.util.Color;
import org.kayteam.chunkloader.util.Send;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChunkManager {

    private ChunkLoader plugin = ChunkLoader.getChunkLoader();

    public boolean chunkLoad;
    public boolean chunkLoadLogs;
    private boolean isPaper = false;
    public List<String> chunkStringList;
    public List<Chunk> chunkList;
    public List<String[]> chunkListStringSplit;

    public void setPaperState(boolean state){
        this.isPaper = state;
        if(state){
            plugin.getLogger().info(Color.convert(plugin.logPrefix+"&fEnabled Paper version."));
        }else{
            plugin.getLogger().info(Color.convert(plugin.logPrefix+"&fEnabled Spigot version."));
        }
    }

    public boolean isChunkLoadEnable(){
        return chunkLoad;
    }

    public boolean isChunkLoadLogsEnable(){
        return chunkLoadLogs;
    }

    public void setChunkLoadLogs(boolean state){
        plugin.config.set("log-chunk-load", state);
        plugin.config.saveFileConfiguration();
        chunkLoadLogs = state;
    }

    public void loadChunk(Chunk chunk){
        String[] chunkKey = formatChunk(chunk);
        if(chunk.getWorld() != null){
            Objects.requireNonNull(Bukkit.getServer().getWorld(chunkKey[2])).loadChunk(Integer.parseInt(chunkKey[0]),Integer.parseInt(chunkKey[1]));
            Objects.requireNonNull(Bukkit.getServer().getWorld(chunkKey[2])).setChunkForceLoaded(Integer.parseInt(chunkKey[0]),Integer.parseInt(chunkKey[1]), true);
            if(isChunkLoadLogsEnable()){
                plugin.getLogger().info(Color.convert(plugin.logPrefix+plugin.messages.getString("logs.load").replaceAll("%chunk%", formatStringChunk(chunk))));
            }
        }
    }

    public void enableChunkLoad() {
        plugin.config.set("chunk-load", true);
        plugin.config.saveFileConfiguration();
        chunkLoad = true;
        for(Chunk chunk : chunkList){
            loadChunk(chunk);
        }
    }

    public void disableChunkLoad(){
        plugin.config.set("chunk-load", false);
        plugin.config.saveFileConfiguration();
        chunkLoad = false;
        for(Chunk chunk : chunkList){
            unloadChunk(chunk);
        }
    }

    public void unloadChunk(Chunk chunk){
        String[] chunkFormated = formatChunk(chunk);
        Bukkit.getWorld(chunkFormated[2]).getChunkAt(Integer.parseInt(chunkFormated[0]), Integer.parseInt(chunkFormated[1])).setForceLoaded(false);
        if(isChunkLoadLogsEnable()){
            plugin.getLogger().info(Color.convert(plugin.logPrefix+plugin.messages.getString("logs.unload").replaceAll("%chunk%", formatStringChunk(chunk))));
        }
    }

    public void deleteChunk(Chunk chunk, Player player){
        Chunk chunkLocation = unformatChunk(formatChunk(chunk));
        World chunkLocationWorld = chunkLocation.getWorld();
        int chunkLocationX = chunkLocation.getX();
        int chunkLocationZ = chunkLocation.getZ();
        String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;
        if(plugin.getChunkManager().getChunkList().contains(chunkLocation)){
            List<String> chunkList = plugin.data.getStringList("chunks-list");
            chunkList.remove(chunk);
            plugin.data.set("chunks-list", chunkList);
            plugin.data.saveFileConfiguration();
            Bukkit.getWorld(chunkLocationWorld.getName()).setChunkForceLoaded(chunkLocationX,chunkLocationZ,false);
            Send.playerMessage(player,plugin.prefix+plugin.messages.getString("removechunk.correct")
                    .replaceAll("%chunk_coords%",chunkCoords));
        }else{
            Send.playerMessage(player,plugin.prefix+plugin.messages.getString("removechunk.inexist")
                    .replaceAll("%chunk_coords%",chunkCoords));
        }
        loadChunkList();
    }

    public void loadChunkList(){
        chunkStringList = plugin.data.getStringList("chunks-list");
        List<Chunk> chunksFormated = new ArrayList<>();
        List<String[]> chunksSplited = new ArrayList<>();
        for(String chunkIndex : chunkStringList){
            Chunk chunk = unformatChunk(formatChunk(chunkIndex));
            if(chunk.getWorld() !=null){
                chunksFormated.add(chunk);
                chunksSplited.add(formatChunk(chunkIndex));
            }
        }
        chunkList = chunksFormated;
        chunkListStringSplit = chunksSplited;
    }

    public String[] formatChunk(Chunk chunk){
        String[] chunkSplited = new String[3];
        chunkSplited[0] = String.valueOf(chunk.getX());
        chunkSplited[1] = String.valueOf(chunk.getZ());
        chunkSplited[2] = chunk.getWorld().getName();
        return chunkSplited;
    }

    public String formatStringChunk(Chunk chunk){
        return chunk.getX()+";"+chunk.getZ()+";"+chunk.getWorld().getName();
    }

    public String[] formatChunk(String chunkFormated){
        return chunkFormated.split(";");
    }

    public Chunk unformatChunk(String[] chunkFormatedSplited){
        return Objects.requireNonNull(Bukkit.getServer().getWorld(chunkFormatedSplited[2])).getChunkAt(Integer.parseInt(chunkFormatedSplited[0]),Integer.parseInt(chunkFormatedSplited[1]));
    }

    public Chunk unformatChunk(String chunkFormated){
        return unformatChunk(formatChunk(chunkFormated));
    }

    public List<String> getChunkStringList(){
        return chunkStringList;
    }

    public List<Chunk> getChunkList(){
        return chunkList;
    }

    public List<String[]> getChunkListStringSplit(){
        return chunkListStringSplit;
    }
}
