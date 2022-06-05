package org.kayteam.chunkloader.main;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kayteam.kayteamapi.yaml.Yaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChunkManager {

    private final MiPlugin plugin;

    public boolean chunkLoad;
    public boolean chunkLoadLogs;
    private boolean isPaper = false;
    private List<String> chunkStringList;
    private List<Chunk> chunkList;
    private List<String[]> chunkListStringSplit;

    public ChunkManager(MiPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean isPaper() {
        return isPaper;
    }

    public void setPaperState(boolean state){
        this.isPaper = state;
        if(state){
            Yaml.sendSimpleMessage(plugin.getServer().getConsoleSender(), plugin.logPrefix+"&fEnabled Paper version.");
        }else{
            Yaml.sendSimpleMessage(plugin.getServer().getConsoleSender(), plugin.logPrefix+"&fEnabled Spigot version.");
        }
    }

    public boolean isChunkLoad(){
        return chunkLoad;
    }

    public boolean isChunkLoadLogs(){
        return chunkLoadLogs;
    }

    public void setChunkLoadLogs(boolean state){
        plugin.config.set("log-chunk-load", state);
        plugin.config.saveFileConfiguration();
        chunkLoadLogs = state;
    }

    public void loadChunk(Chunk chunk){
        if(plugin.getServer().getWorlds().contains(chunk.getWorld())){
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        try{
                            chunk.getWorld().setChunkForceLoaded(chunk.getX(), chunk.getZ(), true);
                        }catch (NoSuchMethodError e){
                            chunk.getWorld().loadChunk(chunk.getX(), chunk.getZ());
                        }
                    }
                },1);
            if(isChunkLoadLogs()){
                Yaml.sendSimpleMessage(plugin.getServer().getConsoleSender(), plugin.messages.getString("logs.load"), new String[][]{{"%chunk%", formatStringChunk(chunk)}});
            }
        }
    }

    public void enableChunkLoad() {
        loadChunkList();
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
        try{
            chunk.getWorld().setChunkForceLoaded(chunk.getX(), chunk.getZ(), false);
        }catch (NoSuchMethodError e){
            chunk.getWorld().unloadChunk(chunk);
        }
        if(isChunkLoadLogs()){
            Yaml.sendSimpleMessage(plugin.getServer().getConsoleSender(), plugin.messages.getString("logs.unload"), new String[][]{{"%chunk%", formatStringChunk(chunk)}});
        }
    }

    public void deleteChunk(Chunk chunk, Player player){
        World chunkLocationWorld = chunk.getWorld();
        int chunkLocationX = chunk.getX();
        int chunkLocationZ = chunk.getZ();
        String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;
        if(chunkList.contains(chunk)){
            List <String> chunkStringList = plugin.data.getStringList("chunks-list");
            chunkStringList.remove(formatStringChunk(chunk));
            plugin.data.set("chunks-list", chunkStringList);
            plugin.data.saveFileConfiguration();
            unloadChunk(chunk);
            plugin.messages.sendMessage(player,"removechunk.correct", new String[][]{{"%chunk_coords%",chunkCoords}});
        }else{
            plugin.messages.sendMessage(player,"removechunk.inexist", new String[][]{{"%chunk_coords%",chunkCoords}});
        }
        loadChunkList();
    }

    public void loadChunkList(){
        chunkStringList = plugin.data.getStringList("chunks-list");
        List<Chunk> chunksFormated = new ArrayList<>();
        List<String[]> chunksSplited = new ArrayList<>();
        for(String chunkIndex : chunkStringList){
            Chunk chunk = unformatChunk(chunkIndex);
            if(plugin.getServer().getWorlds().contains(chunk.getWorld())){
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
