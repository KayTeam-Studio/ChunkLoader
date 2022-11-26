package org.kayteam.chunkloader;

import org.bukkit.plugin.java.JavaPlugin;
import org.kayteam.api.BrandSender;
import org.kayteam.api.bStats.Metrics;
import org.kayteam.api.inventory.InventoryManager;
import org.kayteam.api.updatechecker.UpdateChecker;
import org.kayteam.chunkloader.commands.Command_AddChunk;
import org.kayteam.chunkloader.commands.Command_AddChunkRegion;
import org.kayteam.chunkloader.commands.Command_ChunkLoader;
import org.kayteam.chunkloader.commands.Command_RemoveChunk;
import org.kayteam.chunkloader.listeners.ChunkUnloadListener;
import org.kayteam.chunkloader.listeners.PlayerJoinListener;
import org.kayteam.chunkloader.chunk.ChunkManager;
import org.kayteam.storageapi.storage.Yaml;

import java.util.Objects;

public class ChunkLoader extends JavaPlugin {

    private static ChunkLoader instance;

    private static ChunkManager chunkManager;

    public static Yaml messages;
    private Yaml messages_es;
    private Yaml messages_en;
    private Yaml messages_ru;
    private Yaml messages_fr;
    public static Yaml data;
    public static Yaml config;

    public static final String logPrefix = "&2Chunk&aLoader &7>> &f";
    private String lang;

    @Override
    public void onEnable() {
        instance = this;
        chunkManager = new ChunkManager();
        inventoryManager = new InventoryManager(this);
        enablePluginUpdateChecker();
        checkPaper();
        registerFiles();
        loadConfig();
        loadMessages();
        registerCommands();
        enableBStats();
        registerListeners();
        loadAll();
        BrandSender.sendBrandMessage(this, "&aEnabled");
    }

    private void loadConfig(){
        chunkManager.chunkLoad = config.getBoolean("chunk-load", true);
        chunkManager.chunkLoadLogs = config.getBoolean("log-chunk-load", true);
        lang = config.getString("lang", "en");
    }

    public static ChunkManager getChunkManager(){
        return chunkManager;
    }

    private void loadMessages(){
        try{
            messages = new Yaml(this,"messages_"+ lang);
            messages.registerYamlFile();
            Yaml.sendSimpleMessage(getServer().getConsoleSender(), messages_en.getString("logs.messages"), new String[][]{{"%lang%", "messages_"+lang}});
        }catch (Exception e){
            Yaml.sendSimpleMessage(getServer().getConsoleSender(), messages_en.getString("logs.messages-error"), new String[][]{{"%lang%", "messages_"+lang}});
        }
    }

    private void registerFiles(){
        messages_es = new Yaml(this, "messages_es");
        messages_en = new Yaml(this, "messages_en");
        messages_ru = new Yaml(this, "messages_ru");
        messages_fr = new Yaml(this, "messages_fr");
        config = new Yaml(this,"config");
        data = new Yaml(this,"data");
        messages_es.registerYamlFile();
        messages_en.registerYamlFile();
        messages_ru.registerYamlFile();
        messages_fr.registerYamlFile();
        config.registerYamlFile();
        data.registerYamlFile();
    }

    private void enablePluginUpdateChecker(){
        updateChecker = new UpdateChecker(this, 92834);
        if (updateChecker.getUpdateCheckResult().equals(UpdateChecker.UpdateCheckResult.OUT_DATED)) {
            updateChecker.sendOutDatedMessage(getServer().getConsoleSender());
        }
    }

    private static UpdateChecker updateChecker;
    public static UpdateChecker getUpdateChecker() {
        return updateChecker;
    }

    private static InventoryManager inventoryManager;
    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    private void enableBStats(){
        int pluginId = 	12091;
        new Metrics(this, pluginId);
    }

    private void registerListeners(){
        getServer().getPluginManager().registerEvents(new ChunkUnloadListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(inventoryManager, this);
    }

    private void loadAll(){
        if(chunkManager.isChunkLoad()){
            chunkManager.enableChunkLoad();
        }
    }

    private void checkPaper(){
        try {
            Class.forName("com.destroystokyo.paper.ParticleBuilder");
            getChunkManager().setPaperState(true);
        } catch (ClassNotFoundException ignored) {
            getChunkManager().setPaperState(false);
        }
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("addchunk")).setExecutor(new Command_AddChunk());
        Objects.requireNonNull(getCommand("addchunkregion")).setExecutor(new Command_AddChunkRegion());
        Objects.requireNonNull(getCommand("removechunk")).setExecutor(new Command_RemoveChunk());
        Objects.requireNonNull(getCommand("chunkloader")).setExecutor(new Command_ChunkLoader());
    }

    public static ChunkLoader getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        BrandSender.sendBrandMessage(this, "&cDisabled");
    }
}

