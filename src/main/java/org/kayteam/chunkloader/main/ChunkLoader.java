package org.kayteam.chunkloader.main;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.chunkloader.commands.Command_AddChunk;
import org.kayteam.chunkloader.commands.Command_ChunkLoader;
import org.kayteam.chunkloader.commands.Command_RemoveChunk;
import org.kayteam.chunkloader.util.Color;
import org.kayteam.chunkloader.util.YML;

public class ChunkLoader extends JavaPlugin {

    public ConsoleCommandSender logger = Bukkit.getConsoleSender();

    public YML messages = new YML(this,"messages");
    public YML data = new YML(this,"data");
    public YML config = new YML(this,"config");

    public String prefix = messages.getFile().getString("plugin-prefix");
    private String logPrefix = "&2Chunk&aLoader &7>>> &f";

    @Override
    public void onEnable() {
        registerFiles();
        registerCommands();
        if(config.getFile().getBoolean("enable-load-on-start")){
            loadChunks(this);
        }
        logger.sendMessage(Color.convert(logPrefix+"El plugin fue activado correctamente."));
        logger.sendMessage(Color.convert(logPrefix+"Creado por segu23"));
    }

    public void loadChunks(ChunkLoader plugin) {
        FileConfiguration data = plugin.data.getFile();
        for(String chunkKey : data.getKeys(false)){
            Bukkit.getServer().getWorld(data.getString(chunkKey+".world")).loadChunk(data.getInt(chunkKey+".x"),data.getInt(chunkKey+".z"));
        }
    }

    public void unloadChunks(ChunkLoader plugin){
        FileConfiguration data = plugin.data.getFile();
        for(String chunkKey : data.getKeys(false)){
            Bukkit.getServer().getWorld(data.getString(chunkKey+".world")).unloadChunk(data.getInt(chunkKey+".x"),data.getInt(chunkKey+".z"));
        }
    }

    private void registerCommands() {
        getCommand("addchunk").setExecutor(new Command_AddChunk(this));
        getCommand("removechunk").setExecutor(new Command_RemoveChunk(this));
        getCommand("chunkloader").setExecutor(new Command_ChunkLoader(this));
    }

    private void registerFiles() {
        messages.registerFile();
        data.registerFile();
        config.registerFile();
    }

    @Override
    public void onDisable() {
        logger.sendMessage(Color.convert(logPrefix+"El plugin fue desactivado correctamente."));
        logger.sendMessage(Color.convert(logPrefix+"Creado por segu23"));
    }
}

