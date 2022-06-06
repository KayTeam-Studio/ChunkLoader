package org.kayteam.chunkloader.main;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.kayteam.api.simple.yaml.SimpleYaml;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChunkManager {

    public boolean chunkLoad;
    public boolean chunkLoadLogs;
    private boolean isPaper = false;
    private List<String> chunkStringList;
    private List<Chunk> chunkList;
    private List<String[]> chunkListStringSplit;

    public boolean isPaper() {
        return isPaper;
    }

    public void setPaperState(boolean state){
        this.isPaper = state;
        if(state){
            SimpleYaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.logPrefix+"&fEnabled Paper version.");
        }else{
            SimpleYaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.logPrefix+"&fEnabled Spigot version.");
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
                SimpleYaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.messages.getString("logs.load"), new String[][]{{"%chunk%", formatStringChunk(chunk)}});
            }
        }
    }

    public void enableChunkLoad() {
        loadChunkList();
        ChunkLoader.config.set("chunk-load", true);
        ChunkLoader.config.saveYamlFile();
        chunkLoad = true;
        for(Chunk chunk : chunkList){
            loadChunk(chunk);
        }
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
            SimpleYaml.sendSimpleMessage(ChunkLoader.getInstance().getServer().getConsoleSender(), ChunkLoader.messages.getString("logs.unload"), new String[][]{{"%chunk%", formatStringChunk(chunk)}});
        }
    }

    public void deleteChunk(Chunk chunk, Player player){
        World chunkLocationWorld = chunk.getWorld();
        int chunkLocationX = chunk.getX();
        int chunkLocationZ = chunk.getZ();
        String chunkCoords = "X: "+chunkLocationX+"; Z:"+chunkLocationZ;
        if(chunkList.contains(chunk)){
            List <String> chunkStringList = ChunkLoader.data.getStringList("chunks-list");
            chunkStringList.remove(formatStringChunk(chunk));
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
        chunkStringList = ChunkLoader.data.getStringList("chunks-list");
        List<Chunk> chunksFormated = new ArrayList<>();
        List<String[]> chunksSplited = new ArrayList<>();
        for(String chunkIndex : chunkStringList){
            Chunk chunk = unformatChunk(chunkIndex);
            if(ChunkLoader.getInstance().getServer().getWorlds().contains(chunk.getWorld())){
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

    public void addChunk(Chunk chunk){
        String chunkCoords = "X: "+chunk.getX()+"; Z: "+chunk.getZ();
        String chunkFormated = ChunkLoader.getChunkManager().formatStringChunk(chunk);
        List<String> chunkList = ChunkLoader.data.getStringList("chunks-list");
        chunkList.add(chunkFormated);
        ChunkLoader.data.set("chunks-list", chunkList);
        ChunkLoader.data.saveYamlFile();
        ChunkLoader.getChunkManager().getChunkStringList().add(chunkFormated);
        ChunkLoader.getChunkManager().getChunkList().add(chunk);

        try{
            chunk.setForceLoaded(true);
        }catch (NoSuchMethodError e){
            chunk.load();
        }
    }
}
