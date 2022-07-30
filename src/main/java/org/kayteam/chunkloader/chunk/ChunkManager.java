package org.kayteam.chunkloader.chunk;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kayteam.chunkloader.ChunkLoader;
import org.kayteam.chunkloader.util.ChunkUtil;
import org.kayteam.storageapi.storage.Yaml;

import java.util.ArrayList;
import java.util.List;

public class ChunkManager {

    public boolean chunkLoad;
    public boolean chunkLoadLogs;
    private boolean isPaper = false;
    private List<Chunk> chunkList = new ArrayList<>();

    public boolean isPaper() {
        return isPaper;
    }

    public void setPaperState(boolean state){
        this.isPaper = state;
        if(state){
            Yaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.logPrefix+"&fEnabled Paper version.");
        }else{
            Yaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.logPrefix+"&fEnabled Spigot version.");
        }
    }

    public boolean isChunkLoad(){
        return chunkLoad;
    }

    public boolean isChunkLoadLogs(){
        return chunkLoadLogs;
    }

    public void setChunkLoadLogs(boolean state){
        ChunkLoader.config.set("log-chunk-load", state);
        ChunkLoader.config.saveYamlFile();
        chunkLoadLogs = state;
    }

    public void loadChunk(Chunk chunk){
        if(ChunkLoader.getInstance().getServer().getWorlds().contains(chunk.getWorld())){
                Bukkit.getScheduler().runTaskLater(ChunkLoader.getInstance(), new Runnable() {
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
                Yaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.messages.getString("logs.load"), new String[][]{{"%chunk%", ChunkUtil.toString(chunk)}});
            }
        }
    }

    public void enableChunkLoad() {
        loadChunkList();
        ChunkLoader.config.set("chunk-load", true);
        ChunkLoader.config.saveYamlFile();
        chunkLoad = true;
    }

    public void disableChunkLoad(){
        ChunkLoader.config.set("chunk-load", false);
        ChunkLoader.config.saveYamlFile();
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
            Yaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.messages.getString("logs.unload"), new String[][]{{"%chunk%", ChunkUtil.toString(chunk)}});
        }
    }

    public void deleteChunk(Chunk chunk, Player player){
        World chunkLocationWorld = chunk.getWorld();
        int chunkLocationX = chunk.getX();
        int chunkLocationZ = chunk.getZ();
        String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;
        if(chunkList.contains(chunk)){
            getChunkList().remove(chunk);
            List <String> chunkStringList = ChunkUtil.toStringList(getChunkList());
            ChunkLoader.data.set("chunks-list", chunkStringList);
            ChunkLoader.data.saveYamlFile();
            unloadChunk(chunk);
            ChunkLoader.messages.sendMessage(player,"removechunk.correct", new String[][]{{"%chunk_coords%",chunkCoords}});
        }else{
            ChunkLoader.messages.sendMessage(player,"removechunk.inexist", new String[][]{{"%chunk_coords%",chunkCoords}});
        }
        loadChunkList();
    }

    public void loadChunkList(){
        chunkList.clear();
        List<String> chunks = ChunkLoader.data.getStringList("chunks-list");
        for(String chunkString : chunks){
            Chunk chunk = ChunkUtil.toChunk(chunkString);
            if(ChunkLoader.getInstance().getServer().getWorlds().contains(chunk.getWorld())){
                getChunkList().add(chunk);
                loadChunk(chunk);
            }
        }
    }

    public List<Chunk> getChunkList(){
        return chunkList;
    }

    public void addChunk(Chunk chunk){
        getChunkList().add(chunk);
        ChunkLoader.data.set("chunks-list", ChunkUtil.toStringList(getChunkList()));
        ChunkLoader.data.saveYamlFile();

        loadChunk(chunk);
    }
}
