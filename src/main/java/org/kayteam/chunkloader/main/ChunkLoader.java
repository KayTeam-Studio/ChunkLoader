package org.kayteam.chunkloader.main;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.chunkloader.commands.Command_AddChunk;
import org.kayteam.chunkloader.commands.Command_ChunkLoader;
import org.kayteam.chunkloader.commands.Command_RemoveChunk;
import org.kayteam.chunkloader.listeners.UnloadEvent;
import org.kayteam.chunkloader.util.Color;
import org.kayteam.chunkloader.util.YML;

import java.util.List;
import java.util.Objects;

public class ChunkLoader extends JavaPlugin {

    public static ChunkLoader chunkLoader;

    public YML messages;
    public YML messages_es = new YML(this, "messages_es");
    public YML messages_en = new YML(this, "messages_en");
    public YML data = new YML(this,"data");
    public YML config = new YML(this,"config");

    private boolean chunkLoad;

    private List<String> chunkList;

    public String prefix;
    private final String logPrefix = "&2Chunk&aLoader &7>> &f";

    @Override
    public void onEnable() {
        chunkLoader = this;
        config.registerFile();
        chunkLoad = config.getFile().getBoolean("chunk-load");
        messages_es.registerFile();
        messages_en.registerFile();
        String lang = config.getFile().getString("lang");
        try{
            messages = new YML(this,"messages_"+ lang);
            getLogger().info(Color.convert(messages.getFile().getString("logs.messages").replaceAll("%lang%", "messages_"+lang)));
        }catch (Exception e){
            getLogger().info(Color.convert(messages.getFile().getString("logs.messages-error").replaceAll("%lang%", "messages_"+lang)));
        }
        prefix = messages.getFile().getString("plugin-prefix");
        data.registerFile();
        chunkList = data.getFile().getStringList("chunks-list");
        registerCommands();
        getLogger().info(Color.convert(logPrefix+"El plugin fue activado correctamente."));
        getLogger().info(Color.convert(logPrefix+"Creado por segu23"));
        registerListeners();
        if(isChunkLoadEnableOnStart()){
            enableChunkLoad();
        }
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new UnloadEvent(), this);
    }

    public static ChunkLoader getChunkLoader(){
        return chunkLoader;
    }

    public boolean isChunkLoadEnable(){
        return chunkLoad;
    }

    public boolean isChunkLoadEnableOnStart(){
        return getConfig().getBoolean("enable-load-on-start");
    }

    public void enableChunkLoad() {
        getConfig().set("chunk-load", true);
        config.saveFile();
        chunkLoad = true;
        FileConfiguration data = this.data.getFile();
        for(String chunkFormated : data.getStringList("chunks-list")){
            loadChunk(unformatChunk(formatChunk(chunkFormated)));
        }
    }

    public void disableChunkLoad(){
        getConfig().set("chunk-load", false);
        config.saveFile();
        chunkLoad = false;
    }

    public void loadChunk(Chunk chunk){
        String[] chunkKey = formatChunk(chunk);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().runTaskAsynchronously(getChunkLoader(), new Runnable() {
                    @Override
                    public void run() {
                        Objects.requireNonNull(Bukkit.getServer().getWorld(chunkKey[2])).loadChunk(Integer.parseInt(chunkKey[0]),Integer.parseInt(chunkKey[1]));
                        Objects.requireNonNull(Bukkit.getServer().getWorld(chunkKey[2])).setChunkForceLoaded(Integer.parseInt(chunkKey[0]),Integer.parseInt(chunkKey[1]), true);
                        getLogger().info(Color.convert(messages.getFile().getString("logs.load").replaceAll("%chunk%", formatChunkString(chunk))));
                    }
                });
            }
        });
        thread.start();
    }

    public String[] formatChunk(Chunk chunk){
        return (chunk.getX()+";"+chunk.getZ()+";"+chunk.getWorld().getName()).split(";");
    }

    public String formatChunkString(Chunk chunk){
        return (chunk.getX()+";"+chunk.getZ()+";"+chunk.getWorld().getName());
    }

    public String[] formatChunk(String chunkFormated){
        return chunkFormated.split(";");
    }

    public Chunk unformatChunk(String[] chunkFormatedSplited){
        return Objects.requireNonNull(Bukkit.getServer().getWorld(chunkFormatedSplited[2])).getChunkAt(Integer.parseInt(chunkFormatedSplited[0]),Integer.parseInt(chunkFormatedSplited[1]));
    }

    public List<String> getChunkList(){
        return chunkList;
    }


    private void registerCommands() {
        getCommand("addchunk").setExecutor(new Command_AddChunk(this));
        getCommand("removechunk").setExecutor(new Command_RemoveChunk(this));
        getCommand("chunkloader").setExecutor(new Command_ChunkLoader(this));
    }

    @Override
    public void onDisable() {
        getLogger().info(Color.convert(logPrefix+"El plugin fue desactivado correctamente."));
        getLogger().info(Color.convert(logPrefix+"Creado por segu23"));
    }
}

